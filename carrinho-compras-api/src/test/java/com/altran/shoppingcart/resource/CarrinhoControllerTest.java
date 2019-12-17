package com.altran.shoppingcart.resource;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.altran.shoppingcart.controller.CarrinhoController;
import com.altran.shoppingcart.exceptions.NoContentException;
import com.altran.shoppingcart.exceptions.RestControllerHandler;
import com.altran.shoppingcart.model.Carrinho;
import com.altran.shoppingcart.model.vo.ItemCarrinhoVO;
import com.altran.shoppingcart.service.CarrinhoService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarrinhoControllerTest {

	private static final String CONTENT = "{\r\n" + 
			"  \"idUsuario\": \"teste\",\r\n" +
			"  \"idCarrinho\": \"1\",\r\n" + 
			"  \"itens\": [\r\n" + 
			"    {\r\n" + 
			"      \"nome\": \"abacate\",\r\n" + 
			"      \"quantidade\": 10,\r\n" + 
			"      \"valor\": 10\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"nome\": \"manga\",\r\n" + 
			"      \"quantidade\": 10,\r\n" + 
			"      \"valor\": 9\r\n" + 
			"    },\r\n" +
			"    {\r\n" + 
			"      \"nome\": \"limão\",\r\n" + 
			"      \"quantidade\": 10,\r\n" + 
			"      \"valor\": 7\r\n" + 
			"    }\r\n" + 
			"  ],\r\n" + 
			"  \"valorTotal\": 260\r\n" + 
			"}";

	private MockMvc mockMvc;

	@Mock
	private CarrinhoService service;
	@InjectMocks
	private CarrinhoController controller;
	

	@Before
	public void beforeEachTest() {
		MockitoAnnotations.initMocks(this);
		service = mock(CarrinhoService.class);

		List<Carrinho> listCarrinho = new ArrayList<>();
		listCarrinho.add(this.setCarrinho());
		listCarrinho.add(this.setCarrinho());
		
		given(service.getCarrinhoByUserId("asdasda")).willReturn(listCarrinho);

		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setHandlerExceptionResolvers(withExceptionControllerAdvice())
				.setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}

	@Test
	public void getByIdOk() throws Exception {
		mockMvc
		.perform(get("/carrinho/v1/teste")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}

	@Test
	public void getByIdNoContent() throws Exception {
		when(service.getCarrinhoByUserId("teste")).thenReturn(null);
		doThrow(new NoContentException("")).when(service);
		try {
			mockMvc.perform(get("/carrinho/v1/2")).andExpect(status().isNoContent());
		} catch (Exception e) {
		}
	}
	
	@Test
	public void createOk() throws Exception {
		mockMvc
		.perform(post("/carrinho/v1/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(CONTENT)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}
	
	@Test
	public void updateOk() throws Exception {
		mockMvc
		.perform(put("/carrinho/v1/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(CONTENT)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}
	
	private Carrinho setCarrinho() {
		Carrinho example = new Carrinho();
		example.setId(NumberUtils.LONG_ONE);
		example.setIdUsuario("teste");
		List<ItemCarrinhoVO> itens = new ArrayList<>();
		itens.add(new ItemCarrinhoVO());
		example.setItens(itens);
		example.setValorTotal(BigDecimal.TEN);
		return example;
	}
	
	private ExceptionHandlerExceptionResolver withExceptionControllerAdvice() {
		final ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
			@Override
			protected ServletInvocableHandlerMethod getExceptionHandlerMethod(final HandlerMethod handlerMethod,
					final Exception exception) {
				Method method = new ExceptionHandlerMethodResolver(RestControllerAdvice.class).resolveMethod(exception);
				if (method != null) {
					return new ServletInvocableHandlerMethod(new RestControllerHandler(), method);
				}
				return super.getExceptionHandlerMethod(handlerMethod, exception);
			}
		};
		exceptionResolver.afterPropertiesSet();
		return exceptionResolver;
	}

}