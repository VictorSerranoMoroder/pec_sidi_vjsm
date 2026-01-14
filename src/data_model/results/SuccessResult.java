package data_model.results;

import java.io.Serializable;

import data_model.Request;

public record SuccessResult<TClass extends Serializable>(
	    Request request,
	    TClass data
	) implements Result<TClass> {}