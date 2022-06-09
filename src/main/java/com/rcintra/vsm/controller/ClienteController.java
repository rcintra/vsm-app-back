package com.rcintra.vsm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rcintra.vsm.models.Cliente;
import com.rcintra.vsm.service.ClienteService;

@RestController
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> listarClientes() {
		return new ResponseEntity<>(service.findAllClientes(), HttpStatus.OK);
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> consultarClientePorId(@PathVariable Long id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}

	@PostMapping("/cliente")
	public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
		return new ResponseEntity<>(service.saveCliente(cliente), HttpStatus.CREATED);
	}
	
	@PutMapping("/cliente/{id}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
		Cliente clienteEncontrado = service.findById(id);
		
		if (clienteEncontrado == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			
			clienteEncontrado.setNome(cliente.getNome());
			clienteEncontrado.setCidade(cliente.getCidade());
			
			return new ResponseEntity<>(service.saveCliente(clienteEncontrado), HttpStatus.CREATED);
		
		} catch(DataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
}
