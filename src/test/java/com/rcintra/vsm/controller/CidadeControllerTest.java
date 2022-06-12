package com.rcintra.vsm.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcintra.vsm.models.Cidade;
import com.rcintra.vsm.service.ClienteService;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class CidadeControllerTest {
	
	private static final int TEST_CIDADE_ID = 1;

	@Autowired
	private MockMvc mockMvc;
	 
	@MockBean
	private ClienteService service;
	
	@BeforeEach
	void setup() {
	}
	 
	@Test
	public void dadoListaTodasCidades_whenGetAllClientes_thenReturnClientesList() throws Exception {
		List<Cidade> list = new ArrayList<>();
		list.add(Cidade.builder().nome("Assis").uf("SP").build());
		list.add(Cidade.builder().nome("Ourinhos").uf("SP").build());
		 
		given(service.findAllCidades()).willReturn(list);
		 
		ResultActions response = mockMvc.perform(get("/api/cidades"));
		 
		response.andExpect(status().isOk())
		 	.andDo(print())
		 	.andExpect(jsonPath("$.size()",
		 			is(list.size())));
	}
	 
	 
	@Test
	public void dadoCidadeId_whenGetCidadeById_thenReturnCidadeObject() throws Exception {
		long cidadeId = 1L;
		Cidade cidade = Cidade.builder().nome("Ourinhos").uf("SP").build();
		
		given(service.findCidadeById(cidadeId)).willReturn(cidade);

		ResultActions response = mockMvc.perform(get("/api/cidade/{id}", cidadeId));

		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.nome", is(cidade.getNome())))
			.andExpect(jsonPath("$.uf", is(cidade.getUf())));
	}
	 
	 
	@Test
	public void dadoInvalidoCidadeId_whenGetCidadeById_thenRetornaVazio() throws Exception{
		long cidadeId = 1L;

		given(service.findCidadeById(cidadeId)).willReturn(null);

		ResultActions response = mockMvc.perform(get("/api/cidade/{id}", cidadeId));

		response.andExpect(status().isNotFound())
	                .andDo(print());

	}
	
	@Test
	public void testCadastroSucess() throws Exception{
		
		Cidade cidade = Cidade.builder().nome("Ourinhos").uf("SP").build();
		
		mockMvc.perform( MockMvcRequestBuilders
				.post("/api/cidade")
				.content(asJsonString(cidade))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void testCadastroError() throws Exception{
		
		Cidade cidade = Cidade.builder().uf("SP").build();
		
		mockMvc.perform( MockMvcRequestBuilders
				.post("/api/cidade")
				.content(asJsonString(cidade))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	 
}
