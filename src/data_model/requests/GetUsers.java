package data_model.requests;

import java.util.ArrayList;

import data_model.User;

public record GetUsers() implements QueryRequest<ArrayList<String>> {}