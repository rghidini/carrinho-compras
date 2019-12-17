package com.altran.shoppingcart.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

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
public class CarrinhoVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Id do usuário deve estar preenchido")
	private String idUsuario;
	
	@NotEmpty(message = "Não é possível gravar um carrinho sem itens")
	private List<ItemCarrinhoVO> itens;
	
	@Positive(message = "O valor precisa ser maior que zero")
	private BigDecimal valorTotal;
	
}
