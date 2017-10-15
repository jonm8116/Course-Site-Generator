/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.data.TAData;

/**
 *
 * @author John
 */
public class Toggle_Transaction implements jTPS_Transaction{
    private String cellKey;
    private String taName;
    private TAData data;
    
    public Toggle_Transaction(String cellKey, String taName, TAData data){
        this.cellKey = cellKey;
        this.taName = taName;
        this.data = data;
    }
    @Override
    public void doTransaction(){
        data.toggleTAOfficeHours(cellKey, taName);
    }
    @Override
    public void undoTransaction(){
        data.toggleTAOfficeHours(cellKey, taName);
    }
}
