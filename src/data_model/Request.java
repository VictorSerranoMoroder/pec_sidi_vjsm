/*
 * Request.java
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

import data_model.db_requests.ProcedureRequest;
import data_model.db_requests.QueryRequest;
import data_model.svr_requests.ServerRequest;

public sealed interface Request<TClass extends Serializable> extends Serializable
	permits ServerRequest, QueryRequest, ProcedureRequest{}