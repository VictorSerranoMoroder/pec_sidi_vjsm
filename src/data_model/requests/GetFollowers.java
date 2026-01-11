package data_model.requests;

import java.util.ArrayList;

public record GetFollowers(String username) implements QueryRequest<ArrayList<String>> {}
