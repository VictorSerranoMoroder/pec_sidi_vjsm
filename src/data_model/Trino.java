/*
 * Trino.java
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
package data_model;

import java.io.Serializable;
import java.util.Date;

public class Trino implements Serializable{

	private static final long serialVersionUID = 3L;
	private String trino;
	private String nickPropietario;	//Ojo no puede haber varios usuarios con el mismo nick
	private long timestamp; //momento en el que se produce el evento (tiempo en el servidor)

	public Trino(String nickPropietario, String trino)
	{
		this.trino=trino;
		this.nickPropietario=nickPropietario;
		Date date = new Date();
		this.timestamp=date.getTime();
	}
	public String GetTrino()
	{
		return (trino);
	}
	public String GetNickPropietario()
	{
		return(nickPropietario);
	}
	public long GetTimestamp()
	{
		return (timestamp);
	}
	public String toString(){
		return (getClass().getName()+"@"+trino+"|"+nickPropietario+"|"+timestamp+"|");
	}
}