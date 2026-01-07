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
	    UNBAN_USER,
	    ADD_TRINO
    }
	
	public final QueryType queryType;
	public final ProcedureType procedureType;
	public final Serializable payload;      
    
    public QueryRequest(QueryType queryType, Serializable payload) {
        this.queryType = queryType;
        this.procedureType = null;
        this.payload = payload; 
    }
    
    public QueryRequest(ProcedureType procedureType, Serializable payload) {
        this.queryType = null;
        this.procedureType = procedureType;
        this.payload = payload;
    }
}
