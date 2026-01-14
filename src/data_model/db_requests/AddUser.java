package data_model.db_requests;

import data_model.User;

public record AddUser(User user) implements ProcedureRequest {}