package data_model.db_requests;

public record GetFeed(String username) implements QueryRequest<String> {}