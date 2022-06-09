package com.rcintra.vsm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcintra.vsm.models.Cidade;
import com.rcintra.vsm.models.Cliente;
import com.rcintra.vsm.repository.CidadeRepository;
import com.rcintra.vsm.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> findAllClientes() {
		return clienteRepository.findAll();
	}
	
	public List<Cidade> findAllCidades() {
		return cidadeRepository.findAll();
	}

	public Cidade saveCidade(Cidade cidade) {
		return cidadeRepository.save(cidade);
	}
	
	public Cliente saveCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Cliente findById(final Long id) {
		Cliente cliente = null;
		Optional<Cliente> opt = clienteRepository.findById(id);
		if (opt.isPresent()) 
			cliente = opt.get();
		return cliente;
	}
}
