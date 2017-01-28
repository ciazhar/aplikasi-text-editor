package texteditorv2;
public class main {
    
    static Frame f;///deklarasi variabel f
    
    public static void main(String[] args) {
        Frame f = new Frame(); ///buat objek f
        f.setTitle("Text Editor v2");///judul
        f.setSize(600,400);///ukuran frame dlm pixel
        f.setLocationRelativeTo(null);///set posisi frame. null berarti ada di center
        f.setVisible(true);///membuat f menjadi kelihatan
        
    }
    
}
