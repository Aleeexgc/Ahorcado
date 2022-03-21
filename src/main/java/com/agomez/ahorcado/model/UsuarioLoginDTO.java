package com.agomez.ahorcado.model;


public class UsuarioLoginDTO {
	
	private String usuario;
	
	private String clave;

	
	public UsuarioLoginDTO() {
		
	}

	
	public UsuarioLoginDTO(String usuario, String clave) {
		
		this.usuario = usuario;
		this.clave = clave;
	}
	

	public String getUsuario() {
		return usuario;
	}

	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	
	public String getClave() {
		return clave;
	}

	
	public void setClave(String clave) {
		this.clave = clave;
	}
}
