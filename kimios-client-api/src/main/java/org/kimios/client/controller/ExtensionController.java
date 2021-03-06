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
package org.kimios.client.controller;

import org.kimios.client.exception.ExceptionHelper;
import org.kimios.kernel.ws.pojo.DMEntityAttribute;
import org.kimios.webservices.ExtensionService;

public class ExtensionController
{

    private ExtensionService client;

    public ExtensionService getClient()
    {
        return client;
    }

    public void setClient( ExtensionService client )
    {
        this.client = client;
    }

    public DMEntityAttribute getDMEntityAttribute( String sessionId, long dmEntityId, String dmEntityAttributeName )
        throws Exception
    {
        try
        {
            return client.getEntityAttribute( sessionId, dmEntityId, dmEntityAttributeName );
        }
        catch ( Exception e )
        {
            throw new ExceptionHelper().convertException( e );
        }
    }

    // TODO: Create others Attribute functions Call
}
