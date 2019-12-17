package com.altran.shoppingcart.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.shoppingcart.exceptions.NoContentException;
import com.altran.shoppingcart.model.Carrinho;
import com.altran.shoppingcart.model.vo.CarrinhoVO;
import com.altran.shoppingcart.model.vo.ItemCarrinhoVO;
import com.altran.shoppingcart.repository.CarrinhoRepository;
import com.altran.shoppingcart.service.interfaces.ICarrinhoService;
import com.altran.shoppingcart.service.interfaces.IUsuarioService;

@Service
public class CarrinhoService implements ICarrinhoService{

	private CarrinhoRepository repository;
	private IUsuarioService usuarioService;
	private SequenceGeneratorService sequence;

	@Autowired
	private CarrinhoService(CarrinhoRepository repository, IUsuarioService usuarioService, SequenceGeneratorService sequence) {
		this.repository = repository;
		this.usuarioService = usuarioService;
		this.sequence = sequence;
	}

	@Override
	public List<Carrinho> getCarrinhoByUserId(String userId) {
		List<Carrinho> listCarrinho = new ArrayList<>();
		
		Optional.ofNullable(repository.findByIdUsuario(userId))
		.orElseThrow(() -> new NoContentException(""))
		.stream()
		.filter(Objects::nonNull)
		.forEach(c -> {
			Carrinho carrinho = new Carrinho();
			carrinho.setId(c.getId());
			carrinho.setIdUsuario(c.getIdUsuario());
			carrinho.setItens(Optional.ofNullable(c.getItens())
					.orElseGet(Collections::emptyList)
					.stream()
					.sorted(Comparator.comparing(ItemCarrinhoVO::getNome)
							.thenComparing(Comparator.comparing(ItemCarrinhoVO::getValor)))
					.collect(Collectors.toList()));
			carrinho.setValorTotal(c.getValorTotal());
			listCarrinho.add(carrinho);
		});
		
		return listCarrinho;
	}

	@Override
	public Carrinho createCart(CarrinhoVO carrinho) {
		Carrinho car = new Carrinho();
		usuarioService.getUsuarioById(carrinho.getIdUsuario());
		car.setId(sequence.generateSequence(Carrinho.SEQUENCE_NAME));
		car.setIdUsuario(carrinho.getIdUsuario());
		car.setItens(carrinho.getItens());
		car.setValorTotal(carrinho.getValorTotal());

		car = repository.save(car);

		car.setItens(Optional.ofNullable(car.getItens())
				.orElseGet(Collections::emptyList)
				.stream()
				.sorted(Comparator.comparing(ItemCarrinhoVO::getNome)
						.thenComparing(Comparator.comparing(ItemCarrinhoVO::getValor)))
				.collect(Collectors.toList()));
		return car;
	}

	@Override
	public Carrinho updateCart(Carrinho carrinho) {
		carrinho = repository.save(carrinho);
		carrinho.setItens(Optional.ofNullable(carrinho.getItens())
				.orElseGet(Collections::emptyList)
				.stream()
				.sorted(Comparator.comparing(ItemCarrinhoVO::getNome)
						.thenComparing(Comparator.comparing(ItemCarrinhoVO::getValor)))
				.collect(Collectors.toList()));
		return carrinho;
	}

}