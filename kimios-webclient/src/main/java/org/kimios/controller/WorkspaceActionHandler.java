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
package org.kimios.controller;

import org.kimios.kernel.ws.pojo.*;
import org.kimios.client.controller.*;

import java.util.Calendar;
import java.util.Map;

public class WorkspaceActionHandler extends Controller{

    public WorkspaceActionHandler(Map<String, String> parameters) {
        super(parameters);
    }

  public String execute() throws Exception{
      if(action!=null){
        if(action.equals("NewWorkspace"))
          addWorkspace();
        if(action.equals("UpdateWorkspace"))
          updateWorkspace();

                return "";
      }else
                return "NOACTION";
  }
        
        
  private void addWorkspace() throws Exception {
    User user=securityController.getUser(sessionUid);
    WorkspaceController fsm = workspaceController;
    Workspace w = new Workspace();
    w.setName(parameters.get("name"));
    w.setCreationDate(Calendar.getInstance());
    w.setOwner(user.getUid());
    w.setOwnerSource(user.getSource());
    w.setUid(-1);
    long workspaceUid=fsm.createWorkspace(sessionUid, w);
    w.setUid(workspaceUid);
                
    securityController.updateDMEntitySecurities(sessionUid, workspaceUid, 1, false,
                        DMEntitySecuritiesParser.parseFromJson(parameters.get("sec"), workspaceUid, 1));

  }
  
  private void updateWorkspace() throws Exception {
      WorkspaceController fsm = workspaceController;
      long workspaceUid=Long.parseLong(parameters.get("uid"));
      Workspace w = fsm.getWorkspace(sessionUid, workspaceUid);
      w.setName(parameters.get("name"));
      fsm.updateWorkspace(sessionUid, w);
      if(securityController.hasFullAccess(sessionUid, workspaceUid, 1)){
        boolean changeSecurity = true;
        if (parameters.get("changeSecurity") != null)
          changeSecurity = Boolean.parseBoolean(parameters.get("changeSecurity"));
        
        if (changeSecurity == true){
          securityController.updateDMEntitySecurities(sessionUid, workspaceUid, 1, (parameters.get("isRecursive")!=null && parameters.get("isRecursive").equals("true")),
                                          DMEntitySecuritiesParser.parseFromJson(parameters.get("sec"), workspaceUid, 1));
        }
      }
  }

}

