package data_model.db_requests;

import java.util.ArrayList;

public record GetFollowers(String username) implements QueryRequest<ArrayList<String>> {}
