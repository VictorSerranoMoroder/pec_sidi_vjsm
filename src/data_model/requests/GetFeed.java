package data_model.requests;

public record GetFeed(String username) implements QueryRequest<String> {}