package data_model.requests;

import data_model.User;

public record AddUser(User user) implements ProcedureRequest {}