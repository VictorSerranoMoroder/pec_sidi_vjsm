package data_model.results;

import java.io.Serializable;

public sealed interface Result<TClass extends Serializable> extends Serializable
	permits SuccessResult, ErrorResult {};