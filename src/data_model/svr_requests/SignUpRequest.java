package data_model.svr_requests;

public record SignUpRequest(String username, String password) implements ServerRequest {}