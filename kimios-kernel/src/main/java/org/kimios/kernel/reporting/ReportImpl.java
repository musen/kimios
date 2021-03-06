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
package org.kimios.kernel.reporting;

import org.hibernate.Session;
import org.kimios.exceptions.ConfigException;
import org.kimios.kernel.exception.DataSourceException;

public abstract class ReportImpl
{
    protected String name;

    protected String sessionUid;

    protected Session getSession()
    {
        return FactoryInstantiator.getInstance().getDtrFactory().getSession();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSessionUid()
    {
        return sessionUid;
    }

    public void setSessionUid(String sessionUid)
    {
        this.sessionUid = sessionUid;
    }

    public abstract String getData() throws ConfigException,
            DataSourceException;
}

