package com.altran.shoppingcart.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.shoppingcart.model.Item;
import com.altran.shoppingcart.model.vo.ItemVO;
import com.altran.shoppingcart.service.interfaces.IItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping(value = "/item/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Item", tags = { "ITEM" })
public class ItemController {
	
	private IItemService service;
	
	@Autowired
	public ItemController(IItemService service) {
		this.service = service;
	}

	@ApiOperation(value = "Buscar item por id", response = Item.class)
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
	public Item buscarItemPorId(
			@ApiParam(name = "id", value = "Id do item", required = true, example = "1")@PathVariable Long id){
		return service.getItemById(id);
	}
	
	@ApiOperation(value = "Buscar todos itens", response = Item.class)
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
	public List<Item> buscarTodosItens(){
		return service.getAll();
	}
	
	@ApiOperation(value = "Criar um novo item", response = Item.class)
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
	public Item criarItem(@Valid @RequestBody ItemVO item){
		return service.createItem(item);
	}
	
	@ApiOperation(value = "Atualizar um item", response = Item.class)
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
	public Item atualizarItem(@Valid @RequestBody Item item){
		return service.updateItem(item);
	}
	
	@ApiOperation(value = "Apagar um item", response = Item.class)
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
	public void apagarItem(
			@ApiParam(name = "id", value = "Id do item", required = true, example = "1")@PathVariable Long id){
		service.deleteItem(id);
	}

}
