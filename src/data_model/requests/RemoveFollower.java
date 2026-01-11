package data_model.requests;

public record RemoveFollower(String userSource, String userTarget) implements ProcedureRequest {}