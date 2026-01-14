package data_model.db_requests;

public record BanUser(String username) implements ProcedureRequest {}
