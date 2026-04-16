import javax.swing.*;
import java.awt.*;

public final class AsciiWindow extends JFrame {
    private JTextArea asciiArea;
    private JPanel contentPane;
    private JCheckBox invertCheckBox;
    private JCheckBox mirrorCheckBox;
    private JComboBox<CharSet> charsetCombo;
    private JCheckBox edgesCheckBox;
    private JCheckBox noiseCheckBox;
    private JButton exitButton;
    private JSlider resXSlider;
    private JSlider resYSlider;
    private JLabel resXLabel;
    private JLabel resYLabel;
    
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
        
        for (CharSet value : CharSet.getEntries())
            charsetCombo.addItem(value);
        
        String resXText = String.valueOf(resXSlider.getValue());
        String resYText = String.valueOf(resYSlider.getValue());
        resXLabel.setText(resXText);
        resYLabel.setText(resYText);
        resXSlider.addChangeListener(e -> resXLabel.setText(resXText));
        resYSlider.addChangeListener(e -> resYLabel.setText(resYText));
        
        exitButton.addActionListener(e -> System.exit(0));
    }
    
    public AsciiSettings getSettings() {
        return new AsciiSettings(
                invertCheckBox.isSelected(),
                mirrorCheckBox.isSelected(),
                edgesCheckBox.isSelected(),
                noiseCheckBox.isSelected(),
                (CharSet) charsetCombo.getSelectedItem(),
                resXSlider.getValue(),
                resYSlider.getValue()
        );
    }
    
    public void setAsciiArea(String ascii) {
        asciiArea.setText(ascii);
    }
}
