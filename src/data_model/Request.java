package data_model;

import java.io.Serializable;

import data_model.db_requests.ProcedureRequest;
import data_model.db_requests.QueryRequest;
import data_model.svr_requests.ServerRequest;

public sealed interface Request<TClass extends Serializable> extends Serializable
	permits ServerRequest, QueryRequest, ProcedureRequest{}