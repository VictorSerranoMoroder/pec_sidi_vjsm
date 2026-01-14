package data_model.db_requests;

public record UnbanUser(String username) implements ProcedureRequest<String> {}
