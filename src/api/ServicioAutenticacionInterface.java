package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data_model.results.Result;
import data_model.svr_requests.AuthenticateUser;
import data_model.svr_requests.SignUpRequest;

public interface ServicioAutenticacionInterface extends Remote {
    public Result AutenticarUsuario(AuthenticateUser request) throws RemoteException;
    public Result RegistrarUsuario(SignUpRequest request) throws RemoteException;
}
