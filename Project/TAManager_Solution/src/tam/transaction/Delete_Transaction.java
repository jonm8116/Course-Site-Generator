/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import java.util.ArrayList;
import javafx.scene.control.Label;
import jtps.jTPS_Transaction;
import tam.data.TAData;
import tam.data.TeachingAssistant;

/**
 *
 * @author John
 */
public class Delete_Transaction implements jTPS_Transaction{
    private TeachingAssistant ta;
    private TAData data;
    private Label cellLabel;
    private ArrayList<String> cellKeys;
    
    public Delete_Transaction(TeachingAssistant ta, TAData data, Label cellLabel, ArrayList<String> cellKeys){
        this.ta = ta;
        this.data = data;
        this.cellLabel = cellLabel;
        this.cellKeys = cellKeys;
    }
    
    @Override
    public void doTransaction(){
        
        data.removeTA(ta.getName());
        try{
            for(String cellKey: cellKeys){
                    data.toggleTAOfficeHours(cellKey, ta.getName());
            }
        } catch(NullPointerException ex){
            
        }
    }
    @Override
    public void undoTransaction(){
        try{
            data.addTA(ta.getName(), ta.getEmail());
            //data.removeTAFromCell(cellLabel.textProperty(), ta.getName());
            for(String cellKey: cellKeys){
                data.toggleTAOfficeHours(cellKey, ta.getName());
            }
            
        } catch(NullPointerException ex){
            
        }
    }
}
