package com.rcintra.vsm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rcintra.vsm.models.Cidade;
import com.rcintra.vsm.service.ClienteService;

@RestController
@RequestMapping("/api")
public class CidadeController {
	
	@Autowired
	private ClienteService service;

	@GetMapping("/cidades")
	public ResponseEntity<List<Cidade>> listarCidades() {
		return new ResponseEntity<>(service.findAllCidades(), HttpStatus.OK);
	}
	
	@PostMapping("/cidade")
	public ResponseEntity<Cidade> saveCidade(@RequestBody Cidade cidade) {
		return new ResponseEntity<>(service.saveCidade(cidade), HttpStatus.CREATED);
	}
}
