package data_model.svr_requests;

import java.io.Serializable;

import data_model.Request;

public sealed interface ServerRequest<TClass extends Serializable> extends Request
	permits AuthenticateUser, SignUpRequest {}