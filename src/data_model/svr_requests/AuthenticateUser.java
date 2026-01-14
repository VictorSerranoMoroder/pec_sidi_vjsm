package data_model.svr_requests;

public record AuthenticateUser(String username, String password) implements ServerRequest {}