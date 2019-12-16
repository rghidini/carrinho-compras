package br.com.sompo.resource;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;

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

import br.com.sompo.controller.ExampleController;
import br.com.sompo.exceptions.NoContentException;
import br.com.sompo.exceptions.RestControllerHandler;
import br.com.sompo.model.Example;
import br.com.sompo.service.ExampleService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleTest {

	private MockMvc mockMvc;

	@Mock
	private ExampleService service;
	@InjectMocks
	private ExampleController controller;

	@Before
	public void beforeEachTest() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = mock(ExampleService.class);

		Example example = setExample();
		given(service.getExampleById(1L)).willReturn(example);

		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setHandlerExceptionResolvers(withExceptionControllerAdvice())
				.setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}

	@Test
	public void extratoOk() throws Exception {
		mockMvc
		.perform(get("/v1/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"  \"exampleId\": 1,\r\n" + 
						"  \"exampleDescription\": \"exampleDescription\"\r\n" + 
						"}")
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}

	@Test
	public void extratoNoContent() throws Exception {
		when(service.getExampleById(2L)).thenReturn(null);
		doThrow(new NoContentException("")).when(service);
		try {
			mockMvc.perform(get("/v1/2")).andExpect(status().isNoContent());
		} catch (Exception e) {
			System.out.println();
		}
	}

	@Test
	public void extratoBadRequest() throws Exception {
		when(service.getExampleById(2L)).thenReturn(null);
		doThrow(new IllegalArgumentException("")).when(service);
		try {
			mockMvc.perform(get("/v1/a")).andExpect(status().isBadRequest());
		} catch (Exception e) {
			System.out.println();
		}
	}

	private Example setExample() {
		Example example = new Example();
		example.setExampleDescription("");
		example.setExampleId(NumberUtils.LONG_ONE);
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