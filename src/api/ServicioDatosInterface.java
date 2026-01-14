package api;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import data_model.Request;
import data_model.db_requests.ProcedureRequest;
import data_model.db_requests.QueryRequest;
import data_model.results.Result;


public interface ServicioDatosInterface extends Remote {
	public <TClass extends Serializable> Result<TClass> realizarQuery(QueryRequest<TClass> request) throws RemoteException;
	public <TClass extends Serializable> Result<TClass> ejecutarProcedimiento(ProcedureRequest<TClass> request) throws RemoteException;
}
