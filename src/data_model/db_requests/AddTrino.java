package data_model.db_requests;

import java.util.ArrayList;

import data_model.Trino;

public record AddTrino(Trino trino) implements ProcedureRequest {}
