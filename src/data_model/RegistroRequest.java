package data_model;

import java.io.Serializable;

public class RegistroRequest implements Serializable {
	private static final long serialVersionUID = 3L;
	public final User usuario;
    
    public RegistroRequest(String username, String password) {
    	usuario = new User();
        this.usuario.username = username;
        this.usuario.password = password;
    }
}
