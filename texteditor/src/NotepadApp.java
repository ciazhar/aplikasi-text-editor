//import  awt dan swing
import java.awt.*;
///action itu buat mouse
import java.awt.event.ActionEvent;///buat input misal klik dobel klik
import java.awt.event.ActionListener;///proses lanjutan
///key itu buat keybord
import java.awt.event.KeyEvent;/// buat input dari keyboard misal ctrl+a
import java.awt.event.KeyListener;///proses lanjutan
import java.io.BufferedWriter; 
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

//swing diawali dengan "J"
public class NotepadApp extends JFrame implements KeyListener, ActionListener{
    ///text area + menu
    private TextArea textarea = new TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);//kita meunggunakan baris dan kolom 0 untuk supaya fit dengan window
    private MenuBar menubar = new MenuBar();
    private Menu file = new Menu();
    private MenuItem newfile = new MenuItem();
    private MenuItem openfile = new MenuItem();
    private MenuItem savefile = new MenuItem();
    private MenuItem saveasfile = new MenuItem();
    private MenuItem closefile = new MenuItem();
    
    ///indikator kesimpan apa belum
    private Boolean is_saved = false;///untuk mengecek file udah disimpan atau belum
    private String file_path = "";///path buat menyimpan kembali file yang udah diedit
    private String default_title ="Text Editor";   
    private String title = default_title;///nilai title yang akan berubah ubah, sedangkan default tetap
    
    //contructor
    public NotepadApp(){
        //window aplikasi
        ///set ukuran window in pixel
        setSize(500, 400);
        ///set judul diambil dari variabel title
        setTitle(default_title);    
        ///set exit on close
        setDefaultCloseOperation(EXIT_ON_CLOSE);///jframe only
        ///set kostumisasi text area
        textarea.setFont(new Font("Arial", Font.PLAIN, 12));
        ///buat key listener
        textarea.addKeyListener(this);
        textarea.requestFocusInWindow();//request focus memungkinkan key listener bekerja sebagaimana mestinya   
        ///deploy text area
        add(textarea);
        
        //menu
        ///buat menubar
        setMenuBar(menubar);
        ///buat menu file
        menubar.add(file);
        file.setLabel("File");///dikasih nama
               
        //buat sub menu New
        newfile.setLabel("New");///dikasih nama
        newfile.addActionListener(this);///men nak di klik ono action e
        newfile.setShortcut(new MenuShortcut(KeyEvent.VK_N, false));//buat shortcut VK_N = ctrl new, false maksudnya tanpa shift
        file.add(newfile);///masukin submenu new ke menu file
        
        //buat sub menu Open
        openfile.setLabel("Open");///dikasih nama
        openfile.addActionListener(this);
        openfile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));//buat shortcut VK_N = ctrl new, false maksudnya tanpa shift
        file.add(openfile);///masukin submenu new ke menu file
        
        //buat sub menu Save
        savefile.setLabel("Save");///dikasih nama
        savefile.addActionListener(this);
        savefile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));//buat shortcut VK_N = ctrl new, false maksudnya tanpa shift
        file.add(savefile);///masukin submenu new ke menu file
        
        //buat sub menu Save as
        saveasfile.setLabel("Save As");///dikasih nama
        saveasfile.addActionListener(this);
        saveasfile.setShortcut(new MenuShortcut(KeyEvent.VK_S, true));//buat shortcut VK_N = ctrl new, false maksudnya tanpa shift
        file.add(saveasfile);///masukin submenu new ke menu file
        
        //buat sub menu Close
        closefile.setLabel("Close");///dikasih nama
        closefile.addActionListener(this);
        closefile.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));//buat shortcut VK_N = ctrl new, false maksudnya tanpa shift
        file.add(closefile);///masukin submenu new ke menu file
    }

    ///implementasi key listener pada constructor
    @Override
    public void keyTyped(KeyEvent e) {///pengecekan saat text area diketikkan
        this.setTitle("*"+ title);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    ///implementasi action listener pada constructor
    @Override
    public void actionPerformed(ActionEvent e) {
        ///untuk menu new 
        if(e.getSource()==newfile){
            title = default_title;
            this.setTitle(title);
            is_saved=false;
            textarea.setText("");
        }
        else if(e.getSource()==closefile){
            ///exit window
            dispose();///close window tapi kadang masih jalan
            System.exit(0);/// proses di system dihentikan
        }
        else if(e.getSource()==openfile){
            JFileChooser open = new JFileChooser();
            int option = open.showOpenDialog(open);
            if(option == JFileChooser.APPROVE_OPTION){
                textarea.setText("");
                try{
                    Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
                    while(scan.hasNext())
                        textarea.append(scan.nextLine()+"\n");
                    title = open.getSelectedFile().getName()+" "+default_title;
                    this.setTitle(title);
                    is_saved= true;
                    file_path = open.getSelectedFile().getPath();
                }
                catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }    
        }
        else if(e.getSource()==savefile){
            if(is_saved == true){
                try(BufferedWriter out = new BufferedWriter(new FileWriter(file_path))){
                    out.write(textarea.getText());///yg udah ditulis nanti dikirim ke write ke file path
                    title = title.substring(0);
                    this.setTitle(title);
                }
                catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
            else{
                JFileChooser save = new JFileChooser();
                int option = save.showSaveDialog(save);
                if(option == JFileChooser.APPROVE_OPTION){
                    try{
                        try(BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()))){
                            out.write(textarea.getText());
                        }
                        title = save.getSelectedFile().getName()+" "+title;
                        this.setTitle(title);
                        is_saved = true;
                        file_path = save.getSelectedFile().getPath();
                    }
                    catch(Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
        else if(e.getSource()==saveasfile){
            JFileChooser saveas = new JFileChooser();
            int option = saveas.showSaveDialog(saveas);
            if(option == JFileChooser.APPROVE_OPTION){
                    try{
                        try(BufferedWriter out = new BufferedWriter(new FileWriter(saveas.getSelectedFile().getPath()))){
                            out.write(textarea.getText());
                        }
                        title = saveas.getSelectedFile().getName()+" "+title;
                        this.setTitle(title);
                        is_saved = true;
                        file_path = saveas.getSelectedFile().getPath();
                    }
                    catch(Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }
        }
    }
    
    public static void main(String args[]){
        NotepadApp notepad = new NotepadApp();
        notepad.setVisible(true);
    }  

}
