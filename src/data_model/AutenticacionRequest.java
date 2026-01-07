package data_model;

import java.io.Serializable;

public final class AutenticacionRequest implements Serializable {
	private static final long serialVersionUID = 3L;
	public final User usuario;
    
    public AutenticacionRequest(String username, String passwordHash) {
        usuario = new User();
    	this.usuario.username = username;
        this.usuario.password = passwordHash;
    }
}
