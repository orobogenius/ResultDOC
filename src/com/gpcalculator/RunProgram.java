package com.gpcalculator;

import javax.swing.*;
import jxl.write.WriteException;

public class RunProgram {    
  
	  //------ RunProgram routine to run the program -------\\

    public static void main(String... vargs){
        //LookAndFeel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e){
            JOptionPane.showMessageDialog(null,"Setting to default L&F");
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException ex){
                    ex.printStackTrace();
                }
        }
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                UI.prepareGUI();
            } catch (WriteException ex) {}
        });
    }
}