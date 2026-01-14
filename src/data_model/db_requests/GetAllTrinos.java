package data_model.db_requests;

import java.util.ArrayList;

import data_model.Trino;

public record GetAllTrinos() implements QueryRequest<ArrayList<Trino>> {}
