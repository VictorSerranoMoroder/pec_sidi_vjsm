package data_model;

import java.io.Serializable;

public class QueryRequest implements Serializable{

	private static final long serialVersionUID = 3L;
	
	public enum QueryType {
        GET_USER,
        GET_FOLLOWERS,
        GET_TRINOS,
        GET_FEED
    }
	
	public enum ProcedureType {
		ADD_USER,
	    REMOVE_USER,
	    BAN_USER,
	    ADD_TRINO
    }
	
	public final QueryType queryType;
	public final ProcedureType procedureType;
	public final Serializable payload;      
	
	public final String usernameFilter; // optional, depending on query
	public final Integer maxResults;    // optional
    
    public QueryRequest(QueryType queryType, String usernameFilter, Integer maxResults) {
        this.queryType = queryType;
        this.usernameFilter = usernameFilter;
        this.maxResults = maxResults;
        this.procedureType = null;
        this.payload = null; 
    }
    
    public QueryRequest(ProcedureType procedureType, Serializable payload) {
        this.queryType = null;
        this.usernameFilter = null;
        this.maxResults = null;
        this.procedureType = procedureType;
        this.payload = payload;
    }
}
