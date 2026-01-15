/*
 * CallbackUsuarioImpl.java
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
package cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import api.CallbackUsuarioInterface;
import data_model.Trino;

public class CallbackUsuarioImpl extends UnicastRemoteObject
		implements CallbackUsuarioInterface {

	public CallbackUsuarioImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	    }

	@Override
	public void recibirTrino(Trino trino) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("---------");
		System.out.println("Nuevo trino de: " + trino.GetNickPropietario());
		System.out.println(trino.GetTrino());
		System.out.println("---------");
	}

}
