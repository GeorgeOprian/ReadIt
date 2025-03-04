package com.dis.readit.model.book;

import com.dis.readit.model.user.DataBaseUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "BookReview")
@Table(name = "book_review")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BookReview {

	@Id
	@Column(name = "review_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reviewId;

	@ManyToOne
	@JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_BOOK_USER"))
	@JsonManagedReference
	private Book book;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_BOOK"))
	private DataBaseUser user;

	@Column(name = "nbr_stars")
	private Double nbrStars;

	@Column(name = "content", columnDefinition = "text")
	private String content;

	@Column(name = "review_date")
	private LocalDate reviewDate;

	public BookReview(Book book, DataBaseUser user, Double nbrStars, String content, LocalDate reviewDate) {
		this.book = book;
		this.user = user;
		this.nbrStars = nbrStars;
		this.content = content;
		this.reviewDate = reviewDate;
	}
}
