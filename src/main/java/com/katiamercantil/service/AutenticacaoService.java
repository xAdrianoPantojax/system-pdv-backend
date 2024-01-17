package com.katiamercantil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.katiamercantil.repository.ClienteRepository;
import com.katiamercantil.repository.FuncionarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
    private FuncionarioRepository funcionarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 UserDetails cliente = clienteRepository.findByEmail(username);
        if (cliente != null) {
            return cliente;
        }

        // Tenta autenticar como funcionário
        UserDetails funcionario = funcionarioRepository.findByEmail(username);
        if (funcionario != null) {
            return funcionario;
        }

        // Se não encontrou cliente nem funcionário, lança exceção
        throw new UsernameNotFoundException("Usuário não encontrado");
    }

}


