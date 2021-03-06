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
package org.kimios.kernel.user.impl.factory.hibernate;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;
import org.kimios.exceptions.ConfigException;
import org.kimios.kernel.exception.DataSourceException;
import org.kimios.kernel.hibernate.HFactory;
import org.kimios.kernel.user.AuthenticationSource;
import org.kimios.kernel.user.AuthenticationSourceBean;
import org.kimios.kernel.user.AuthenticationSourceFactory;
import org.kimios.kernel.user.AuthenticationSourceImpl;
import org.kimios.kernel.utils.ClassFinder;

public class HAuthenticationSourceFactory extends HFactory implements AuthenticationSourceFactory
{
    public void deleteAuthenticationSource(AuthenticationSource source) throws DataSourceException, ConfigException
    {
        AuthenticationSourceBean a = new AuthenticationSourceBean();
        a.setJavaClass(source.getClass().getName());
        a.setName(source.getName());
        a = (AuthenticationSourceBean) getSession().merge(a);
        getSession().delete(a);
    }

    public AuthenticationSource getAuthenticationSource(String name) throws ConfigException, DataSourceException
    {
        try {
            AuthenticationSourceBean a =
                    (AuthenticationSourceBean) getSession().get(AuthenticationSourceBean.class, name);
            if (a == null) {
                return null;
            }
            try {
                AuthenticationSource source = (AuthenticationSource) Class.forName(a.getJavaClass()).newInstance();
                source.setName(a.getName());
                setClassFields(source, a);
                return source;
            } catch (ClassNotFoundException cnfe) {
                throw new ConfigException(cnfe, "Cannot instantiate authentication source");
            } catch (IllegalAccessException iae) {
                throw new ConfigException(iae, "Cannot instantiate authentication source");
            } catch (InstantiationException ie) {
                throw new ConfigException(ie, "Cannot instantiate authentication source");
            }
        } catch (HibernateException he) {
            throw new DataSourceException(he, he.getMessage());
        }
    }

    public Vector<AuthenticationSource> getAuthenticationSources() throws ConfigException, DataSourceException
    {

        try {
            @SuppressWarnings("unchecked")
            List<AuthenticationSourceBean> lAsb = getSession().createCriteria(
                    AuthenticationSourceBean.class).addOrder(Order.asc("name")).list();
            try {
                Vector<AuthenticationSource> v = new Vector<AuthenticationSource>();
                for (AuthenticationSourceBean a : lAsb) {
                    AuthenticationSource source = (AuthenticationSource) Class.forName(a.getJavaClass()).newInstance();
                    source.setName(a.getName());
                    setClassFields(source, a);
                    v.add(source);
                }
                return v;
            } catch (ClassNotFoundException cnfe) {
                throw new ConfigException(cnfe, "Cannot instantiate authentication source");
            } catch (IllegalAccessException iae) {
                throw new ConfigException(iae, "Cannot instantiate authentication source");
            } catch (InstantiationException ie) {
                throw new ConfigException(ie, "Cannot instantiate authentication source");
            }
        } catch (HibernateException he) {
            throw new DataSourceException(he, he.getMessage());
        }
    }

    public void saveAuthenticationSource(AuthenticationSource source, String className) throws DataSourceException
    {
        try {
            AuthenticationSourceBean a = new AuthenticationSourceBean();
            a.setJavaClass(className);
            a.setName(source.getName());
            getSession().save(a);
            getSession().flush();
        } catch (HibernateException e) {
            boolean integrity = e instanceof ConstraintViolationException;
            throw new DataSourceException(e, e.getMessage());
        }
    }

    public void saveAuthenticationSource(AuthenticationSource source) throws DataSourceException, ConfigException
    {
        saveAuthenticationSource(source, source.getClass().getName());
    }

    public void updateAuthenticationSource(AuthenticationSource source, String newName) throws DataSourceException,
            ConfigException
    {

        try {
            AuthenticationSourceBean a = new AuthenticationSourceBean();
            a.setJavaClass(source.getClass().getName());
            a.setName(source.getName());
            try {
                a = (AuthenticationSourceBean) getSession().merge(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getSession().update(a);
            getSession().flush();
        } catch (HibernateException e) {
            boolean integrity = e instanceof ConstraintViolationException;
            throw new DataSourceException(e, e.getMessage());
        }
    }

    /**
     * Get a class name XML list of the all implemented authentication sources
     */
    public String getAvailableAuthenticationSource()
    {
        Collection<Class<? extends AuthenticationSourceImpl>> classes = ClassFinder.findImplement("org.kimios",
                AuthenticationSourceImpl.class);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xml += "<authentication-source-list>\n";
        for (Class<?> c : classes) {
            xml += "<authentication-source class-name=\"" + c.getName() + "\"/>\n";
        }
        xml += "</authentication-source-list>\n";
        return xml;
    }

    private void setClassFields(AuthenticationSource source, AuthenticationSourceBean bean)
            throws IllegalArgumentException, IllegalAccessException
    {

        Map<String, String> parameters = bean.getParameters();
        for (Field field : source.getClass().getDeclaredFields()) {
            String value = parameters.get(field.getName());
            if (value != null) {
                field.setAccessible(true);
                field.set(source, value);
            }
        }
    }

    public String getAvailableAuthenticationSourceParams(String className) throws ClassNotFoundException
    {
        Class<?> c = Class.forName(className);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xml += "<authentication-source>\n";
        for (Field f : c.getDeclaredFields()) {
            xml += "<field name=\"" + f.getName() + "\"/>\n";
        }
        xml += "</authentication-source>\n";
        return xml;
    }
}

