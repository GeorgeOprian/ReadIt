package com.dis.readit.model.book;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor
@Entity(name = "BookThumbnail")
@Table(name = "book_thumbnail")
public class BookThumbnail {

	@Id
	@Column(name ="thumbnail_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer thumbnailId;

	@Column(name ="small_thumbnail")
	private String smallThumbnail;

	@Column(name ="thumbnail")
	private String thumbnail;

	public BookThumbnail(String smallThumbnail, String thumbnail) {
		this.smallThumbnail = smallThumbnail;
		this.thumbnail = thumbnail;
	}
}
