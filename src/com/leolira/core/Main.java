/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leolira.core;

import com.leolira.ui.Principal;
import javax.swing.JFrame;

/**
 *
 * @author Leo Lira
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
        Principal principal = new Principal();  
            
        principal.setVisible(true);
        principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    }
    
   
}
