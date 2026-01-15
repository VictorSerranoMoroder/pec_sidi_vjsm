/*
 * User.java
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
import java.util.Objects;

public class User implements Serializable {

	private static final long serialVersionUID = 3L;
	public boolean isOnline;
	public boolean isBanned;
	public String username;
	public String password;

	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public void setOnline(boolean online)
	{
		isOnline = online;
	}

	public void setBan(boolean ban)
	{
		isBanned = ban;
	}

	// Overrides needed to correctly compare users
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

	@Override
	public String toString() {
		return "User [isOnline=" + isOnline + ", isBanned=" + isBanned + ", username=" + username + ", password="
				+ password + "]";
	}
}
