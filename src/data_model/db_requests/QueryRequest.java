package data_model.db_requests;

import java.io.Serializable;

import data_model.Request;

public sealed interface QueryRequest<TClass extends Serializable> extends Request 
	permits GetUser, GetUsers, GetFeed, GetFollowers, GetAllTrinos, GetTrinos {}
