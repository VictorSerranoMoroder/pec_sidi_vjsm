package data_model.requests;

import data_model.Trino;

public record AddTrino(Trino trino) implements ProcedureRequest {}
