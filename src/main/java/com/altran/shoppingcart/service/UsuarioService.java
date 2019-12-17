package com.altran.shoppingcart.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.shoppingcart.exceptions.NoContentException;
import com.altran.shoppingcart.model.Usuario;
import com.altran.shoppingcart.repository.UsuarioRepository;
import com.altran.shoppingcart.service.interfaces.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService{

	private UsuarioRepository repository;

	@Autowired
	private UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public Usuario getUsuarioById(Long id) {
		Usuario user;
		Optional<Usuario> findUser = repository.findById(id);
		if(!findUser.isPresent()) {
			throw new NoContentException("");
		}
		user = findUser.get();
		return user;
	}

	@Override
	public List<Usuario> getAll() {
		return Optional.ofNullable(repository.findAll())
				.orElseThrow(() -> new NoContentException(""))
				.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	@Override
	public Usuario createUser(Usuario user) {
		return repository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		return repository.save(usuario);
	}

}