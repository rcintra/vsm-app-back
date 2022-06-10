package com.rcintra.vsm.controller;

import java.util.List;

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
public class ClienteController {
	
	private static final String MSG_CLIENTE_NOT_FOUND = "Cliente não encontrado";

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
		
		Cliente clienteEncontrado = service.findById(id);
		
		if (ObjectUtils.isEmpty(clienteEncontrado)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CLIENTE_NOT_FOUND);
		}
		
		return new ResponseEntity<>(clienteEncontrado, HttpStatus.OK);
	}

	@PostMapping("/cliente")
	public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
		return new ResponseEntity<>(service.saveCliente(cliente), HttpStatus.CREATED);
	}
	
	@PutMapping("/cliente/{id}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
		Cliente clienteEncontrado = service.findById(id);
		
		if (ObjectUtils.isEmpty(clienteEncontrado)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CLIENTE_NOT_FOUND);
		}
		
		try {
			
			if (cliente != null && cliente.getId() != null) {
				clienteEncontrado.setNome(cliente.getNome());
				clienteEncontrado.setCidade(cliente.getCidade());				
			} else {
				clienteEncontrado.setHabilitado(clienteEncontrado.getHabilitado() ? Boolean.FALSE : Boolean.TRUE);
			}
			
			return new ResponseEntity<>(service.saveCliente(clienteEncontrado), HttpStatus.CREATED);
		
		} catch(DataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
}
