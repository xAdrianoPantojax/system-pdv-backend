package com.katiamercantil.dto;

import com.katiamercantil.model.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String sobrenome;
	
	@NotBlank
	private String cpf;
	
	@NotBlank
	private String rg;
	
	@NotBlank
	private String sexo;
	 
	@NotBlank
	private String dataNasc;
	
	@NotBlank
	private String dataAdmissao;
	
	private String dataRecisao;
	
	@NotBlank
	private String cargo;
	
	@NotBlank
	private String setor;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	private String telefone;
	
	@NotBlank
	private String celular;
	
	private Boolean status;
	
	private Boolean administrador;
	
	@NotBlank
	private String cep;
	
	@NotNull
	private Endereco endereco;
}
