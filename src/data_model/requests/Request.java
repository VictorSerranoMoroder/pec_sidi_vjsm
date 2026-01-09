package data_model.requests;

import java.io.Serializable;

public sealed interface Request extends Serializable
	permits QueryRequest, ProcedureRequest{}