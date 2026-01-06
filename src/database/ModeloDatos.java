package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import data_model.Trino;
import data_model.User;

public class ModeloDatos {
	
	private List<User> users = new ArrayList<>();
	private HashMap<User, List<User>> subscriptionMap = new HashMap<>();
	private HashMap<User, List<Trino>> trinoMap = new HashMap<>();
	
	public ModeloDatos()
	{
	}
	
	public boolean addUser(User usuario)
	{
		if (getUser(usuario.username).isPresent())
		{
			return false;
		}
		users.add(usuario);
		subscriptionMap.put(usuario, new ArrayList<>());
		trinoMap.put(usuario, new ArrayList<>());
		return true;
	}
	
	public Optional<User> getUser(String usuario)
	{
		Optional<User> foundUser = users.stream().filter(user -> user.username.equals(usuario)).findFirst();
		return foundUser;
	}
}
