package com.altran.shoppingcart.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.altran.shoppingcart.model.vo.ItemCarrinhoVO;
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
@Document(collection = "carrinho")
public class Carrinho implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Transient
	public static final String SEQUENCE_NAME = "carrinho_sequence";
	
	@Id
	private Long id;
	
	@NotBlank(message = "Id do usuário deve estar preenchido")
	private String idUsuario;
	
	@NotEmpty(message = "Não é possível gravar um carrinho sem itens")
	private List<ItemCarrinhoVO> itens;
	
	@Positive(message = "O valor precisa ser maior que zero")
	private BigDecimal valorTotal;
	
}
