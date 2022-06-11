package com.rcintra.vsm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cpfCnpj;
	
	private String endereco;
	private Integer numero;
	private String bairro;
	private String telefone;
	private String email;
	private String cep;
	
	private Boolean habilitado;
	
	@ManyToOne
	@JoinColumn(name = "cidade.id")
	private Cidade cidade;
	
/*	
	*Nome:
		*CPF/CNPJ:
		Endereço:
		Número:
		Bairro:
		Telefone:
		E-mail:
		CEP:
		Cidade:
		UF:
		 */
	
	
}
