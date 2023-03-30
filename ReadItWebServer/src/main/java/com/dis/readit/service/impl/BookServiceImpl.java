package com.dis.readit.service.impl;

import com.dis.readit.dtos.input.ImageLinks;
import com.dis.readit.dtos.input.InputBookModel;
import com.dis.readit.dtos.input.VolumeInfo;
import com.dis.readit.model.Book;
import com.dis.readit.model.BookThumbnail;
import com.dis.readit.model.Category;
import com.dis.readit.repository.BookRepository;
import com.dis.readit.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public String insertBook(InputBookModel inputBookModel) {

		Book book = createBook(inputBookModel);

		Book savedBook = bookRepository.save(book);

		return savedBook.getBookId() + savedBook.getTitle();
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

		for (String category : volumeInfo.getCategories()) {
			book.addCategory(new Category(category));
		}
		return book;
	}
}
