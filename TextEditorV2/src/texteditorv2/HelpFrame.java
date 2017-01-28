package texteditorv2;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class HelpFrame extends JFrame{
    public JTextArea helpA;
    
    public HelpFrame(){
        setTitle("Help...");
        helpA = new JTextArea("blablabla");
        helpA.setLineWrap(true);
        helpA.setWrapStyleWord(true);
        helpA.setEditable(false);
        add(helpA,BorderLayout.CENTER);
    }
}
