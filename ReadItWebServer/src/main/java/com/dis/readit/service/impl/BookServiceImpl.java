package com.dis.readit.service.impl;

import com.dis.readit.dtos.input.ImageLinks;
import com.dis.readit.dtos.input.InputBookModel;
import com.dis.readit.dtos.input.VolumeInfo;
import com.dis.readit.dtos.output.BookDto;
import com.dis.readit.dtos.output.CategoryDto;
import com.dis.readit.exception.BookAlreadyExistsException;
import com.dis.readit.mapper.BookMapper;
import com.dis.readit.mapper.CategoriesMapper;
import com.dis.readit.model.Book;
import com.dis.readit.model.BookThumbnail;
import com.dis.readit.model.Category;
import com.dis.readit.repository.BookRepository;
import com.dis.readit.repository.CategoryRepository;
import com.dis.readit.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	private final CategoryRepository categoryRepository;

	private final BookMapper bokMapper;

	private final CategoriesMapper categoriesMapper;

	public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, BookMapper mapper, CategoriesMapper categoriesMapper) {
		this.bookRepository = bookRepository;
		this.categoryRepository = categoryRepository;
		this.bokMapper = mapper;
		this.categoriesMapper = categoriesMapper;
	}

	@Override
	public BookDto insertBook(InputBookModel inputBookModel) {

		Book book = createBook(inputBookModel);

		Optional<Book> bookOptional = bookRepository.findByIsbn(book.getIsbn());

		if (bookOptional.isPresent()) {
			throw new BookAlreadyExistsException("A book with the same ISBN already exists.");
		}

		List<Category> existentCategories = saveCategories(inputBookModel);

		for (Category category : existentCategories) {
			book.addCategory(category);
		}

		Book savedBook = bookRepository.save(book);

		return createBookDto(savedBook);
	}

	private List<Category> saveCategories(InputBookModel inputBookModel) {
		List<Category> inputCategories = createCategories(inputBookModel);
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
	public List<BookDto> loadAllBooks() {
		List<Book> loadedBooks = bookRepository.findAll();

		return loadedBooks.stream().map(book -> createBookDto(book)).collect(Collectors.toList());
	}

	private BookDto createBookDto(Book book) {
		BookDto bookDto = bokMapper.mapToDto(book);

		List<CategoryDto> categories = book.getBookCategories()
				.stream()
				.map(bookCategory -> categoriesMapper.mapToDto(bookCategory.getCategory()))
				.collect(Collectors.toList());

		bookDto.setCategories(categories);

		return bookDto;
	}

	private List<Category> createCategories(InputBookModel inputBookModel) {

		VolumeInfo volumeInfo = inputBookModel.getItem().getVolumeInfo();

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
		book.setThumbnail(new BookThumbnail(imageLinks.getSmallThumbnail(), imageLinks.getThumbnail()));


		return book;
	}
}
