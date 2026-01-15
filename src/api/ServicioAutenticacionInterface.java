/*
 * ServicioAutenticacionInterface.java
 * Copyright (C) 2025 Víctor Serrano Moroder vserrano157@alumno.uned.es
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
