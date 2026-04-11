import javax.swing.*;
import java.awt.*;

public final class AsciiWindow extends JFrame {
    private JTextArea asciiArea;
    private JPanel contentPane;
    
    public AsciiWindow() {
        setTitle("pascii");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();
        
        // Set the frame location to the center of the screen
        setLocationRelativeTo(null);
        
        // Set the frame visible
        setVisible(true);
        
        asciiArea.setBackground(Color.BLACK);
        asciiArea.setForeground(Color.WHITE);
    }
    
    public void setAsciiArea(String ascii) {
        asciiArea.setText(ascii);
    }
}
