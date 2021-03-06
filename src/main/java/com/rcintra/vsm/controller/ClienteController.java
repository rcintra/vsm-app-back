package com.rcintra.vsm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rcintra.vsm.models.Cliente;
import com.rcintra.vsm.service.ClienteService;

@RestController
@RequestMapping("/api")
public class ClienteController extends BaseController {
	
	private static final String MSG_CLIENTE_NOT_FOUND = "Cliente não encontrado.";

	@Autowired
	private ClienteService service;

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> listarClientes() {
		return new ResponseEntity<>(service.findAllClientes(), HttpStatus.OK);
	}
	
	@GetMapping("/clientes/cpfcnpj/{cpfCnpj}")
	public ResponseEntity<List<Cliente>> listarClientesPorCpfCnpj(@PathVariable String cpfCnpj) {
		
		List<Cliente> clienteEncontrado = service.findAllClientes(cpfCnpj);
		
		if (ObjectUtils.isEmpty(clienteEncontrado)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CLIENTE_NOT_FOUND);
		}
		
		return new ResponseEntity<>(clienteEncontrado, HttpStatus.OK);
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> consultarClientePorId(@PathVariable Long id) {
		
		Cliente clienteEncontrado = service.findClienteById(id);
		
		if (ObjectUtils.isEmpty(clienteEncontrado)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CLIENTE_NOT_FOUND);
		}
		
		return new ResponseEntity<>(clienteEncontrado, HttpStatus.OK);
	}

	@PostMapping("/cliente")
	public ResponseEntity<Cliente> saveCliente(@Valid @RequestBody Cliente cliente) {
		return new ResponseEntity<>(service.saveCliente(cliente), HttpStatus.CREATED);
	}
	
	@PutMapping("/cliente/{id}")
	public ResponseEntity<Cliente> updateCliente(@Valid @PathVariable Long id, @RequestBody Cliente cliente) {
		Cliente clienteEncontrado = service.findClienteById(id);
		
		if (ObjectUtils.isEmpty(clienteEncontrado)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CLIENTE_NOT_FOUND);
		}
		
		try {
			
				clienteEncontrado.setNome(cliente.getNome());
				//clienteEncontrado.setCpfCnpj(); nao permite alterar o cpf/cnpj
				clienteEncontrado.setEndereco(cliente.getEndereco());
				clienteEncontrado.setNumero(cliente.getNumero());
				clienteEncontrado.setBairro(cliente.getBairro());
				clienteEncontrado.setCep(cliente.getCep());
				clienteEncontrado.setTelefone(cliente.getTelefone());
				clienteEncontrado.setEmail(cliente.getEmail());
				clienteEncontrado.setCidade(cliente.getCidade());	
			
			return new ResponseEntity<>(service.updateCliente(clienteEncontrado), HttpStatus.CREATED);
		
		} catch(DataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PutMapping("/cliente/status/{id}")
	public ResponseEntity<Cliente> updateStatusCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
		Cliente clienteEncontrado = service.findClienteById(id);
		
		if (ObjectUtils.isEmpty(clienteEncontrado)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CLIENTE_NOT_FOUND);
		}
		
		try {
			
			clienteEncontrado.setHabilitado(clienteEncontrado.getHabilitado() ? Boolean.FALSE : Boolean.TRUE);
			
			return new ResponseEntity<>(service.updateCliente(clienteEncontrado), HttpStatus.CREATED);
		
		} catch(DataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
