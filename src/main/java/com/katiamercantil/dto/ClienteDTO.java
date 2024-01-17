package com.katiamercantil.dto;

import java.util.Date;

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
public class ClienteDTO {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String sobrenome;
	
	@NotBlank
	private String cpfCnpj;
	
	@NotBlank
	private String sexo;
	
	@NotNull
	private Date dataNascimento;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String senha;
	 
	@NotBlank
	private String telefone;
	
	private Boolean status;
	
	@NotNull 
	private Endereco endereco;
	
}
