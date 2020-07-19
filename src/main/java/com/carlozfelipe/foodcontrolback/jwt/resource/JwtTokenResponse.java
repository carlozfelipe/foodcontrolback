package com.carlozfelipe.foodcontrolback.jwt.resource;

import com.carlozfelipe.foodcontrolback.enums.UsuarioEnum;

import java.io.Serializable;

public class JwtTokenResponse implements Serializable {

	private static final long serialVersionUID = 8317676219297719109L;

	private final String token;
	private UsuarioEnum usuarioEnum;

	public UsuarioEnum getUsuarioEnum() {
		return usuarioEnum;
	}

	public void setUsuarioEnum(UsuarioEnum usuarioEnum) {
		this.usuarioEnum = usuarioEnum;
	}

	public JwtTokenResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}