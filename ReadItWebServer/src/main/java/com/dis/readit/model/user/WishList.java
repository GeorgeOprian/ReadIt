package com.dis.readit.model.user;

import com.dis.readit.model.book.Book;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "WishList")
@Table(name = "wish_list")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class WishList {

	@Id
	@Column(name = "wish_lis_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer wishListId;

	@ManyToOne
	@JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_BOOK_USER"))
	@JsonManagedReference
	private Book book;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_BOOK"))
	private DataBaseUser user;

	public WishList(Book book, DataBaseUser user) {
		this.book = book;
		this.user = user;
	}
}
