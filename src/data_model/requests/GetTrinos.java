package data_model.requests;

public record GetTrinos(String username) implements QueryRequest<String> {}
