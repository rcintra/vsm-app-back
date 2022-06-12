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

import com.rcintra.vsm.models.Cidade;
import com.rcintra.vsm.service.ClienteService;

@RestController
@RequestMapping("/api")
public class CidadeController extends BaseController {
	
	private static final String MSG_CIDADE_NOT_FOUND = "Cidade não encontrada.";
	
	@Autowired
	private ClienteService service;

	@GetMapping("/cidades")
	public ResponseEntity<List<Cidade>> listarCidades() {
		return new ResponseEntity<>(service.findAllCidades(), HttpStatus.OK);
	}
	
	@GetMapping("/cidade/{id}")
	public ResponseEntity<Cidade> consultarCidadePorId(@PathVariable Long id) {
		
		Cidade cidadeEncontrada = service.findCidadeById(id);
		
		if (ObjectUtils.isEmpty(cidadeEncontrada)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CIDADE_NOT_FOUND);
		}
		
		return new ResponseEntity<>(cidadeEncontrada, HttpStatus.OK);
	}
	
	@PostMapping("/cidade")
	public ResponseEntity<Cidade> saveCidade(@Valid @RequestBody Cidade cidade) {
		try {
			return new ResponseEntity<>(service.saveCidade(cidade), HttpStatus.CREATED);
		} catch (DataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/cidade/{id}")
	public ResponseEntity<Cidade> updateCliente(@Valid @PathVariable Long id, @RequestBody Cidade cidade) {
		Cidade cidadeEncontrada = service.findCidadeById(id);
		
		if (ObjectUtils.isEmpty(cidadeEncontrada)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CIDADE_NOT_FOUND);
		}
		
		try {
			
			cidadeEncontrada.setNome(cidade.getNome());
			cidadeEncontrada.setUf(cidade.getUf());
			
			return new ResponseEntity<>(
					service.saveCidade(cidadeEncontrada), 
					HttpStatus.CREATED
			);
		
		} catch(DataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
