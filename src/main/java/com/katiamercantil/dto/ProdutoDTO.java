package com.katiamercantil.dto;

import com.katiamercantil.enums.Departamento;

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
public class ProdutoDTO {
	
	@NotBlank
	private String codigo;
	
	@NotBlank
	private String descricao;
	
	@NotBlank
	private String dataEntrada;
	
	@NotBlank
	private String validade;
	
	@NotBlank
	private String volume;
	
	@NotBlank
	private Float quantidade;
	
	@NotBlank
	private Double precoCusto;
	
	@NotBlank
	private Double precoVenda;
	
	@NotBlank
	private String marca;
	
	@NotBlank
	private Long prateleira;
	
	@NotNull
	private Departamento departamento;
}
