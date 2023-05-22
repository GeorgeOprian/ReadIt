package com.dis.readit.model.address;

import com.dis.readit.model.user.DataBaseUser;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity(name = "LOCALITATE")
@Table(name = "LOCALITATE")
public class Localitate {

	@Id
	@Column(name = "id_localitate")
	private Integer idLocalitate;

	@Column(name = "nume")
	private String nume;

	@ManyToOne
	@JoinColumn(name = "id_judet", foreignKey = @ForeignKey(name = "LOC_JUD_FK"))
	private Judet judet;

}
