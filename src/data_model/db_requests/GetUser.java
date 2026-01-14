package data_model.db_requests;

import data_model.User;

public record GetUser(String username) implements QueryRequest<User> {}