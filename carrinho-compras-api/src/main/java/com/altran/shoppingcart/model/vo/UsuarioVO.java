package com.altran.shoppingcart.model.vo;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
public class UsuarioVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Id não pode estar em branco")
	private String id;
	
	@NotBlank(message = "O nome não pode estar em branco")
	private String nome;

	@NotBlank(message = "E-mail não pode estar em branco")
	@Email(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", message = "Formato de e-mail inválido")
	private String email;

}
