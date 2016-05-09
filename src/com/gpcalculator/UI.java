package com.gpcalculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import jxl.write.WriteException;


/**
 * This class designs and creates the graphical user interface for the program
 * GPCalculator.
 * 
 * @author OROBOGENIUS
 */
public class UI extends JPanel {
    
    protected static JFrame window; //main window
    protected static ImageIcon icon = null;
    protected static final JMenuBar MENUBAR = new JMenuBar();
    protected static JPanel contentPanel;
    protected static JPanel startPanel;
    protected static JPanel newDocumentPanel;
    protected static JPanel documentPanel;
    protected static CardLayout card;
    protected static JPanel resultPanel;
    protected static JPanel buttonPanel;
    protected static JPanel topPanel;
    protected static JPanel bottomPanel;
    protected static final Border BORDERLINE = BorderFactory.createLineBorder(Color.WHITE, 2, true);
    protected static JButton calcButton, nextSemButton, clearButton, documentButton;
    protected static ArrayList<JTextField> courseField;
    protected static ArrayList<JTextField> codeField;
    protected static ArrayList<JComboBox> gradeBox;
    protected static ArrayList<JComboBox> unitBox;
    protected static ArrayList<JCheckBox> checkBox;
    protected static ArrayList<JButton> clearFieldButton;
    protected static JTextArea resultArea;

    //------ Constructor -------\\
    public UI() throws WriteException{
        super(new BorderLayout(2,2));
    }
    
    //----- GUI -----\\
    protected static void prepareGUI() throws WriteException {
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setOpaque(true);
        startPanel = new JPanel();
        startPanel.setLayout(null);
        startPanel.setBackground(new Color(255, 158, 142));
        newDocumentPanel = new JPanel(new BorderLayout(2,2));
        newDocumentPanel.setBackground(new Color(225, 158, 142));
        documentPanel = new JPanel();
        documentPanel.setLayout(null);
        documentPanel.setBackground(new Color(225, 158, 142));
        setUpPanels();
        addFileMenu();
        addHelpMenu();
        window = new JFrame("ResultDOC");
        URL imageURL = RunProgram.class.getResource("app_icon.png");
        if (imageURL!=null){
            icon = new ImageIcon(imageURL, "Application Icon");
            window.setIconImage(icon.getImage());
        }
        window.setJMenuBar(MENUBAR);
        window.add(contentPanel);
        window.setContentPane(contentPanel);
        window.setSize(900, 600);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?\nAny unsaved document might be lost!", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    System.exit(0);
                }
            }
        });
        UI.window.setResizable(false);
        UI.window.setVisible(true);
    }

    //----- Panels ----\\
    private static void setUpPanels() throws WriteException {
        topPanel();
        buttomPanel();
        JLabel welcomeText = new JLabel("WELCOME TO");
        JLabel progName = new JLabel("ResultDOC");
        JLabel aboutProg = new JLabel();
        progName.setFont(new Font("Georgia", Font.BOLD, 25));
        welcomeText.setFont(new Font("Georgia", Font.BOLD, 45));
        welcomeText.setBounds(300, 10, 500, 100);
        progName.setBounds(380, 50, 500, 100);
        String infoText = "<html>To Begin working with this program please select an option"
                + " from the File Menu</html>";
        String aboutText = "<html>ResultDOC is a Result Documentator / GPA Calculator"
                + " for undergraduate students. It helps to Create, Calculate and Document result"
                + " from single semester to multiple semesters as well as the entire duration of an"
                + " undergraduate course.</html>";
        aboutProg.setText(aboutText);
        aboutProg.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        aboutProg.setBounds(200, 200, 600, 100);
        JLabel info = new JLabel(infoText, SwingConstants.CENTER);
        info.setBounds(200, 400, 500, 100);
        info.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        startPanel.add(welcomeText);
        startPanel.add(progName);
        startPanel.add(aboutProg);
        startPanel.add(info);
        newDocumentPanel.add(bottomPanel, BorderLayout.SOUTH);
        newDocumentPanel.add(topPanel, BorderLayout.CENTER);
        contentPanel.add(startPanel, "Start Panel");
        contentPanel.add(newDocumentPanel, "New Document Panel");
        contentPanel.add(documentPanel, "Document Panel");
    }

    //----- Top Panel ----\\
    private static void topPanel() throws WriteException {
        topPanel = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(null);
        northPanel.setOpaque(false);
        northPanel.setPreferredSize(new Dimension(600, 30));
        JLabel course, code, grade, unit;
        course = new JLabel("Course Description");
        code = new JLabel("Course Code");
        grade = new JLabel("Course Grade");
        unit = new JLabel("Course Unit");
        course.setBounds(15, 5, 330, 30);
        code.setBounds(410, 5, 330, 30);
        grade.setBounds(510, 5, 330, 30);
        unit.setBounds(600, 5, 330, 30);
        northPanel.add(course);
        northPanel.add(code);
        northPanel.add(grade);
        northPanel.add(unit);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        centerPanel.setOpaque(false);

        //------ Creates the centerPanel for the topPanel -------\\
        //----- Creates components for the centerPanel ----\\

        //course components
        courseField = new ArrayList<>();
        codeField = new ArrayList<>();
        gradeBox = new ArrayList<>();
        clearFieldButton = new ArrayList<>();
        unitBox = new ArrayList<>();
        checkBox = new ArrayList<>();
        String[] units = {"1", "2", "3", "4", "5", "6"};
        JTextField textField;
        for (int i = 0; i < 10; i++) {
            textField = new JTextField();
            textField.addActionListener(new CalculatorHandler());
            textField.setPreferredSize(new Dimension(400, 30));
            courseField.add(textField);
            centerPanel.add(textField);

            //code components
            JTextField textField2;
            textField2 = new JTextField();
            textField2.addActionListener(new CalculatorHandler());
            textField2.setPreferredSize(new Dimension(100, 30));
            codeField.add(textField2);
            centerPanel.add(textField2);

            //----- Creates JComboBox for courseGrade and courseUnit ---\\
            //courseGrade
            JComboBox combo = new JComboBox(CalculatorHandler.Grade.values());
            combo.setSelectedIndex(-1);
            combo.setPreferredSize(new Dimension(80, 30));
            combo.addActionListener(new CalculatorHandler());
            centerPanel.add(combo);
            gradeBox.add(combo);
            //courseUnit
            JComboBox combo2 = new JComboBox(units);
            combo2.setSelectedIndex(-1);
            combo2.setPreferredSize(new Dimension(80, 30));
            combo2.addActionListener(new CalculatorHandler());
            centerPanel.add(combo2);
            unitBox.add(combo2);
            //checkboxes
            JCheckBox check = new JCheckBox();
            check.setText("Validate");
            check.addActionListener(new CalculatorHandler());
            check.setOpaque(false);
            centerPanel.add(check);
            checkBox.add(check);

            //clearFieldButtons
            JButton clearField = new JButton("Clear field");
            clearField.addActionListener(new CalculatorHandler());
            centerPanel.add(clearField);
            clearFieldButton.add(clearField);
        } //end of for loop
        
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createTitledBorder(BORDERLINE, "NEW SEMESTER", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.TOP, new Font("Georgia", Font.BOLD, 12), Color.WHITE));
        topPanel.add(northPanel, BorderLayout.NORTH);
        topPanel.add(centerPanel, BorderLayout.CENTER);
    }

    //----- Buttom Panel ----\\
    private static void buttomPanel() throws WriteException {
        bottomPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        resultPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(4, 4, 4, 4);
        constraint.gridy = 0;

        bottomPanel.add(resultPanel);
        bottomPanel.add(buttonPanel);
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(900, 140));

        //------ Creates the JTextAreaPanel && ButtonPanel for the bottomPanel -------\\
        
        //JTextArea
        resultArea = new JTextArea(6, 5);
        resultArea.setBorder(BorderFactory.createTitledBorder(BORDERLINE, "Result",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.TOP, new Font("Georgia", Font.BOLD, 12), Color.WHITE));
        resultArea.setEditable(false);
        resultArea.setMargin(new Insets(4, 4, 4, 4));
        resultArea.setBackground(new Color(45, 100, 125));
        resultArea.setForeground(Color.WHITE);
        resultArea.setFont(new Font("Sans Serif", Font.BOLD, 13));
        resultArea.setText("Calculated Result: ");
        JScrollPane scrollBar = new JScrollPane(resultArea);
        resultPanel.add(scrollBar, BorderLayout.CENTER);

        //Button
        buttonPanel.setOpaque(false);
        calcButton = new JButton("Calculate GPA");
        nextSemButton = new JButton("Next Semester");
        clearButton = new JButton("Clear All");
        documentButton = new JButton("Document Result");
        calcButton.addActionListener(new CalculatorHandler());
        calcButton.setMnemonic(KeyEvent.VK_C);
        nextSemButton.addActionListener(new CalculatorHandler());
        nextSemButton.setMnemonic(KeyEvent.VK_N);
        clearButton.addActionListener(new CalculatorHandler());
        documentButton.addActionListener(new CalculatorHandler());
        buttonPanel.add(calcButton, constraint);
        buttonPanel.add(nextSemButton, constraint);
        buttonPanel.add(clearButton, constraint);
        buttonPanel.add(documentButton, constraint);
    }

    private static void addHelpMenu() {
        MENUBAR.setOpaque(true);
        MENUBAR.setBackground(new Color(156, 165, 127));
        MENUBAR.setPreferredSize(new Dimension(200, 20));
        JMenu helpMenu = new JMenu("Help");
        JMenuItem howTo = new JMenuItem("How To");
        howTo.addActionListener((ActionEvent event) -> {
            JFrame help = new JFrame("Help");
            help.setSize(450,300);
            help.setContentPane(createHowToContentPane());
            help.setLocationRelativeTo(null);
            help.setResizable(false);
            help.setVisible(true);
        });
        JMenuItem about = new JMenuItem("About GPCalculator");
        about.addActionListener((ActionEvent evt) -> {
            JFrame about1 = new JFrame("About GPCalculator");
            about1.setContentPane(createAboutContentPane());
            JDialog dialog = new JDialog(about1, "About GPCalculator");
                @Override
                public void windowClosing(WindowEvent evt){
                    dialog.setVisible(false);
                }
            });
            dialog.add(about1.getContentPane());
            dialog.setVisible(true);
            dialog.setResizable(false);
            dialog.setSize(400, 180);
            dialog.setLocationRelativeTo(window);
        });
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpMenu.add(howTo);
        helpMenu.add(about);
        MENUBAR.add(helpMenu);
    }
    
    private static void addFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newDocument = new JMenuItem("New Document");
        newDocument.addActionListener((ActionEvent e) -> {
            FileMenuHandler.newDocumentHandler();
        });
        JMenuItem updateDocument = new JMenuItem("Update Document");
        updateDocument.addActionListener((ActionEvent e) -> {
            FileMenuHandler.updateDocumentHandler();
        });
        JMenuItem modifyDocument = new JMenuItem("Modify Document");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((ActionEvent ev) -> {
            FileMenuHandler.exitHandler();
        });
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(newDocument);
        fileMenu.add(updateDocument);
        fileMenu.add(modifyDocument);
        fileMenu.add(exit);
        MENUBAR.add(fileMenu);
    }
    
    private static Container createAboutContentPane(){
        JPanel contentPane = new JPanel();
        contentPane.setOpaque(true);
        contentPane.setLayout(null);
        JLabel label1 = new JLabel("GPCalculator");
        JLabel label2 = new JLabel("Version:          2.0");
        JLabel label3 = new JLabel("Created By:    Orobogenius");
        JLabel label4 = new JLabel("License:          Freeware");
        JLabel label5 = new JLabel("Email:              orobogenius@gmail.com");
        label1.setFont(new Font("Arial Black", Font.BOLD, 20));
        label1.setBounds(80, 3, 250, 50);
        label2.setBounds(80, 28, 250, 50);
        label3.setBounds(80, 48, 250, 50);
        label4.setBounds(80, 68, 250, 50);
        label5.setBounds(80, 88, 250, 50);
        contentPane.add(label1);
        contentPane.add(label2);
        contentPane.add(label3);
        contentPane.add(label4);
        contentPane.add(label5);
        return contentPane;
    }
    
    private static Container createHowToContentPane() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);
        JTextArea area = new JTextArea(400, 250);
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        return contentPane;
    }
    
}