package com.katiamercantil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.katiamercantil.dto.AutenticacaoDTO;
import com.katiamercantil.model.Cliente;
import com.katiamercantil.model.Funcionario;
import com.katiamercantil.security.DadosTokenJWTDTO;
import com.katiamercantil.security.TokenService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private TokenService tokenService;
	
	@PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody AutenticacaoDTO dados) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getPassword());
            var authentication = manager.authenticate(authenticationToken);

            if (authentication.getPrincipal() instanceof Cliente) {
                var tokenJWT = tokenService.gerarToken((Cliente) authentication.getPrincipal());
                return ResponseEntity.ok(new DadosTokenJWTDTO(tokenJWT));
            } else if (authentication.getPrincipal() instanceof Funcionario) {
                var tokenJWT = tokenService.gerarTokenFuncionario((Funcionario) authentication.getPrincipal());
                return ResponseEntity.ok(new DadosTokenJWTDTO(tokenJWT));
            } else {
                // Se o tipo não for reconhecido, retorne um erro
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (BadCredentialsException | DisabledException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





/* 	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody ClienteDTO dados) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha());
		var authentication = manager.authenticate(authenticationToken);
		
		var tokenJWT = tokenService.gerarToken((Cliente) authentication.getPrincipal());
		return ResponseEntity.ok(new DadosTokenJWTDTO(tokenJWT));
	} */

	/* @PostMapping
	public ResponseEntity efetuarLoginFuncionario(@RequestBody FuncionarioDTO dados) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha());
		var authentication = manager.authenticate(authenticationToken);
		
		var tokenJWT = tokenService.gerarTokenFuncionario((Funcionario) authentication.getPrincipal());
		return ResponseEntity.ok(new DadosTokenJWTDTO(tokenJWT));
	} */

}
