package com.katiamercantil.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.katiamercantil.dto.FuncionarioUpdateDTO;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Funcionario implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String sobrenome;
	
	private String cpf;
	
	private String rg;
	
	private String sexo;
	 
	private String dataNasc;
	
	private String dataAdmissao;
	
	private String dataRecisao;
	
	private String cargo;
	
	private String email;
	
	private String password;
	
	private String celular;
	
	private Boolean status;
	
	@Embedded
	private Endereco endereco;

	public void updateInfo(FuncionarioUpdateDTO funcionarioUpdateDTO){
		
		if (funcionarioUpdateDTO.getNome() != null) {
			this.nome = funcionarioUpdateDTO.getNome();
		}

		if (funcionarioUpdateDTO.getSobrenome() != null) {
			this.sobrenome = funcionarioUpdateDTO.getSobrenome();
		}

		if (funcionarioUpdateDTO.getCpf() != null) {
			this.cpf = funcionarioUpdateDTO.getCpf();
		}

		if (funcionarioUpdateDTO.getRg() != null) {
			this.rg = funcionarioUpdateDTO.getRg();
		}

		if (funcionarioUpdateDTO.getSexo() != null) {
			this.sexo = funcionarioUpdateDTO.getSexo();
		}

		if (funcionarioUpdateDTO.getDataRecisao() != null) {
			this.dataRecisao = funcionarioUpdateDTO.getDataRecisao();
		}

		if (funcionarioUpdateDTO.getCargo() != null) {
			this.cargo = funcionarioUpdateDTO.getCargo();
		}

		if (funcionarioUpdateDTO.getEmail() != null) {
			this.email = funcionarioUpdateDTO.getEmail();
		}

		if (funcionarioUpdateDTO.getPassword() != null) {
			this.password = funcionarioUpdateDTO.getPassword();
		}

		if (funcionarioUpdateDTO.getCelular() != null) {
			this.celular = funcionarioUpdateDTO.getCelular();
		}

		if (funcionarioUpdateDTO.getStatus() != null) {
			this.status = funcionarioUpdateDTO.getStatus();
		}

		if (funcionarioUpdateDTO.getEndereco() != null) {
			this.endereco.updateInfoEnd(funcionarioUpdateDTO.getEndereco());
		}
	
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
