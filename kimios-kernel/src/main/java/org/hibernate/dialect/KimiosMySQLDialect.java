/*
 * Kimios - Document Management System Software
 * Copyright (C) 2008-2012  DevLib'
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
package org.hibernate.dialect;

/**
 * Custom MySQL Dialect to handle UTF-8 Table creation
 */
public class KimiosMySQLDialect extends MySQL5InnoDBDialect
{
    @Override public String getTableTypeString()
    {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
