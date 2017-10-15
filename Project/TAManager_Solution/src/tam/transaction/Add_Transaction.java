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

/**
 *
 * @author John
 */
public class Add_Transaction implements jTPS_Transaction{
    
    private TeachingAssistant newTa;
    private TAData data;
    private ArrayList<Boolean> redoValues;
    
    public Add_Transaction(TeachingAssistant ta, TAData data){
        this.newTa = ta;
        this.data = data;
        this.redoValues = new ArrayList<>();
    }
    
    @Override
    public void doTransaction(){
        
//        Boolean value = redoValues.get(0);
//        if(value != null){
            data.addTA(newTa.getName(), newTa.getEmail());
//            redoValues.remove(0);
//        } else{
//            
//        }
    }
    
    @Override
    public void undoTransaction(){
        data.removeTA(newTa.getName());
        redoValues.add(Boolean.TRUE);
    }
    
}
