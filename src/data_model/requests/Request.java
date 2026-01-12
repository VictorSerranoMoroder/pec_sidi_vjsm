package data_model.requests;

import java.io.Serializable;

public sealed interface Request<TClass extends Serializable> extends Serializable
	permits QueryRequest, ProcedureRequest{}