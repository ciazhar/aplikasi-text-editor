package texteditorv2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.MenuShortcut;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.*;

public class Frame extends JFrame{
    ///menu bar
    public JMenuBar menu;
    ///menu
    public JMenu file;
    public JMenu edit;
    public JMenu font;
    public JMenu fontDecor;
    public JMenu fontSize;
    public JMenu help;
    ///menu item file
    public JMenuItem fileNew;
    public JMenuItem fileOpen;
    public JMenuItem fileSave;
    public JMenuItem fileSaveAs;
    public JMenuItem fileExit;
    ///menu item edit
    public JMenuItem editToggleEditable;
    ///menu font
    public JMenuItem fontArial;
    public JMenuItem fontSerif;
    public JMenuItem fontComicSansMS;
    public JMenuItem fontConsolas;
    /// menu font decor
    public JMenuItem fontDecorPlain;
    public JMenuItem fontDecorBold;
    public JMenuItem fontDecorItalic;
    public JMenuItem fontDecorBoldItalic;
    /// menu font size
    public JMenuItem fontSizeSmall;
    public JMenuItem fontSizeNormal;
    public JMenuItem fontSizeBig;
    public JMenuItem fontSizeHuge;
    ///menu help
    public JMenuItem helpAbout;
    public JMenuItem helpHelp;
    ///textarea
    public JTextArea field;
    ///scroll
    public JScrollPane scroller;
    ///status
    public JLabel status;
    ///font
    public String fontF = "Arial";
    public int fontD = Font.PLAIN;
    public int fontS = 14;
    public Font origFont = new Font(fontF, fontD, fontS);
    ///file chooser buat sub menu open
    public JFileChooser fileSelector;
    ///
    public File currentFile;
    
    ///
    public AboutFrame aboutF;
    
    ///
    public HelpFrame helpF;
    
    
    public Frame(){
        ///setting & deploy menu
        //buat objek
        menu = new JMenuBar();
        
        file = new JMenu("File");
        edit = new JMenu("Edit");
        font = new JMenu("Font");
        fontDecor = new JMenu("Font Decor");
        fontSize = new JMenu("Font Size");
        help = new JMenu("Help");
        
        fileNew = new JMenuItem("New");
        fileOpen = new JMenuItem("Open");
        fileSave = new JMenuItem("Save");
        fileSaveAs = new JMenuItem("Save As");
        fileExit = new JMenuItem("Exit");
        ///masukin sub menu ke menu
        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileSave);
        file.add(fileSaveAs);
        file.add(fileExit);
        
        editToggleEditable = new JMenuItem("Toogle Editable");
        
        edit.add(editToggleEditable);
        
        fontArial = new JMenuItem("Arial");
        fontSerif = new JMenuItem("Serif");
        fontComicSansMS = new JMenuItem("Comic");
        fontConsolas = new JMenuItem("Consolas");
        
        font.add(fontArial);
        font.add(fontSerif);
        font.add(fontComicSansMS);
        font.add(fontConsolas);
        
        fontDecorPlain = new JMenuItem("Plain");
        fontDecorBold = new JMenuItem("Bold");
        fontDecorItalic = new JMenuItem("Italic");
        fontDecorBoldItalic = new JMenuItem("Bold Italic");     
        
        fontDecor.add(fontDecorPlain);
        fontDecor.add(fontDecorBold);
        fontDecor.add(fontDecorItalic);
        fontDecor.add(fontDecorBoldItalic);
        
        fontSizeSmall = new JMenuItem("Small");
        fontSizeNormal = new JMenuItem("Normal");
        fontSizeBig = new JMenuItem("Big");
        fontSizeHuge = new JMenuItem("Huge");
        
        fontSize.add(fontSizeSmall);
        fontSize.add(fontSizeNormal);
        fontSize.add(fontSizeBig);
        fontSize.add(fontSizeHuge);
        
        helpAbout = new JMenuItem("About");
        helpHelp = new JMenuItem("Help");
        
        help.add(helpAbout);
        help.add(helpHelp);
        
        menu.add(file);
        menu.add(edit);
        menu.add(font);
        menu.add(fontDecor);
        menu.add(fontSize);
        menu.add(help);
        
        add(menu, BorderLayout.NORTH);///deploy menu dan atur layout di bagian atas(north)
        
        ///setting text area
        field = new JTextArea();
        field.setLineWrap(true);///kalo line kepanjangan bakal diwrap
        field.setWrapStyleWord(true);
        
        ///setting status
        status = new JLabel("Welcome ea");
        
        ///setting default font
        field.setFont(origFont);
        
        ///setting scroll
        scroller = new JScrollPane(field);
        add(scroller, BorderLayout.CENTER);
        
        ///setting file selector buat sub menu open
        fileSelector = new JFileChooser();
        
        ///
        add(status,BorderLayout.SOUTH);
        
        ///buat njalanin sub menu dan menu
        fileNew.addActionListener(new ActionListener() {///new sub menu
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null,"Are you sure want to crate  a new file ?")==0){
                    field.setText("");///tulisan yg tambil di field
                    status.setText("Generated New File...");
                }
                setTitle("Text Editor V2");
            }
        });
        fileOpen.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(JOptionPane.showConfirmDialog(null, "Are You sure you wish to trash the current file ?")==0){
                    int openResult = fileSelector.showOpenDialog(null);
                    if(openResult==fileSelector.APPROVE_OPTION){
                        openFile(fileSelector.getSelectedFile());
                        status.setText("Opened File ...");
                    }
                }
            }
        });
        fileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentFile == null){
                    int saveResult = fileSelector.showSaveDialog(null);
                    if(saveResult == fileSelector.APPROVE_OPTION){
                        saveFile(fileSelector.getSelectedFile(),field.getText());
                    }
                }
                else {
                    saveFile(currentFile,field.getText());
                }
                status.setText("FIle Saved....");
                
            }
        });
        fileSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int saveResult = fileSelector.showSaveDialog(null);
                saveFile(fileSelector.getSelectedFile(),field.getText());
                status.setText("File Saved...");
            }
        });
        fileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "Are you sure do you want to exit without saving ?")==0){
                    closeWindow();
                }
            }
        });
        editToggleEditable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(field.isEditable()){
                    field.setEditable(false);
                    status.setText("Field is no longer Editable...");
                }
                else if(!field.isEditable()){
                    field.setEditable(true);
                    status.setText("Field is Now Editable...");
                }
            }
        });
        fontArial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontF  = "Arial";
                field.setFont(new Font (fontF,fontD, fontS));
                status.setText("Font Changed to Arial...");
            }
        });
        fontSerif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontF  = "Serif";
                field.setFont(new Font (fontF,fontD, fontS));
                status.setText("Font Changed to Serif...");
            }
        });
        fontComicSansMS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontF  = "Comic Sans MS";
                field.setFont(new Font (fontF,fontD, fontS));
                status.setText("Font Changed to Comic Sans MS...");
            }
        });
        fontConsolas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontF  = "Consolas";
                field.setFont(new Font (fontF,fontD, fontS));
                status.setText("Font Changed to Consolas...");
            }
        });
        fontDecorPlain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontD = Font.PLAIN;
                field.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decoration Set to Plain...");
            }
        });
        fontDecorBold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontD = Font.BOLD;
                field.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decoration Set to Bold...");
            }
        });
        fontDecorItalic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontD = Font.ITALIC;
                field.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decoration Set to Italic...");
            }
        });
        fontDecorBoldItalic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontD = Font.BOLD + Font.ITALIC;
                field.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decoration Set to Bold and Italic...");
            }
        });
        
        fontSizeSmall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontS = 7;
                field.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decoration Set to Small...");
            }
        });
        
        fontSizeNormal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontS = 14;
                field.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decoration Set to Normal...");
            }
        });
        
        fontSizeBig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontS = 21;
                field.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decoration Set to Big...");
            }
        });
        
        fontSizeHuge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontS = 28;
                field.setFont(new Font(fontF, fontD, fontS));
                status.setText("Font Decoration Set to Huge...");
            }
        });
        helpAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aboutF = new AboutFrame();
                
                aboutF.setSize(600,400);
                aboutF.setVisible(true);
                aboutF.setResizable(false);
                aboutF.setLocationRelativeTo(null);
            }
        });
        helpHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpF = new HelpFrame();
                
                aboutF.setSize(600,400);
                aboutF.setVisible(true);
                aboutF.setResizable(false);
                aboutF.setLocationRelativeTo(null);
            }
        });
    }
    
            private void openFile(File file) {
                if(file.canRead()){
                    String filePath = file.getPath();
                    String fileContents = "";
                    if(filePath.endsWith(".txt")){
                        try{
                            Scanner scan = new Scanner(new FileInputStream(file));
                            while(scan.hasNextLine()){
                                fileContents += scan.nextLine();
                            }
                            scan.close();
                        }
                        catch(FileNotFoundException e){
                        
                        }
                        field.setText(fileContents);
                        setTitle("Text Editor V2"+filePath);
                        currentFile = file;
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "That file type isn't supported !\nOnly .txt file is supported");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Could not open file ...");
                }
            }
            
            private void saveFile(File file, String contents) {
                BufferedWriter writer = null;
                String filePath = file.getPath();
                if(!filePath.endsWith(".txt")){
                    filePath += ".txt";
                }
                try{
                    writer = new BufferedWriter(new FileWriter(filePath));
                    writer.write(contents);
                    writer.close();
                    field.setText(contents);
                    setTitle("Text Editor V2"+filePath);
                    currentFile = file;
                }
                catch(Exception e){
                    
                }
            }
            public void closeWindow(){
                WindowEvent close = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(close);
            }
    
};