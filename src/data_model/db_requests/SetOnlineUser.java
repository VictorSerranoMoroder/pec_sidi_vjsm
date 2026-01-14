package data_model.db_requests;

public record SetOnlineUser(String username, boolean online) implements ProcedureRequest {}
