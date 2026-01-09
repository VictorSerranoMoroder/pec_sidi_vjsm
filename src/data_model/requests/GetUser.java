package data_model.requests;

public record GetUser(String username) implements QueryRequest<String> {}