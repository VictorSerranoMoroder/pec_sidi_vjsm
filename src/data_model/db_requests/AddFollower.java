package data_model.db_requests;

public record AddFollower(String userSource, String userTarget) implements ProcedureRequest {}