package data_model.requests;

public record AddFollower(String userSource, String userTarget) implements ProcedureRequest {}
