package data_model.requests;

import data_model.User;

public record GetUser(String username) implements QueryRequest<User> {}