package com.gpcalculator;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import javax.swing.JOptionPane;

/**
 * This class handles all file operations in this program which stems from
 * creating the .xls file to manipulating the file in the underlying FileSystem
 * Architecture. It supports both Windows and Unix File Systems Operations.
 */
public class FileHandler {
 
    protected File file;
    
    protected File createFile() throws IOException{
        Path filePath = Paths.get(System.getProperty("user.home")+"\\Documents\\GPCalculator");
        if (Files.notExists(filePath)){
            Files.createDirectory(filePath);
        }
        file = new File(filePath.toString()+"\\Result.xls");
        if (file.exists()){
            int confirm = JOptionPane.showOptionDialog(null, "The File already Exists.\nDo you want to overwrite the existing file?", "File Already Exists", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == 0) {
                boolean fileStatus = file.delete();
                    if (fileStatus){
                        file = new File(filePath.toString()+"\\Result.xls");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "The File could not be deleted", "File Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            else {
                //FilePersists
            }
        }
        return file;
    }
}