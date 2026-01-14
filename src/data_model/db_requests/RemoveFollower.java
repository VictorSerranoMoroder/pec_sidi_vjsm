package data_model.db_requests;

public record RemoveFollower(String userSource, String userTarget) implements ProcedureRequest {}