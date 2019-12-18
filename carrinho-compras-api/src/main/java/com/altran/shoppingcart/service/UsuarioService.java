package com.altran.shoppingcart.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.altran.shoppingcart.exceptions.NoContentException;
import com.altran.shoppingcart.model.Usuario;
import com.altran.shoppingcart.model.vo.UsuarioVO;
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
	public Usuario getUsuarioById(String id) {
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
		List<Usuario> userList = repository.findAll();
		if(CollectionUtils.isEmpty(userList)) {
			throw new NoContentException("");
		}
		return userList;
	}

	@Override
	public Usuario createUser(UsuarioVO user) {
		Usuario usuario = new Usuario();
		if(repository.findById(user.getId()).isPresent()){
			throw new DuplicateKeyException("");
		}
		usuario.setId(user.getId());
		usuario.setEmail(user.getEmail());
		usuario.setNome(user.getNome());
		return repository.save(usuario);
	}

	@Override
	public void deleteUser(String id) {
		Usuario usuario = this.getUsuarioById(id);
		if(Objects.isNull(usuario)) {
			throw new NoContentException("");
		}
		repository.deleteById(id);
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		return repository.save(usuario);
	}

}