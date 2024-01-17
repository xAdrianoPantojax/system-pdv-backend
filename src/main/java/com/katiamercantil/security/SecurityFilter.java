package com.katiamercantil.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.katiamercantil.repository.ClienteRepository;
import com.katiamercantil.repository.FuncionarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var tokenJWT = recuperarToken(request);
		
		if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            var cliente = clienteRepository.findByEmail(subject);
            var funcionario = funcionarioRepository.findByEmail(subject);

            // Verifica se é um cliente
            if (cliente != null) {
                var authentication = new UsernamePasswordAuthenticationToken(cliente, null, cliente.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // Verifica se é um funcionário
            else if (funcionario != null) {
                var authentication = new UsernamePasswordAuthenticationToken(funcionario, null, funcionario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
		
		
		
		
		
/* 		if(tokenJWT != null) {
			var subject = tokenService.getSubject(tokenJWT);
			var cliente = clienteRepository.findByEmail(subject);
			var authentication = new UsernamePasswordAuthenticationToken(cliente, null, cliente.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} */

		/* if(tokenJWT != null) {
			var subject = tokenService.getSubject(tokenJWT);
			var funcionario = funcionarioRepository.findByEmail(subject);
			var authentication = new UsernamePasswordAuthenticationToken(funcionario, null, funcionario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} */
		
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		return null;
	}
}
