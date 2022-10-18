package br.com.ana.operacoescrudmongo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
@Document(collection = "contato")
public class Contato {

	@Transient
	public static final String SEQUENCE_NAME = "contact_sequence";
	
	@EqualsAndHashCode.Include
	@Id
	private Long id;

	private String nome;

	private String EmailPrincipal;

}
