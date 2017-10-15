/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author John
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String s1 = "Hello";
        String s2 = "Hello";
        s2 += "l";
        String s3 = "Hellol";
        s2 = "Hellol";
        
        if(s1 == s2){
            System.out.println("you right");
        } else{
            System.out.println("Na you wrong fam");
        }
        
        if(s2 == s3){
            System.out.println("you right");
        } else{
            System.out.println("Na you wrong fam");
        }
    }
    
}
