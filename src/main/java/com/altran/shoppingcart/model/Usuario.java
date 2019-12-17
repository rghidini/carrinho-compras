package com.altran.shoppingcart.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@Document
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@NotBlank(message = "O nome não pode ser em branco")
	private String nome;
	
	@NotBlank(message = "E-mail não pode ser em branco")
	@Email(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", message = "Formato de e-mail inválido")
	private String email;
	
}
