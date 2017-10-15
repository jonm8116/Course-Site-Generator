/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import java.util.ArrayList;
import jtps.jTPS_Transaction;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.workspace.TAController;
import tam.workspace.TAWorkspace;

/**
 *
 * @author John
 */
public class Update_Transaction implements jTPS_Transaction{
    private TeachingAssistant newTa;
    private TeachingAssistant oldTA;
    private TAController controller;
    private TAData data;
    private ArrayList<String> cellKeys;
    private TAWorkspace workspace;
    
    public Update_Transaction(TeachingAssistant newTa, TAController controller, TAData data, TeachingAssistant oldTA, 
                              ArrayList<String> cellKeys, TAWorkspace workspace){
        this.newTa = newTa;
        this.controller = controller;
        this.data = data;
        this.oldTA = oldTA;
        this.cellKeys = cellKeys;
        this.workspace = workspace;
    }    
    
    @Override
    public void doTransaction(){
        //workspace.getEmailTextField().setText(newTa.getEmail());
        //workspace.getNameTextField().setText(newTa.getName());
        data.removeTA(oldTA.getName());
        data.addTA(newTa.getName(), newTa.getEmail());
        
        for(String key: cellKeys){
            data.toggleTAOfficeHours(key, oldTA.getName());
            data.toggleTAOfficeHours(key, newTa.getName());
            //data.toggleTAOfficeHours(key, oldTA.getName());
        }
    }
    @Override
    public void undoTransaction(){
        data.removeTA(newTa.getName());
        data.addTA(oldTA.getName(), oldTA.getEmail());
        
        for(String key: cellKeys){
            data.toggleTAOfficeHours(key, newTa.getName());
            data.toggleTAOfficeHours(key, oldTA.getName());
        }
    }
}
