package com.gpcalculator;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Alignment;
import jxl.SheetSettings;
import jxl.format.PageOrientation;
import jxl.write.WritableWorkbook;

/**
 * This Class represents a single and complete Student result WorkBook. The WorkBook
 * contains a Student-Specified number of WorkSheets.
 * Each WorkSheet is separated into two semesters: First and Second semester respectively.
 * Each semester contains the Student's semester result.
 * 
 * @author OROBOGENIUS
 */

public class DocumentHandler {
    /**
     * The student's workbook
     */
    protected static WritableWorkbook workBook;
    private static WritableSheet[] sheets; //An Array of Individual WorkSheet
    private final WritableFont label1Font = new WritableFont(WritableFont.createFont("Bookman Old Style"), 14 ,WritableFont.BOLD);
    private final WritableFont label2Font = new WritableFont(WritableFont.createFont("Calibri"), 11 ,WritableFont.BOLD);
    private final WritableFont label3Font = new WritableFont(WritableFont.createFont("Calibri"), 11 ,WritableFont.BOLD);
    private final WritableFont buttom3Font = new WritableFont(WritableFont.createFont("Calibri"), 11 ,WritableFont.BOLD);
    private final WritableCellFormat label1Format = new WritableCellFormat(label1Font);
    private final WritableCellFormat label2Format = new WritableCellFormat(label2Font);
    private final WritableCellFormat label3Format = new WritableCellFormat(label3Font);
    private final WritableCellFormat buttom3Format = new WritableCellFormat(buttom3Font);
    private final WritableFont contentFont = new WritableFont(WritableFont.createFont("Calibri"), 11);
    private final WritableFont unitAndGradeFont = new WritableFont(WritableFont.createFont("Calibri"), 11);
    private final WritableCellFormat cellFormat = new WritableCellFormat(contentFont);
    private final WritableCellFormat unitAndGradeFormat = new WritableCellFormat(unitAndGradeFont);
    private List<List<String>> courseDesc1;
    private List<List<String>> courseDesc2;
    private List<List<String>> courseCode1;
    private List<List<String>> courseCode2;
    private List<List<String>> courseGrade1;
    private List<List<String>> courseGrade2;
    private List<List<String>> courseUnit1;
    private List<List<String>> courseUnit2;
    private List<Integer> serialNumber1;
    private List<Integer> serialNumber2;
    private List<Double> gpaList1;
    private List<Double> gpaList2;
    private List<Double> cummulativeGPA1;
    private ArrayList<Double> cummulativeGPA2;
    private static int numberOfSession;
    
    /**
     * Constructor for the Document class
     * Creates new objects for each of the List of the student's record
     * @throws WriteException 
     */
    
    public DocumentHandler() throws WriteException{
        this.courseDesc1 = new ArrayList<>();
        this.courseDesc2 = new ArrayList<>();
        this.courseCode1 = new ArrayList<>();
        this.courseCode2 = new ArrayList<>();
        this.courseGrade1 = new ArrayList<>();
        this.courseGrade2 = new ArrayList<>();
        this.courseUnit1 = new ArrayList<>();
        this.courseUnit2 = new ArrayList<>();
        this.serialNumber1 = new ArrayList<>();
        this.serialNumber2 = new ArrayList<>();
        this.gpaList1 = new ArrayList<>();
        this.gpaList2 = new ArrayList<>();
        this.cummulativeGPA1 = new ArrayList<>();
        this.cummulativeGPA2 = new ArrayList<>();
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);   
        cellFormat.setWrap(true);
        buttom3Format.setBorder(Border.ALL, BorderLineStyle.THIN);
        label1Format.setAlignment(Alignment.CENTRE);
        label2Format.setAlignment(Alignment.CENTRE);
        unitAndGradeFormat.setAlignment(Alignment.CENTRE);
        unitAndGradeFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    }
    
    /**
     * Creates the Student's WorkBook at a Student-Specified path
     * @throws WriteException 
     */
    
    void createWorkBook(int numberOfSheets) throws WriteException{ //Extra actual params newUser
       try {
           /*
           //An additional method is needed in the case when the user wants to append sheets
           //to an existing workbook.
           
           //If this is a new user, create the workbook using createSheetsNewUser() method
           //otherwise the appendSheetsToWorkbook() method will be used although the existing user
           //might wish to create a new workbook in which case we must inquire if this is an existing
           //user trying to create a new workbook
           */
           workBook = Workbook.createWorkbook(new FileHandler().createFile());
            /*
             if (newUser){
               if (numberOfSheets==0)
                    createSheetsNewUser(workBook, 1);
               else 
                    createSheetsNewUser(workBook, numberOfSheets);
               }
            else {
                //Existing user and may wish to append or create new workbook
                if (createNewWorkbook)
                    newUser = true;
                    createWorkBook(numberOfSheets, newUser);
               }
            */
            if (numberOfSheets==0)
                createSheets(workBook, 1);
            createSheets(workBook, numberOfSheets); //fix bug here
       }
       catch (IOException e){
           JOptionPane.showMessageDialog(null, e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE, null);
       }
    }
    
    void appendSheetsToWorkBook(){
        //Logi to apend the sheet goes here
    }
    
    /**
     * Creates the Number of WorkSheets to be present in the Student's WorkBook
     * @param workBook the Student's WorkBook
     * @param numberOfSheets the number of WorkSheets specified by the Student
     * @throws WriteException
     * @throws IOException 
     */
    
    void createSheets(WritableWorkbook workBook, int numberOfSheets) throws WriteException, IOException{
        sheets = new WritableSheet[numberOfSheets];
        for (int i=0;i<numberOfSheets;i++){
            sheets[i] = workBook.createSheet("YEAR "+(i+1), i);
        }
        createCells(sheets);
    }
    
    /**
     * Creates the cells in the individual WorkSheet
     * @param sheets An array of WritableSheets created by the number of sheets specified
     * @throws WriteException
     * @throws IOException 
     */
    
    void createCells(WritableSheet[] sheets) throws WriteException, IOException{
        //Creates sheets
        for (WritableSheet sheet : sheets) {
            Label heading1 = new Label(1,0,"RESULT SHEET", label1Format);
            Label heading2 = new Label(1,1,"DELTA STATE UNIVERSITY, ABRAKA", label2Format);
            Label heading3 = new Label(1,2,"LEVEL: ", label3Format);
            Label heading4 = new Label(1,3,"1st Semester", label3Format);
            SheetSettings sheetSettings = sheet.getSettings();
            sheetSettings.setBottomMargin(0.3);
            sheetSettings.setOrientation(PageOrientation.LANDSCAPE);
            sheet.mergeCells(1, 0, 3, 0);
            sheet.mergeCells(1, 1, 3, 1);
            sheet.mergeCells(2, 16, 3, 16);
            sheet.mergeCells(2, 17, 3, 17);
            sheet.mergeCells(2, 32, 3, 32);
            sheet.mergeCells(2, 33, 3, 33);
            sheet.setColumnView(1,65); //Course Description
            sheet.setColumnView(2,16); //Course Code
            sheet.setColumnView(3,20); //Code Unit
            sheet.setColumnView(4,14); //Course Grade
            //First Semester
            Label sn = new Label(0,4,"S/N",cellFormat);            
            Label courseDesc = new Label(1,4,"Course Description",cellFormat);
            Label courseCode = new Label(2,4,"Course Code",cellFormat);
            Label courseUnit = new Label(3,4,"Course Unit",cellFormat);
            Label courseGrade = new Label(4,4,"Course Grade",cellFormat);
            Label totalUnit = new Label(2,15,"TOTAL UNIT",buttom3Format);
            Label gpa = new Label(2,16,"GRADE POINT AVERAGE",buttom3Format);
            Label cgpa = new Label(2,17,"CUMMULATIVE GRADE POINT AVERAGE",buttom3Format);
            sheet.addCell(sn);
            sheet.addCell(courseDesc);
            sheet.addCell(courseCode);
            sheet.addCell(courseUnit);
            sheet.addCell(courseGrade);
            sheet.addCell(totalUnit);
            sheet.addCell(gpa);
            sheet.addCell(cgpa);
            //Second Semester
            Label heading5 = new Label(1,19,"2nd Semester", label3Format);
            Label sn2 = new Label(0,20,"S/N",cellFormat);
            Label courseDesc2 = new Label(1,20,"Course Description",cellFormat);
            Label courseCode2 = new Label(2,20,"Course Code",cellFormat);
            Label courseUnit2 = new Label(3,20,"Course Unit",cellFormat);
            Label courseGrade2 = new Label(4,20,"Course Grade",cellFormat);            
            Label totalUnit2 = new Label(2,31,"TOTAL UNIT",buttom3Format);
            Label gpa2 = new Label(2,32,"GRADE POINT AVERAGE",buttom3Format);
            Label cgpa2 = new Label(2,33,"CUMMULATIVE GRADE POINT AVERAGE",buttom3Format);
            sheet.addCell(sn2);
            sheet.addCell(courseDesc2);
            sheet.addCell(courseCode2);
            sheet.addCell(courseUnit2);
            sheet.addCell(courseGrade2);
            sheet.addCell(totalUnit2);
            sheet.addCell(gpa2);
            sheet.addCell(cgpa2);
            sheet.addCell(heading1);
            sheet.addCell(heading2);
            sheet.addCell(heading3);
            sheet.addCell(heading4);
            sheet.addCell(heading5);
        }
    }
    
    void getFirstSemester(ArrayList<String> desc, ArrayList<String> code, ArrayList<String> grade,
            ArrayList<String> unit, int serialNumber, double gpa, double cgpa){
        this.courseDesc1.add(desc);
        this.courseCode1.add(code);
        this.courseGrade1.add(grade);
        this.courseUnit1.add(unit);
        this.serialNumber1.add(serialNumber);
        this.gpaList1.add(gpa);
        this.cummulativeGPA1.add(cgpa);
    }

    void getSecondSemester(ArrayList<String> desc, ArrayList<String> code, ArrayList<String> grade,
            ArrayList<String> unit, int serialNumber, double gpa, double cgpa){
        this.courseDesc2.add(desc);
        this.courseCode2.add(code);
        this.courseGrade2.add(grade);
        this.courseUnit2.add(unit);
        this.serialNumber2.add(serialNumber);
        this.gpaList2.add(gpa);
        this.cummulativeGPA2.add(cgpa);
    }
    
    void getNumberOfSession(int sheetNumber){
        this.numberOfSession = sheetNumber;
    }
    
    void addDetailsToSheet() throws WriteException{
        setCourseDescription1();
        setCourseDescription2();
        setCourseCode1();
        setCourseCode2();
        setCourseGrade1();
        setCourseGrade2();
        setCourseUnit1();
        setCourseUnit2();
        setSerialNumber1();
        setSerialNumber2();
        setTotalUnit1();
        setTotalUnit2();
        setGpa1();
        setGpa2();
        setCgpa1();
        setCgpa2();
    }
    
    protected void setCourseDescription1() throws WriteException{
        //First Semester
        int row;
        for (int j=0;j<numberOfSession;j++){
            row=5;
            for (int i=0;i<13;i++){
                Label label = new Label(1,row," ",cellFormat);
                sheets[j].addCell(label);
                row++;
            }
        }
        int i=0; //fix bug
        for (List<String> descript: this.courseDesc1){
            row=5;
            descript = sortSpaceList((ArrayList<String>) descript);
            for (String desc1 : descript) {
                Label content = new Label(1, row, desc1, cellFormat);
                sheets[i].addCell(content);
                row++;
            }
            i++;
        }
    }
    
    protected void setCourseDescription2() throws WriteException{
        //Second Semester
        int row;
        for (int j=0;j<numberOfSession;j++){
            row=21;
            for (int i=0;i<13;i++){
                Label label = new Label(1,row," ",cellFormat);
                sheets[j].addCell(label);
                row++;
            }
        }
        int i=0; //fix bug
        for (List<String> descript: this.courseDesc2){
            row=21;
            descript = sortSpaceList((ArrayList<String>) descript);
            for (String desc1 : descript) {
                Label content = new Label(1, row, desc1, cellFormat);
                sheets[i].addCell(content);
                row++;
            }
            i++;
        }
    }
    
    protected void setCourseCode1() throws WriteException{
        //First Semester
        int row;
        for (int j=0;j<numberOfSession;j++){
            row=5;
            for (int i=0;i<13;i++){
                if (i==9) i=12;
                Label label = new Label(2,row," ",cellFormat);
                sheets[j].addCell(label);
                row++;
            }
        }
        int i=0;
        for (List<String> coded : this.courseCode1){
            row=5;
            coded = sortSpaceList((ArrayList<String>) coded);
            for (String code1 : coded){
                code1 = code1.toUpperCase();
                Label content = new Label(2, row, code1, cellFormat);
                sheets[i].addCell(content);
                row++;
            }
            i++;
        }
    }
    
    protected void setCourseCode2() throws WriteException{
        //Second Semester
        int row;
        for (int j=0;j<numberOfSession;j++){
            row=21;
            for (int i=0;i<13;i++){
                if (i==9) i=12;
                Label label = new Label(2,row," ",cellFormat);
                sheets[j].addCell(label);
                row++;
            }
        }
        int i=0;
        for (List<String> coded : this.courseCode2){
            row=21;
            coded = sortSpaceList((ArrayList<String>) coded);
            for (String code1 : coded){
                code1 = code1.toUpperCase();
                Label content = new Label(2, row, code1, cellFormat);
                sheets[i].addCell(content);
                row++;
            }
            i++;
        }
    }
    
    protected void setCourseUnit1() throws WriteException{
        //Frist Semester
        int row;
        for (int j=0;j<numberOfSession;j++){
            row=5;
            for (int i=0;i<13;i++){
                if (i==9) i=12;
                Label label = new Label(3,row," ",cellFormat);
                sheets[j].addCell(label);
                row++;
            }
        }
        int i=0;        
        for (List<String> courseUnit : this.courseUnit1){
            row=5;
            courseUnit = sortNullList((ArrayList<String>) courseUnit);
            for (String unit1 : courseUnit) {
                Label content;
                int num;
                if (unit1.equals("null")){
                    content = new Label(3, row, "", unitAndGradeFormat);
                    sheets[i].addCell(content);
                }
                else {
                    num = Integer.parseInt(unit1);
                    jxl.write.Number number = new jxl.write.Number(3, row, num, unitAndGradeFormat);
                    sheets[i].addCell(number);
                }
            row++;
            }
            i++;
        }
    }
    
    protected void setCourseUnit2() throws WriteException{
        //Second Semester
        int row;
        for (int j=0;j<numberOfSession;j++){
            row=21;
            for (int i=0;i<13;i++){
                if (i==9) i=12;
                Label label = new Label(3,row," ",cellFormat);
                sheets[j].addCell(label);
                row++;
            }
        }
        int i=0;        
        for (List<String> courseUnit : this.courseUnit2){
            row=21;
            courseUnit = sortNullList((ArrayList<String>) courseUnit);
            for (String unit1 : courseUnit) {
                Label content;
                int num;
                if (unit1.equals("null")){
                    content = new Label(3, row, "",unitAndGradeFormat);
                    sheets[i].addCell(content);
                }
                else {
                    num = Integer.parseInt(unit1);
                    jxl.write.Number number = new jxl.write.Number(3, row, num,unitAndGradeFormat);
                    sheets[i].addCell(number);
                }
            row++;
            }
            i++;
        }
    }
    
    protected void setCourseGrade1() throws WriteException{
        //First Semester
        int row;
        for (int j=0;j<numberOfSession;j++){
            row=5;
            for (int i=0;i<13;i++){
                if (i==9) i=12;
                Label label = new Label(4,row," ",cellFormat);
                sheets[j].addCell(label);
                row++;
            }
        }
        int i=0;
        for (List<String> courseGrade : this.courseGrade1){
            row=5;
            courseGrade = sortNullList((ArrayList<String>) courseGrade);
            for (String grade1 : courseGrade){
                Label content;
                if (grade1.equals("null")){
                    content = new Label(4, row, "",unitAndGradeFormat);
                }
                else {
                    content = new Label(4, row, grade1,unitAndGradeFormat);
                }
                sheets[i].addCell(content);
                row++;
            }
            i++;
        }
    }
    
    protected void setCourseGrade2() throws WriteException{
        //Second Semester
        int row;
        for (int j=0;j<numberOfSession;j++){
            row=21;
            for (int i=0;i<13;i++){
                if (i==9) i=12;
                Label label = new Label(4,row," ",cellFormat);
                sheets[j].addCell(label);
                row++;
            }
        }
        int i=0;
        for (List<String> courseGrade : this.courseGrade2){
            row=21;
            courseGrade = sortNullList((ArrayList<String>) courseGrade);
            for (String grade1 : courseGrade){
                Label content;
                if (grade1.equals("null")){
                    content = new Label(4, row, "", unitAndGradeFormat);
                }
                else {
                    content = new Label(4, row, grade1, unitAndGradeFormat);
                }
                sheets[i].addCell(content);
                row++;
            }
            i++;
        }
    }
    
    protected void setSerialNumber1() throws WriteException{
        //First Semester
        int row;
        for (int j=0;j<numberOfSession;j++){
            row=5;
            for (int i=0;i<13;i++){
                Label label = new Label(0,row," ",cellFormat);
                sheets[j].addCell(label);
                row++;
            }
        }
        int j=0;
        while(j<numberOfSession){
            for (Integer sNumber : this.serialNumber1){
                row=5;
                for (int i=0;i<sNumber;i++){
                    Number sn = new Number(0,row,i+1,cellFormat);
                    sheets[j].addCell(sn);
                    row++;
                }
                j++;
            }
        }
    }
    
    protected void setSerialNumber2() throws WriteException{
        //Second Semester
        int row;
        for (int j=0;j<numberOfSession;j++){
            row=21;
            for (int i=0;i<13;i++){
                Label label = new Label(0,row," ",cellFormat);
                sheets[j].addCell(label);
                row++;
            }
        }
        int j=0;
        while(j<numberOfSession){
            for (Integer sNumber : this.serialNumber2){
                row=21;
                for (int i=0;i<sNumber;i++){
                    Number sn = new Number(0,row,i+1,cellFormat);
                    sheets[j].addCell(sn);
                    row++;
                }
                j++;
            }
        }
    }
    
    protected void setTotalUnit1() throws WriteException{
        //First Semester
        for (int i=0;i<numberOfSession;i++){
            Formula add = new Formula(3,15,"SUM(D6:D15)",unitAndGradeFormat);
            sheets[i].addCell(add);
        }
    }
    
    void setTotalUnit2() throws WriteException{
        //Second Semester
        for (int i=0;i<numberOfSession;i++){
            Formula add = new Formula(3,31,"SUM(D22:D31)",unitAndGradeFormat);
            sheets[i].addCell(add);
        }
    }
    
    protected void setGpa1() throws WriteException{
        //First Semester
        int i=0;
        while(i<numberOfSession){
            for (Double num: this.gpaList1){
                if (Double.isNaN(num))
                    num=0.0;
                Number gpa = new Number(4,16,num,unitAndGradeFormat);
                sheets[i].addCell(gpa);
            }
            i++;
        }
    }
    
    protected void setGpa2() throws WriteException{
        //Second Semester
        int i=0;
        while(i<numberOfSession){
            for (Double num: this.gpaList2){
                if (Double.isNaN(num))
                    num=0.0;
                Number gpa = new Number(4,32,num,unitAndGradeFormat);
                sheets[i].addCell(gpa);
            }
            i++;
        }
    }
    
    protected void setCgpa1() throws WriteException{
        //First Semester
        int i=0;
        while(i<numberOfSession){
            for (Double num: this.cummulativeGPA1){
                if (Double.isNaN(num))
                    num=0.0;
                Number cgpa = new Number(4,17,num,unitAndGradeFormat);
                sheets[i].addCell(cgpa);
            }
            i++;
        }
    }
    
    protected void setCgpa2() throws WriteException{
        //Second Semester
        int i=0;
        while(i<numberOfSession){
            for (Double num: this.cummulativeGPA2){
                if (Double.isNaN(num))
                    num=0.0;
                Number cgpa = new Number(4,33,num,unitAndGradeFormat);
                sheets[i].addCell(cgpa);
            }
            i++;
        }
    }
    
    protected void deleteWorkBook() throws IOException {
        java.io.File file = new File("C:/Users/OROBO LUCKY/Desktop/Result.xls"); //fix bug
        file.delete();
    }
    
    ArrayList<String> sortSpaceList(ArrayList<String> array){
       int length = array.size();
       String swap;
       int k=1;
       for (int ptr=1;ptr<length;ptr++){
           for (int i=0;i<length-k;i++){
               if (array.get(i).equals("") && array.get(i+1).isEmpty()==false){
                   swap = array.get(i+1);
                   array.remove(i+1);
                   array.add(i+1,"");
                   array.remove(i);
                   array.add(i, swap);
               }
           }
           k++;
       }
       return array;
    }
    
    ArrayList<String> sortNullList(ArrayList<String> array){
       int length = array.size();
       int k=1;
       String swap;
       for (int ptr=1;ptr<length;ptr++){
           for (int j=0;j<length-k;j++){
              if (array.get(j).equals("null") && array.get(j+1).isEmpty()==false){
                  swap = array.get(j+1);
                  array.remove(j+1);
                  array.add(j+1, "null");
                  array.remove(j);
                  array.add(j,swap);
              } 
           }
           k++;
        }
       return array;
    }
}