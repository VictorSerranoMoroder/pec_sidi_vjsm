package data_model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
	
	private static final long serialVersionUID = 3L;
	public boolean isOnline;
	public boolean isBanned;
	public String username;
	public String password;
	
	
	
	public void setOnline(boolean online)
	{
		isOnline = online;
	}
	
	public void setBan(boolean ban)
	{
		isBanned = ban;
	}
	
	// Overrides needed to correctly compare users
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }
	
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
