package data_model;

import java.io.Serializable;

public final class AutenticacionRequest implements Serializable {
	private static final long serialVersionUID = 3L;
	public final String username;
	public final String password;
    
    public AutenticacionRequest(String username, String passwordHash) {
        this.username = username;
        this.password = passwordHash;
    }
}
