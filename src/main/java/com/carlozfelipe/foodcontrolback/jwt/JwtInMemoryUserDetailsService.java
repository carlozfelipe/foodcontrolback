package com.carlozfelipe.foodcontrolback.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.carlozfelipe.foodcontrolback.dao.UsuarioDao;
import com.carlozfelipe.foodcontrolback.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioDao usuarioDao;

	static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();

	static {
		inMemoryUserList.add(new JwtUserDetails(1L, "in28minutes",
				"$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e", "ROLE_USER_2"));
		inMemoryUserList.add(new JwtUserDetails(2L, "ranga",
				"$2a$10$IetbreuU5KihCkDB6/r1DOJO0VyU9lSiBcrMDT.biU7FOt2oqZDPm", "ROLE_USER_2"));
		
		//$2a$10$IetbreuU5KihCkDB6/r1DOJO0VyU9lSiBcrMDT.biU7FOt2oqZDPm
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> opUsuario = usuarioDao.findById(Long.parseLong(username));


		if (!opUsuario.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}
		Usuario usuario = opUsuario.get();
		JwtUserDetails jwtUserDetails = new JwtUserDetails(usuario.getId(), username, usuario.getPass(), usuario.getTipo().name());
		return jwtUserDetails;
	}

}
