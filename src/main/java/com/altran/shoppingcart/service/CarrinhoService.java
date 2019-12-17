package com.altran.shoppingcart.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.shoppingcart.exceptions.NoContentException;
import com.altran.shoppingcart.model.Carrinho;
import com.altran.shoppingcart.repository.CarrinhoRepository;
import com.altran.shoppingcart.service.interfaces.ICarrinhoService;

@Service
public class CarrinhoService implements ICarrinhoService{

	private CarrinhoRepository repository;

	@Autowired
	private CarrinhoService(CarrinhoRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Carrinho> getCarrinhoByUserId(Long userId) {
		return Optional.ofNullable(repository.findByIdUsuario(userId))
				.orElseThrow(() -> new NoContentException(""))
				.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	@Override
	public List<Carrinho> createCart(List<Carrinho> carrinho) {
		return repository.saveAll(carrinho);
	}

	@Override
	public List<Carrinho> updateCart(List<Carrinho> carrinho) {
		return repository.saveAll(carrinho);
	}

}