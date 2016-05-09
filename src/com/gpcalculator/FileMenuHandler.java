package com.gpcalculator;

import java.awt.CardLayout;
import javax.swing.*;

/**
 * This class is responsible for handling events that occurs in the File Menu of the program
 * @author OROBO LUCKY
 */
public class FileMenuHandler {
    
    protected static final int NEWDOC = 1;
    protected static final int UPDATEDOC = 2;
    protected static final int MODIFYDOC = 3;
    protected static int workSpace;
    
    /**
     * This method allows the user to create a New Document (workbook)
     */
    
    protected class NewDocumentHandler{
        
        NewDocumentHandler(){
            UI.card = (CardLayout)(UI.contentPanel.getLayout());
            if (!UI.startPanel.isVisible()){
                int confirm = JOptionPane.showOptionDialog(null, "Do you want to open a New Document?\nAny unsaved document might be lost!", "New Document", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    UI.card.show(UI.contentPanel, "New Document Panel");
                }
            }
            else {
                UI.card.show(UI.contentPanel, "New Document Panel");
            }
            workSpace = NEWDOC;
        }
    }
    
    protected static void updateDocumentHandler(){
        int confirm = JOptionPane.showOptionDialog(null, "Do you want to open an Existing Document?\nAny unsaved document might be lost!", "New Document", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == 0) {
            UI.card = (CardLayout)(UI.contentPanel.getLayout());
            UI.card.show(UI.contentPanel, "New Document Panel");
        }
    }
    
    protected static void exitHandler(){
        int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?\nAny unsaved document might be lost!", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    System.exit(0);
        }
    }
}
