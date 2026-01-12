package data_model.requests;

import java.io.Serializable;

public sealed interface ProcedureRequest<TClass extends Serializable> extends Request
	permits AddUser, AddTrino, AddFollower, BanUser, UnbanUser, RemoveFollower {}
