package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import data_model.Trino;
import data_model.User;

public class ModeloDatos {
	
	public record UserData(
		List<String> subscriptions,
		List<Trino> trinos
	) implements Serializable {
		
		public UserData() {
	        this(new ArrayList<>(), new ArrayList<>());
	    }
	};
	
	private List<User> users;
	private HashMap<String, UserData> userDataMap = new HashMap<>();
	
	public ModeloDatos()
	{
	}
	
	public boolean addUser(User usuario)
	{
		if (getUser(usuario.username).isPresent())
		{
			return false;
		}
	
		return users.add(usuario);
	}
	
	public boolean addSubscription(String userSource, String userTarget)
	{
		Optional<User> source = getUser(userSource);
		if (!source.isPresent())
		{
			return false;
		}
		
		Optional<User> target = getUser(userTarget);
		if (!source.isPresent())
		{
			return false;
		}
		
		Optional<UserData> userDataRegister = Optional.ofNullable(userDataMap.get(source.get().username));
		if (!userDataRegister.isPresent())
		{
			return false;
		}
		
		return userDataRegister.get().subscriptions.add(target.get().username);
	}
	
	public boolean addTrino(String userSource, String content)
	{
		Optional<User> source = getUser(userSource);
		if (!source.isPresent())
		{
			return false;
		}
		
		Optional<UserData> userDataRegister = Optional.ofNullable(userDataMap.get(source.get().username));
		if (!userDataRegister.isPresent())
		{
			return false;
		}
		
		return userDataRegister.get().trinos.add(new Trino(source.get().username, content));
	}
	
	public boolean editUser(User usuario)
	{
		Optional<User> foundUser = users.stream().filter(user -> user.username.equals(usuario)).findFirst();
		
		if (!foundUser.isPresent())
		{
			return false;
		}
		
		User existing = foundUser.get();
		existing.setOnline(usuario.isOnline);
		existing.setBan(usuario.isBanned);
		return true;
	}
	
	public boolean removeUser(User usuario)
	{
		return users.remove(usuario);
	}
	
	public Optional<User> getUser(String usuario)
	{
		return users.stream().filter(user -> user.username.equals(usuario)).findFirst();
	}
	
	public Optional<List<String>> getSubscriptions(String username)
	{
		return Optional.ofNullable(userDataMap.get(username).subscriptions);
	}
	
	public Optional<List<Trino>> getTrinos(String username)
	{
		return Optional.ofNullable(userDataMap.get(username).trinos);
	}
	
	
	
}
