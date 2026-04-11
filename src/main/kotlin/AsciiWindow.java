import javax.swing.*;

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
    }
    
    public void setAsciiArea(String ascii) {
        asciiArea.setText(ascii);
    }
}
