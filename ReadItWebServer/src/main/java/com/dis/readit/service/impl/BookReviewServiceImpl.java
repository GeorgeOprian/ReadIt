package com.dis.readit.service.impl;

import com.dis.readit.dtos.book.BookReviewCreateDto;
import com.dis.readit.dtos.book.BookReviewDto;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.mapper.UserMapper;
import com.dis.readit.model.book.Book;
import com.dis.readit.model.book.BookReview;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.repository.BookRepository;
import com.dis.readit.repository.BookReviewRepository;
import com.dis.readit.service.BookReviewService;
import com.dis.readit.service.UserLoaderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookReviewServiceImpl implements BookReviewService {

	private final BookReviewRepository reviewRepository;
	private final BookRepository bookRepository;

	private final UserMapper userMapper;

	private final UserLoaderService userLoaderService;

	public BookReviewServiceImpl(BookRepository bookRepository, BookReviewRepository reviewRepository, UserMapper userMapper, UserLoaderService userLoaderService) {
		this.bookRepository = bookRepository;
		this.reviewRepository = reviewRepository;
		this.userMapper = userMapper;
		this.userLoaderService = userLoaderService;
	}

	@Override
	public BookReviewDto addReview(BookReviewCreateDto dto) {

		DataBaseUser user = userLoaderService.getUserByEmail(dto.getUserEmail());

		Optional<Book> bookOpt = bookRepository.findById(dto.getBookId());

		bookOpt.orElseThrow(() -> new EntityNotFound("Book with id " + dto.getBookId() + " was not found"));

		Book book = bookOpt.get();

		BookReview review = createBookReview(dto, user, book);

		book.addReview(review);

		computeRatingDetailsForBook(dto, book);

		saveBookReview(book, review);

		return createBookReviewDto(review);
	}

	private BookReviewDto createBookReviewDto(BookReview review) {
		BookReviewDto dto = new BookReviewDto();

		dto.setUser(userMapper.mapToDto(review.getUser()));
		dto.setNbrStars(review.getNbrStars());
		dto.setContent(review.getContent());

		return dto;
	}

	private static BookReview createBookReview(BookReviewCreateDto dto, DataBaseUser user, Book book) {
		BookReview review = new BookReview();

		review.setBook(book);
		review.setUser(user);
		review.setNbrStars(dto.getNbrStars());
		review.setContent(dto.getContent());
		return review;
	}

	@Transactional
	protected void saveBookReview(Book book, BookReview review) {
		reviewRepository.save(review);

		bookRepository.save(book);

	}

	private static void computeRatingDetailsForBook(BookReviewCreateDto dto, Book book) {
		List<BookReview> reviews = book.getReviews();

		double ratingSum = reviews.stream().mapToDouble(BookReview::getNbrStars).sum();
		int reviewsNbr = reviews.size();

		double averageRating = (ratingSum + dto.getNbrStars()) / (reviewsNbr + 1);

		book.setAverageRating(averageRating);
		book.setRatingsCount(reviewsNbr);
	}

	@Override
	public List<BookReviewDto> getBookReviews(Integer bookId) {

		Collection<BookReview> reviewsForBook = reviewRepository.getReviewsForBook(bookId);

		return reviewsForBook.stream().map(this::createBookReviewDto).collect(Collectors.toList());
	}

	@Override
	public void deleteReview(Integer idReview) {

		reviewRepository.deleteById(idReview);

	}
}
