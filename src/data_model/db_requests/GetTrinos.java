package data_model.db_requests;

public record GetTrinos(String username) implements QueryRequest<String> {}
