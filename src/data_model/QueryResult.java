package data_model;

import java.io.Serializable;
import java.util.Optional;

public class QueryResult<TClass extends Serializable> implements Serializable{
	private static final long serialVersionUID = 3L;
	
	public final QueryRequest request;
	public final boolean success;
	public final TClass data;
	
	public QueryResult(QueryRequest request, boolean result, TClass data)
	{
		this.request = request;
		this.success = data != null;
		this.data = data;
	}
	
	public QueryResult(QueryRequest request, boolean result)
	{
		this.request = request;
	    this.success = result;
	    this.data = null;
	}
	
	public QueryResult(QueryRequest request, Optional<TClass> data)
	{
		this.request = request;
	    this.success = data.isPresent();
	    this.data = data.get();
	}
}
