package com.rcintra.vsm.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcintra.vsm.models.Cliente;
import com.rcintra.vsm.service.ClienteService;

@WebMvcTest
public class ClienteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClienteService service;

	@Test
	public void dadoListaTodosClientes_whenGetAllClientes_thenReturnClientesList() throws Exception {
		List<Cliente> list = new ArrayList<>();
		list.add(Cliente.builder().nome("Rafael").cpfCnpj("11111111").build());
		list.add(Cliente.builder().nome("Joao").cpfCnpj("2222222").build());

		given(service.findAllClientes()).willReturn(list);

		ResultActions response = mockMvc.perform(get("/api/clientes"));

		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(list.size())));

	}
	
	@Test
	public void dadoCidadeId_whenGetCidadeById_thenReturnCidadeObject() throws Exception {
		 long clienteId = 1L;
		 Cliente cliente = Cliente.builder().nome("Rafael").cpfCnpj("11111111").build();
		
		 given(service.findClienteById(clienteId)).willReturn(cliente);

		 ResultActions response = mockMvc.perform(get("/api/cliente/{id}", clienteId));

		 response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.nome", is(cliente.getNome())))
			.andExpect(jsonPath("$.cpfCnpj", is(cliente.getCpfCnpj())));
	 }
	 
	 @Test
	 public void dadoInvalidoCidadeId_whenGetClienteById_thenRetornaVazio() throws Exception{
		 long clienteId = 1L;

		 given(service.findClienteById(clienteId)).willReturn(null);

		 ResultActions response = mockMvc.perform(get("/api/cliente/{id}", clienteId));

		 response.andExpect(status().isNotFound())
	                .andDo(print());

	 }
	 
		@Test
		public void testCadastroSucess() throws Exception{
			
			 Cliente cliente = Cliente.builder().nome("Rafael").cpfCnpj("11111111").build();
			
			mockMvc.perform( MockMvcRequestBuilders
					.post("/api/cliente")
					.content(asJsonString(cliente))
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated());
		}
		
		@Test
		public void testCadastroError() throws Exception{
			
			 Cliente cliente = Cliente.builder().nome("Rafael").build();
			
			mockMvc.perform( MockMvcRequestBuilders
					.post("/api/cliente")
					.content(asJsonString(cliente))
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
