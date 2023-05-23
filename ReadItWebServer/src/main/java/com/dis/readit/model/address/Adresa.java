package com.dis.readit.model.address;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity(name = "ADRESA")
@Table(name = "ADRESA")
public class Adresa {

	@Id
	@Column(name = "id_adresa")
	private Integer idAdresa;

	@Column(name = "strada")
	private String strada;

	@Column(name = "numar")
	private Integer numar;

	@Column(name = "bloc")
	private String bloc;

	@Column(name = "scara")
	private String scara;

	@Column(name = "numar_apartament")
	private Integer numarApartament;

	@OneToOne
	@JoinColumn(name = "id_localitate")
	private Localitate localitate;


}
