package data_model;

import data_model.requests.Request;

public record ErrorResult(
	    Request request,
	    String message
	) implements Result {}
