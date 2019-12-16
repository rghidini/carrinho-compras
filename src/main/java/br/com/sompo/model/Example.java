package br.com.sompo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@Table(name = "Example")
public class Example implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CDRMO")
	private Long exampleId;
	
	@Column(name = "DSRMO")
	private String exampleDescription;
	
}
