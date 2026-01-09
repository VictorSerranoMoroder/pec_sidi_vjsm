package data_model;

import java.io.Serializable;

public sealed interface Result<TClass extends Serializable> extends Serializable
permits SuccessResult, ErrorResult {};