package com.gpcalculator;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.TitledBorder;
import java.util.List;
import jxl.write.WriteException;
import static com.gpcalculator.UI.*;

/**
 * A simple class that helps the student calculates and documents his result.
 * During successive semesters it calculates each GPA and shows the
 * corresponding CGPA (accumulated and calculated).
 * 
 * @author OROBO LUCKY (OROBOGENIUS)
 */
public class CalculatorHandler implements ActionListener {
    
    //------ Variable Declarations -------\\

    static List<Double> accumulatedGPA = new ArrayList<>(); //List of GPAs for successive semester
    static List<Double> accumulatedCGPA = new ArrayList<>(); //List of CGPAs for successive semester
    static List<Integer> validatedIndex = new ArrayList<>();
    static List<List<List<String>>> session = new ArrayList<>(); //List of an entire session details
    static List<List<String>> firstSemester = new ArrayList<>(); //List of first semester details
    static List<List<String>> secondSemester = new ArrayList<>(); //List of second semester details
    static final int NUMBEROFCOURSES = 10; //Fixed number of courses
    static int totalUnit = 0;
    static int courseTotal = 0;
    static int numberOfSemester = 0;
    static int sessionCount = 0;
    static int semesterCount=1;
    static boolean calcButtonClicked;
    static double sumOfGPA;
    static double gpa;
    static double tempGPA;
    static double tempCGPA;
    static double cgpa;
    static int[] grade = new int[10];
    static int[] unit = new int[10];
    String previousSemester="", currentSemester;
    static DocumentHandler document;

    /**
     * An enum that represents each grade and its corresponding integer value which
     * is used to compute the student's GPA for successive semesters
     */
    
    protected enum Grade {
        A("A",5), B("B",4), C("C",3), D("D",2), E("E",1), F("F",0);
        
        private final String grade;
        private final int value;

        Grade (String grade, int value){
            this.grade = grade;
            this.value = value;
        }
        
        /**
         * 
         * @return the grade of the student.
         */
        public String grade(){
            return grade;
        }
        
        /**
         * 
         * @return the value of the corresponding grade of the student.
         */
        
        public int value(){
            return value;
        }
        
        /**
         * Overrides toString method of Object class.
         * @return the grade of the student.
         */
        
        @Override
        public String toString(){
            return grade;
        }
    }
    
    
    /**
     * Creates a new Document object to be used in creating the student's result
     * sheet.
     * @throws WriteException 
     */
    public CalculatorHandler() throws WriteException{
        document = new DocumentHandler();
    }

    //------ ActionListener -------\\
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        int validated; //number of courses validated

        for (int i = 0; i < NUMBEROFCOURSES; i++) {
            if (source == clearFieldButton.get(i)) {
                courseField.get(i).setText("");
                codeField.get(i).setText("");
                gradeBox.get(i).setSelectedIndex((-1));
                unitBox.get(i).setSelectedIndex((-1));
                checkBox.get(i).setSelected(false);
            }
        }
        //Calculate button was clicked
        if (source == calcButton){
            calcButtonClicked=true;
            validated = 0;
            //this will run for the number of courses
            for (int j = 0; j < NUMBEROFCOURSES; j++) {
                //check the number of courses that has been validated
                if (checkBox.get(j).isSelected()) {
                    validated++;
                    validatedIndex.add(j);
                }
                        //---- Checks for validity of data -----\\
                //when course grade and course unit are valid but data has not been validated
                if ((gradeBox.get(j).getSelectedIndex() != -1
                        && unitBox.get(j).getSelectedIndex() != -1)
                        && !checkBox.get(j).isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please validate your data",
                            "Validate", JOptionPane.INFORMATION_MESSAGE);
                    checkBox.get(j).requestFocusInWindow();
                    return;
                }
                //when course grade is valid but course unit is not and data has not been validated
                if ((gradeBox.get(j).getSelectedIndex() != -1
                        && unitBox.get(j).getSelectedIndex() == -1)
                        && !checkBox.get(j).isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please select a valid unit",
                            "Invalid Unit", JOptionPane.INFORMATION_MESSAGE);
                    unitBox.get(j).requestFocusInWindow();
                    return;
                }
                //when course grade is valid and data has been validated but course unit is not
                if ((gradeBox.get(j).getSelectedIndex() != -1
                        && unitBox.get(j).getSelectedIndex() == -1)
                        && checkBox.get(j).isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please select a valid unit",
                            "Invalid unit", JOptionPane.INFORMATION_MESSAGE);
                    unitBox.get(j).requestFocusInWindow();
                    return;
                }
                //when course unit is valid but course grade and data has not been validated
                if ((gradeBox.get(j).getSelectedIndex() == -1
                        && unitBox.get(j).getSelectedIndex() != -1)
                        && !checkBox.get(j).isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please select a valid grade",
                            "Invalid grade", JOptionPane.INFORMATION_MESSAGE);
                    gradeBox.get(j).requestFocusInWindow();
                    return;
                }
                //when course unit is valid and data has been validated but but course grade is not valid
                if ((gradeBox.get(j).getSelectedIndex() == -1
                        && unitBox.get(j).getSelectedIndex() != -1)
                        && checkBox.get(j).isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please select a valid grade",
                            "Invalid grade", JOptionPane.INFORMATION_MESSAGE);
                    gradeBox.get(j).requestFocusInWindow();
                    return;
                }
                //when data has been validated but course grade and course unit are not valid
                if ((gradeBox.get(j).getSelectedIndex() == -1
                        && unitBox.get(j).getSelectedIndex() == -1)
                        && checkBox.get(j).isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please select a valid grade",
                            "Invalid grade", JOptionPane.INFORMATION_MESSAGE);
                    gradeBox.get(j).requestFocusInWindow();
                    return;
                }
            }
            
            //All data has been validated, this runs through all validated data
            //and calculates the GPA and CGPA respectively
            for (int i = 0; i < validated; i++) {
                String item;
                item = (String) gradeBox.get(validatedIndex.get(i)).getSelectedItem().toString();//fix
                try {
                    switch (item) {
                    case "A":
                        grade[i] = Grade.A.value;
                        break;
                    case "B":
                        grade[i] = Grade.B.value;
                        break;
                    case "C":
                        grade[i] = Grade.C.value;;
                        break;
                    case "D":
                        grade[i] = Grade.D.value;;
                        break;
                    case "E":
                        grade[i] = Grade.E.value;;
                        break;
                    case "F":
                        grade[i] = Grade.F.value;;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "\nAll preceding fields must be validated",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        checkBox.get(validatedIndex.get(i)).requestFocusInWindow();
                        break;
                    }
                }
                catch (NullPointerException e){
                    JOptionPane.showMessageDialog(null, "An error has occurred\n"
                            + "Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    unit[i] = Integer.parseInt((String) unitBox.get(validatedIndex.get(i)).getSelectedItem());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please select a valid unit",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                courseTotal += grade[i] * unit[i];
                totalUnit += unit[i];
            }
           gpa = (double) (courseTotal) / totalUnit;
           if (currentSemester.equals(previousSemester) && gpa==accumulatedGPA.get(numberOfSemester)){
                //if calculate button has already been clicked for the current semester, 
                //and the input is still thesame,
                //do nothing
               return;
            }
            else if (currentSemester.equals(previousSemester) && gpa!=accumulatedGPA.get(numberOfSemester)){
                //if calculate button has already been clicked for the current semester, 
                //and the input is not thesame,
                //replace the previous GPA and recalculate CGPA
                accumulatedGPA.set(numberOfSemester,gpa);
                accumulatedGPA.stream().forEach((item) -> {
                    sumOfGPA += item;
                });
                cgpa = sumOfGPA / (numberOfSemester + 1);
                accumulatedCGPA.set(numberOfSemester,cgpa);
                resultArea.append(String.format("\nGPA for this semester is: %1.2f", accumulatedGPA.get(numberOfSemester)));
                resultArea.append(String.format("\nCGPA is: %1.2f", accumulatedCGPA.get(numberOfSemester)));
            }
            else {
                //This is a new semester, add the GPA to the list and calculate CGPA
               if (Double.isNaN(gpa)){
                   //if there is no computation for the current semester
                   return;
               }
               else {
                    accumulatedGPA.add(gpa);
                    tempGPA=gpa;
                    accumulatedGPA.stream().forEach((item) -> {
                        sumOfGPA += item;
                     });
                     cgpa = sumOfGPA / (numberOfSemester + 1);
                     tempCGPA = cgpa;
                     accumulatedCGPA.add(cgpa);
                     resultArea.append(String.format("\nGPA for this semester is: %1.2f", gpa));
                     resultArea.append(String.format("\nCGPA is: %1.2f", cgpa));
               }
            }
            previousSemester = currentSemester;
            //reset values
            gpa = 0;
            cgpa = 0;
            courseTotal = 0;
            totalUnit = 0;
            sumOfGPA = 0;
        } //end of calcButton
        if (source == clearButton) {
            for (int i = 0; i < NUMBEROFCOURSES; i++) {
                courseField.get(i).setText("");
                codeField.get(i).setText("");
                gradeBox.get(i).setSelectedIndex((-1));
                unitBox.get(i).setSelectedIndex((-1));
                checkBox.get(i).setSelected(false);
                grade[i] = 0;
                unit[i] = 0;
            }
            gpa = 0;
            courseTotal = 0;
            totalUnit = 0;
            validatedIndex.clear();
        } //end of clearButton
        if (source == nextSemButton) {
            if (FileMenuHandler.workSpace == FileMenuHandler.NEWDOC){
                
            }
            int serialNumber=0;
            ArrayList<String> tempDescription = new ArrayList<>();
            ArrayList<String> tempCode = new ArrayList<>();
            ArrayList<String> tempGrade = new ArrayList<>();
            ArrayList<String> tempUnit = new ArrayList<>();
            if (Double.isNaN(tempGPA)==false && tempGPA!=0){
                numberOfSemester++;
            }
            for (int i=0;i<NUMBEROFCOURSES;i++){
                if (courseField.get(i).getText().isEmpty()==false || codeField.get(i).getText().isEmpty()==false
                    || gradeBox.get(i).getSelectedIndex()!=-1 || unitBox.get(i).getSelectedIndex()!=-1){
                    serialNumber++;
                }
            }
            for (int i=0;i<NUMBEROFCOURSES;i++){
                tempDescription.add(courseField.get(i).getText());
                tempCode.add(codeField.get(i).getText());
                tempGrade.add(String.valueOf(gradeBox.get(i).getSelectedItem()));
                tempUnit.add(String.valueOf(unitBox.get(i).getSelectedItem()));
            }
            if (semesterCount==1){
                nextSemButton.setText("New Session");
                JOptionPane.showMessageDialog(null, "SECOND SEMESTER");
                document.getFirstSemester(tempDescription, tempCode, tempGrade, tempUnit,
                        serialNumber, tempGPA, tempCGPA);
                semesterCount++;
            }
            else if (semesterCount==2){
                sessionCount++;
                JOptionPane.showMessageDialog(null, "NEW SESSION :: FIRST SEMESTER");
                nextSemButton.setText("Next Semester");
                semesterCount=1;
                if (calcButtonClicked){
                    resultArea.append("\n----New Session----");
                }
                document.getSecondSemester(tempDescription, tempCode, tempGrade, tempUnit,
                        serialNumber, tempGPA, tempCGPA);
            }
            for (int i = 0; i < NUMBEROFCOURSES; i++) {
               courseField.get(i).setText("");
               codeField.get(i).setText("");
               gradeBox.get(i).setSelectedIndex((-1));
               unitBox.get(i).setSelectedIndex((-1));
               checkBox.get(i).setSelected(false);
               grade[i] = 0;
               unit[i] = 0;
           }
           topPanel.setBorder(BorderFactory.createTitledBorder(BORDERLINE, "NEW SEMESTER", TitledBorder.DEFAULT_JUSTIFICATION,
                   TitledBorder.TOP, new Font("Georgia", Font.BOLD, 12), Color.WHITE));
           gpa = 0;
           courseTotal = 0;
           cgpa = 0.0;
           totalUnit = 0;
           tempGPA=0;
           tempCGPA=0;
        } //end of semesterButton
        if (source==documentButton){
            card = (CardLayout)(UI.contentPanel.getLayout());
            card.show(UI.contentPanel, "Document Panel");
            session.add(firstSemester);
            session.add(secondSemester);
            try {
                document.createWorkBook(sessionCount);
                document.getNumberOfSession(sessionCount);
                document.addDetailsToSheet();
                DocumentHandler.workBook.write();
                DocumentHandler.workBook.close();
                JOptionPane.showMessageDialog(null,"Document created");
            }
            catch (WriteException | IOException e){
                JOptionPane.showMessageDialog(null,"Could not create document");
            }
        }
    } //end of actionPerformed()
} //end of class GPCalculator