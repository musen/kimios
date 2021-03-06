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
package org.kimios.kernel.dms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.kimios.exceptions.ConfigException;
import org.kimios.kernel.exception.DataSourceException;

@Entity
@Table(name = "folder")
@PrimaryKeyJoinColumn(name = "id")
public class Folder extends DMEntityImpl
{
    private DMEntityImpl parent;

    private long parentUid;

    private int parentType;

    public Folder()
    {
        this.type = DMEntityType.FOLDER;
    }

    public Folder(long uid, String name, String owner, String ownerSource, Date creationDate, long parentUid,
            int parentType)
    {
        this.type = DMEntityType.FOLDER;
        this.uid = uid;
        this.name = name;
        this.owner = owner;
        this.ownerSource = ownerSource;
        this.creationDate = creationDate;
        this.parentUid = parentUid;
        this.parentType = parentType;
    }

    public Folder(long uid, String name, String owner, String ownerSource, Date creationDate, Date updateDate,
            long parentUid, int parentType)
    {
        this.type = DMEntityType.FOLDER;
        this.uid = uid;
        this.name = name;
        this.owner = owner;
        this.ownerSource = ownerSource;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.parentUid = parentUid;
        this.parentType = parentType;
    }

    @ManyToOne(targetEntity = DMEntityImpl.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false, insertable = false, updatable = false)
    public DMEntityImpl getParent() throws ConfigException, DataSourceException
    {
        return this.parent;
    }

    public void setParent(DMEntityImpl parent)
    {
        if (parent != null) {
            this.parentUid = parent.getUid();
            this.parentType = parent.getType();
        }
        this.parent = parent;
    }

    @Column(name = "parent_type", nullable = false)
    public int getParentType()
    {
        return parentType;
    }

    public void setParentType(int parentType)
    {
        this.parentType = parentType;
    }

    @Column(name = "parent_id", nullable = false)
    public long getParentUid()
    {
        return parentUid;
    }

    public void setParentUid(long parentUid)
    {
        this.parentUid = parentUid;
    }

    @Transient
    public org.kimios.kernel.ws.pojo.Folder toPojo()
    {
        return new org.kimios.kernel.ws.pojo.Folder(this.uid, this.name, this.owner,
                this.ownerSource, this.creationDate, this.updateDate, this.parentUid, this.parentType, this.path);
    }
}

