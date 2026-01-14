package data_model.results;

import data_model.Request;

public record ErrorResult(
	    Request request,
	    String message
	) implements Result {}
