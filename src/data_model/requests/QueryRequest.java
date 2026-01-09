package data_model.requests;

import java.io.Serializable;

public sealed interface QueryRequest<TClass extends Serializable> extends Request 
	permits GetUser, GetUsers, GetFeed, GetFollowers, GetTrinos {}
