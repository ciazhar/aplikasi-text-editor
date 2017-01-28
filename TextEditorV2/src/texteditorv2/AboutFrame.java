package texteditorv2;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class AboutFrame extends JFrame{
    
    public JTextArea exp;
    
    public AboutFrame(){
        setTitle("About TextEditor");
        
        exp = new JTextArea("Welcome to Blalalalalallalaaaaaaaaaaaaaaaaa\n Bwa wbababwabwabwbbwawabb\n ulullululululululuul");
        
        exp.setLineWrap(true);
        exp.setWrapStyleWord(true);
        exp.setEditable(false);
        add(exp,BorderLayout.CENTER);
    }
}


