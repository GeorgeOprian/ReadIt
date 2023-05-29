package com.dis.readit.service.impl;

import com.dis.readit.dtos.PageDto;
import com.dis.readit.dtos.book.BookDto;
import com.dis.readit.dtos.book.BookListDto;
import com.dis.readit.dtos.book.CategoryDto;
import com.dis.readit.exception.EntityAlreadyExistsException;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.mapper.BookMapper;
import com.dis.readit.mapper.CategoriesMapper;
import com.dis.readit.mapper.PageMapper;
import com.dis.readit.model.book.Book;
import com.dis.readit.model.book.Category;
import com.dis.readit.repository.BookRepository;
import com.dis.readit.repository.CategoryRepository;
import com.dis.readit.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
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

	public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, BookMapper mapper, CategoriesMapper categoriesMapper) {
		this.bookRepository = bookRepository;
		this.categoryRepository = categoryRepository;
		this.bookMapper = mapper;
		this.categoriesMapper = categoriesMapper;
	}

	@Override
	public BookDto insertBook(BookDto request) {

		Book book = bookMapper.mapToBo(request);

		Optional<Book> bookOptional = bookRepository.findByIsbn(book.getIsbn());

		if (bookOptional.isPresent()) {
			throw new EntityAlreadyExistsException("A book with the same ISBN already exists.");
		}

		Book savedBook = saveBookDetails(request, book);

		return createBookDto(savedBook);
	}

	@Transactional Book saveBookDetails(BookDto request, Book book) {
		List<Category> existentCategories = saveCategories(request.getCategories());

		for (Category category : existentCategories) {
			book.addCategory(category);
		}

		return bookRepository.save(book);
	}

	private List<Category> saveCategories(List<CategoryDto> categoriesFromRequest) {

		if (categoriesFromRequest == null) {
			return new ArrayList<>();
		}

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

		// TODO: 29-May-23 delete from rent wishlist and reviews 
		
		bookRepository.deleteById(bookId);

	}
}
