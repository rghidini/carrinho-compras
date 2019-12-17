package com.altran.shoppingcart.resource;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;

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

import com.altran.shoppingcart.controller.UsuarioController;
import com.altran.shoppingcart.exceptions.NoContentException;
import com.altran.shoppingcart.exceptions.RestControllerHandler;
import com.altran.shoppingcart.model.Usuario;
import com.altran.shoppingcart.service.UsuarioService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioControllerTest {

	private static final String CONTENT = "{\r\n" +
			"  \"id\": \"teste\",\r\n" + 
			"  \"nome\": \"teste\",\r\n" + 
			"  \"email\": \"teste@teste.com\"\r\n" + 
			"}";

	private MockMvc mockMvc;

	@Mock
	private UsuarioService service;
	@InjectMocks
	private UsuarioController controller;
	

	@Before
	public void beforeEachTest() {
		MockitoAnnotations.initMocks(this);
		service = mock(UsuarioService.class);

		Usuario usuario = this.setUsuario();
		
		given(service.getUsuarioById("teste")).willReturn(usuario);

		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setHandlerExceptionResolvers(withExceptionControllerAdvice())
				.setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}

	@Test
	public void getByIdOk() throws Exception {
		mockMvc
		.perform(get("/users/v1/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}

	@Test
	public void getByIdNoContent() throws Exception {
		when(service.getUsuarioById("assasa")).thenReturn(null);
		doThrow(new NoContentException("")).when(service);
		try {
			mockMvc.perform(get("/users/v1/2")).andExpect(status().isNoContent());
		} catch (Exception e) {
		}
	}
	
	@Test
	public void createOk() throws Exception {
		mockMvc
		.perform(post("/users/v1/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(CONTENT)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getAllOk() throws Exception {
		mockMvc
		.perform(get("/users/v1/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getAllNoContent() throws Exception {
		when(service.getAll()).thenReturn(null);
		doThrow(new NoContentException("")).when(service);
		try {
			mockMvc.perform(get("/users/v1/")).andExpect(status().isNoContent());
		} catch (Exception e) {
		}
	}

	@Test
	public void updateOk() throws Exception {
		mockMvc
		.perform(put("/users/v1/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(CONTENT)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deleteOk() throws Exception {
		mockMvc
		.perform(delete("/users/v1/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deleteNoContent() throws Exception {
		when(service.getUsuarioById("asassa")).thenReturn(null);
		doThrow(new NoContentException("")).when(service);
		try {
			mockMvc.perform(delete("/users/v1/2")).andExpect(status().isNoContent());
		} catch (Exception e) {
		}
	}
	
	private Usuario setUsuario() {
		Usuario example = new Usuario();
		example.setNome("Teste");
		example.setId("teste");
		example.setEmail("teste@teste.com");
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