package api;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import data_model.Result;
import data_model.requests.ProcedureRequest;
import data_model.requests.QueryRequest;
import data_model.requests.Request;


public interface ServicioDatosInterface extends Remote {
	public <TClass extends Serializable> Result<TClass> realizarQuery(QueryRequest request) throws RemoteException;
	public <TClass extends Serializable> Result<TClass> ejecutarProcedimiento(ProcedureRequest request) throws RemoteException;
}
