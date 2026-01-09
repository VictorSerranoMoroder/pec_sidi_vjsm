package data_model.requests;

public record GetFollowers(String username) implements QueryRequest<String> {}
