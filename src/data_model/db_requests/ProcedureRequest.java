package data_model.db_requests;

import java.io.Serializable;

import data_model.Request;

public sealed interface ProcedureRequest<TClass extends Serializable> extends Request
	permits AddUser, AddTrino, AddFollower, BanUser, UnbanUser, RemoveFollower, SetOnlineUser {}
