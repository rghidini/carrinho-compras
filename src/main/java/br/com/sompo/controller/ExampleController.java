package br.com.sompo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sompo.model.Example;
import br.com.sompo.service.interfaces.IExampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin	//TODO trocar example pelo nome do seu projeto
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Example", tags = { "EXAMPLE" })
public class ExampleController {
	
	private IExampleService service;
	
	@Autowired
	public ExampleController(IExampleService service) {
		this.service = service;
	}

	@ApiOperation(value = "Buscar examples por id", response = Example.class)
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
	public Example buscarExamplePorId(
			@ApiParam(name = "id", value = "Id do example", required = true, example = "1")@PathVariable Long id){
		return service.getExampleById(id);
	}

}
