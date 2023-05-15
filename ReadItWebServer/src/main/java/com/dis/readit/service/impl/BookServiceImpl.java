package com.dis.readit.service.impl;

import com.dis.readit.dtos.input.books.ImageLinks;
import com.dis.readit.dtos.input.books.InputBookModel;
import com.dis.readit.dtos.input.books.VolumeInfo;
import com.dis.readit.dtos.output.PageDto;
import com.dis.readit.dtos.output.book.BookDto;
import com.dis.readit.dtos.output.book.BookListDto;
import com.dis.readit.dtos.output.book.CategoryDto;
import com.dis.readit.exception.BookAlreadyExistsException;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.mapper.BookMapper;
import com.dis.readit.mapper.CategoriesMapper;
import com.dis.readit.mapper.PageMapper;
import com.dis.readit.model.book.Book;
import com.dis.readit.model.book.BookCategory;
import com.dis.readit.model.book.BookThumbnail;
import com.dis.readit.model.book.Category;
import com.dis.readit.repository.BookRepository;
import com.dis.readit.repository.CategoryRepository;
import com.dis.readit.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	private final CategoryRepository categoryRepository;

	private final BookMapper bookMapper;
	private final CategoriesMapper categoriesMapper;

	private final ObjectMapper objectMapper;

	public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, BookMapper mapper, CategoriesMapper categoriesMapper, ObjectMapper objectMapper) {
		this.bookRepository = bookRepository;
		this.categoryRepository = categoryRepository;
		this.bookMapper = mapper;
		this.categoriesMapper = categoriesMapper;
		this.objectMapper = objectMapper;
	}

	@Override
	public BookDto insertBook(BookDto request) {

		Book book = bookMapper.mapToBo(request);

		Optional<Book> bookOptional = bookRepository.findByIsbn(book.getIsbn());

		if (bookOptional.isPresent()) {
			throw new BookAlreadyExistsException("A book with the same ISBN already exists.");
		}

		List<Category> existentCategories = saveCategories(request.getCategories());

		for (Category category : existentCategories) {
			book.addCategory(category);
		}

		Book savedBook = bookRepository.save(book);

		return createBookDto(savedBook);
	}

	private List<Category> saveCategories(List<CategoryDto> categoriesFromRequest) {

		List<Category> inputCategories = categoriesFromRequest.stream().map(category -> new Category(category.getCategoryName()))
				.collect(Collectors.toList());

		List<Category> categoriesToSave = new ArrayList<>();
		List<Category> existentCategories = new ArrayList<>();
		for (Category category : inputCategories) {
			Optional<Category> existentCategory = categoryRepository.findByCategoryName(category.getCategoryName());
			if (existentCategory.isEmpty()) {
				categoriesToSave.add(category);
			} else {
				existentCategories.add(existentCategory.get());
			}
		}

		existentCategories.addAll(categoryRepository.saveAll(categoriesToSave));
		return existentCategories;

	}

	@Override
	public PageDto<BookListDto> loadListBooks(Integer pageNumber, Integer pageSize, String sortBy) {
		PageRequest pageRequest=  PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sortBy);

		Page<Book> pagedBooks = bookRepository.findAll(pageRequest);

		Page<BookListDto> pageBooksDtos = pagedBooks.map(this::createListBookDto);
		return PageMapper.mapToDto(pageBooksDtos);
	}

	@Override
	public BookDto findBookById(Integer bookId) {
		Optional<Book> bookOpt = bookRepository.findById(bookId);

		bookOpt.orElseThrow(() -> new EntityNotFound("Book with id " + bookId + " was not found"));

		return createBookDto(bookOpt.get());
	}

	private BookDto createBookDto(Book book) {
		BookDto bookDto = bookMapper.mapToDto(book);

		List<CategoryDto> categories = book.getBookCategories()
				.stream()
				.map(bookCategory -> categoriesMapper.mapToDto(bookCategory.getCategory()))
				.collect(Collectors.toList());

		bookDto.setCategories(categories);

		return bookDto;
	}

	private BookListDto createListBookDto(Book book) {
		BookListDto bookDto = bookMapper.mapToListDto(book);

		List<CategoryDto> categories = book.getBookCategories()
				.stream()
				.map(bookCategory -> categoriesMapper.mapToDto(bookCategory.getCategory()))
				.collect(Collectors.toList());

		bookDto.setCategories(categories);

		return bookDto;
	}

	private List<Category> createCategories(InputBookModel inputBookModel) {

		VolumeInfo volumeInfo = inputBookModel.getItem().getVolumeInfo();
		if (volumeInfo.getCategories() == null) {
			return null;
		}

		return volumeInfo.getCategories().stream().map(category -> new Category(category)).collect(Collectors.toList());

	}

	private static Book createBook(InputBookModel inputBookModel) {
		Book book = new Book();

		VolumeInfo volumeInfo = inputBookModel.getItem().getVolumeInfo();

		book.setTitle(volumeInfo.getTitle());
		book.setAuthor(String.join(", ", volumeInfo.getAuthors()));
		book.setPublisher(volumeInfo.getPublisher());
		book.setPublishedDate(volumeInfo.getPublishedDate());
		book.setDescription(volumeInfo.getDescription());
		book.setIsbn(volumeInfo.getIndustryIdentifiers().get(0).getIdentifier());
		book.setPageCount(volumeInfo.getPageCount());
		book.setAverageRating(volumeInfo.getAverageRating());
		book.setRatingsCount(volumeInfo.getRatingsCount());
		book.setMaturityRating(volumeInfo.getMaturityRating());
		book.setLanguage(volumeInfo.getLanguage());
		book.setInStock(inputBookModel.getInStock());

		ImageLinks imageLinks = volumeInfo.getImageLinks();
		if (imageLinks != null) {
			book.setThumbnail(new BookThumbnail(imageLinks.getSmallThumbnail(), imageLinks.getThumbnail()));
		}

		return book;
	}

	@Override
	public BookDto updateBook(Integer bookId, Map<String, Object> body) {
		Optional<Book> bookOpt = bookRepository.findById(bookId);

		bookOpt.orElseThrow(() -> new EntityNotFound("Book with id " + bookId + " was not found"));

		Book bookFromDB = bookOpt.get();

		Book patchedBook = applyPatchToEntity(bookFromDB, Book.class, body);

		Book savedBook = bookRepository.save(patchedBook);

		return bookMapper.mapToDto(savedBook);
	}

	private <T> T applyPatchToEntity(T bookFromDB, Class classz, Map<String, Object> requestBody) {
		requestBody.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(classz, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, bookFromDB, value);
		});

		return bookFromDB;
	}

	@Override
	public void deleteBook(Integer bookId) {
		Optional<Book> bookOpt = bookRepository.findById(bookId);

		bookOpt.orElseThrow(() -> new EntityNotFound("Book with id " + bookId + " was not found"));

		bookRepository.deleteById(bookId);

	}
}
