package data_model;

import java.io.Serializable;
import data_model.requests.Request;

public record SuccessResult<TClass extends Serializable>(
	    Request request,
	    TClass data
	) implements Result<TClass> {}