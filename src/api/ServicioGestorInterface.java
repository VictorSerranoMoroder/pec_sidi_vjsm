/*
 * ServicioGestorInterface.java
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

import data_model.Trino;

public interface ServicioGestorInterface extends Remote {
	public String solicitarInfoUsuario(String username) throws RemoteException;
	public boolean enviarTrino(Trino trino) throws RemoteException;
	public String listarUsuarios() throws RemoteException;
	public boolean editarSubscripcion(String usernameSource, String usernameTarget, boolean isSubscribed) throws RemoteException;
	public void registrarTrinoCallback(String username, CallbackUsuarioInterface callback) throws RemoteException;
}
