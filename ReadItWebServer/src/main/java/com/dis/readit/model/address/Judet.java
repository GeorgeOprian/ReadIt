package com.dis.readit.model.address;

import com.dis.readit.model.subscription.Subscription;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity(name = "JUDET")
@Table(name = "JUDET")
public class Judet {

	@Id
	@Column(name = "id_judet")
	private Integer idJudet;

	@Column(name = "nume")
	private String nume;

	@OneToMany(
			mappedBy = "judet",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private List<Localitate> localitati = new ArrayList<>();
}
