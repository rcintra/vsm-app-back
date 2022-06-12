package com.rcintra.vsm.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
	
	public List<Cliente> findAllClientes(String cpfCnpj) {
		if (StringUtils.isBlank(cpfCnpj)) 
			return clienteRepository.findAll();
		return clienteRepository.findByCpfCnpj(cpfCnpj);
	}
	
	public List<Cidade> findAllCidades() {
		return cidadeRepository.findAll();
	}

	public Cidade saveCidade(Cidade cidade) {
		return cidadeRepository.save(cidade);
	}
	
	public Cliente saveCliente(Cliente cliente) {
		cliente.setHabilitado(Boolean.TRUE);
		Cidade cidade = cliente.getCidade();
		cliente.setCidade(null);
		if (cidade != null && cidade.getId() != null) {
			cliente.setCidade(findCidadeById(cidade.getId()));
		}
		return clienteRepository.save(cliente);
	}
	
	public Cliente updateCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Cliente findClienteById(final Long id) {
		Cliente cliente = null;
		Optional<Cliente> opt = clienteRepository.findById(id);
		if (opt.isPresent()) 
			cliente = opt.get();
		return cliente;
	}
	
	public Cidade findCidadeById(final Long id) {
		Cidade cidade = null;
		Optional<Cidade> opt = cidadeRepository.findById(id);
		if (opt.isPresent()) 
			cidade = opt.get();
		return cidade;
	}
}
