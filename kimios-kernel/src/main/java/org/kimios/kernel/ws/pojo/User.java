/*
 * Kimios - Document Management System Software
 * Copyright (C) 2012-2013  DevLib'
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kimios.kernel.ws.pojo;

import java.util.Calendar;
import java.util.Date;

public class User
{
    private String uid;

    private String name;

    private String source;

    private Calendar lastLogin;

    private String mail;

    public User()
    {
    }

    public User(String uid, String name, String source, Date lastLogin, String mail)
    {
        this.uid = uid;
        this.name = name;
        this.source = source;
        if (lastLogin != null) {
            this.lastLogin = Calendar.getInstance();
            this.lastLogin.setTime(lastLogin);
        }
        this.mail = mail;
    }

    public Calendar getLastLogin()
    {
        return lastLogin;
    }

    public void setLastLogin(Calendar lastLogin)
    {
        this.lastLogin = lastLogin;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }
}

