package com.altran.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.altran.shoppingcart.model.Usuario;
import com.altran.shoppingcart.service.interfaces.IUsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping(value = "/users/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Usuario", tags = { "USUARIO" })
public class UsuarioController {
	
	private IUsuarioService service;
	
	@Autowired
	public UsuarioController(IUsuarioService service) {
		this.service = service;
	}

	@ApiOperation(value = "Buscar usuario por id", response = Usuario.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 204, message = "Não há conteúdo para enviar para esta solicitação"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 401, message = "Não autenticado"),
			@ApiResponse(code = 403, message = "Não autorizado"),
			@ApiResponse(code = 404, message = "Não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno no servidor"),
			@ApiResponse(code = 504, message = "Tempo limite da requisição excedido")
			})
	@GetMapping(path = {"/{id}"})
	@ResponseStatus(value = HttpStatus.OK, reason = "OK")
	public Usuario buscarUsuarioPorId(
			@ApiParam(name = "id", value = "Id do usuario", required = true, example = "1")@PathVariable Long id){
		return service.getUsuarioById(id);
	}
	
	@ApiOperation(value = "Buscar todos usuarios", response = Usuario.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 204, message = "Não há conteúdo para enviar para esta solicitação"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 401, message = "Não autenticado"),
			@ApiResponse(code = 403, message = "Não autorizado"),
			@ApiResponse(code = 404, message = "Não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno no servidor"),
			@ApiResponse(code = 504, message = "Tempo limite da requisição excedido")
			})
	@GetMapping(path = {"/"})
	@ResponseStatus(value = HttpStatus.OK, reason = "OK")
	public List<Usuario> buscarTodosItens(){
		return service.getAll();
	}
	
	@ApiOperation(value = "Criar um novo usuario", response = Usuario.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Criado com sucesso"),
			@ApiResponse(code = 204, message = "Não há conteúdo para enviar para esta solicitação"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 401, message = "Não autenticado"),
			@ApiResponse(code = 403, message = "Não autorizado"),
			@ApiResponse(code = 404, message = "Não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno no servidor"),
			@ApiResponse(code = 504, message = "Tempo limite da requisição excedido")
			})
	@PostMapping(path = {"/"})
	@ResponseStatus(value = HttpStatus.CREATED, reason = "Criado com sucesso")
	public Usuario criarUsuario(@RequestBody Usuario usuario){
		return service.createUser(usuario);
	}
	
	@ApiOperation(value = "Atualizar um usuario", response = Usuario.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 204, message = "Não há conteúdo para enviar para esta solicitação"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 401, message = "Não autenticado"),
			@ApiResponse(code = 403, message = "Não autorizado"),
			@ApiResponse(code = 404, message = "Não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno no servidor"),
			@ApiResponse(code = 504, message = "Tempo limite da requisição excedido")
			})
	@PutMapping(path = {"/"})
	@ResponseStatus(value = HttpStatus.OK, reason = "OK")
	public Usuario atualizarUsuario(@RequestBody Usuario usuario){
		return service.updateUsuario(usuario);
	}
	
	@ApiOperation(value = "Apagar um usuario", response = Usuario.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 204, message = "Não há conteúdo para enviar para esta solicitação"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 401, message = "Não autenticado"),
			@ApiResponse(code = 403, message = "Não autorizado"),
			@ApiResponse(code = 404, message = "Não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno no servidor"),
			@ApiResponse(code = 504, message = "Tempo limite da requisição excedido")
			})
	@DeleteMapping(path = {"/{id}"})
	@ResponseStatus(value = HttpStatus.OK, reason = "OK")
	public void apagarUsuario(
			@ApiParam(name = "id", value = "Id do usuario", required = true, example = "1")@PathVariable Long id){
		service.deleteUser(id);
	}

}
