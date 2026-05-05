import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.swing.*;
import java.lang.reflect.Field;

class AsciiWindowTest {
    private AsciiWindow window;
    
    @BeforeEach
    void setUp() {
        window = new AsciiWindow();
    }
    
    @Test
    void sliderLabels() throws NoSuchFieldException {
        JSlider resXSlider = (JSlider) getPrivateInstance(window, "resXSlider"),
                resYSlider = (JSlider) getPrivateInstance(window, "resYSlider");
        
        resXSlider.setValue(1234);
        resYSlider.setValue(1234);
        
        JLabel resXLabel = (JLabel) getPrivateInstance(window, "resXLabel"),
                resYLabel = (JLabel) getPrivateInstance(window, "resYLabel");
        
        assert resXLabel.getText().equals(String.valueOf(resXSlider.getValue()))
                && resYLabel.getText().equals(String.valueOf(resYSlider.getValue()));
    }
    
    @Test
    void comboBoxEntries() throws NoSuchFieldException {
        JComboBox<CharSet> charsetCombo = (JComboBox<CharSet>) getPrivateInstance(window, "charsetCombo");
        for (int i = 0; i < charsetCombo.getItemCount(); i++) {
            CharSet item = charsetCombo.getItemAt(i);
            assert CharSet.getEntries().contains(item);
        }
    }
    
    @Test
    void title() {
        assert window.getTitle().equals("pascii");
    }
    
    @ParameterizedTest
    @CsvSource({
            "true,false,false,false,ASCII_MINIMAL,10,10",
            "false,true,false,false,ASCII_MINIMAL,10,10",
            "false,false,true,false,ASCII_MINIMAL,10,10",
            "false,false,false,true,ASCII_MINIMAL,10,10",
            "false,false,false,false,ASCII_DETAILED,10,10",
            "false,false,false,false,ASCII_MINIMAL,100,10",
            "false,false,false,false,ASCII_MINIMAL,10,100",
            "true,false,true,false,ASCII_DETAILED,12,34",
            "false,true,false,true,BLOCKY,43,21"
    })
    void getSettings(boolean inverted, boolean mirror,
                     boolean edges, boolean noise,
                     CharSet charSet, int resX,
                     int resY) throws NoSuchFieldException {
        JCheckBox invertCheckBox = (JCheckBox) getPrivateInstance(window, "invertCheckBox"),
                mirrorCheckBox = (JCheckBox) getPrivateInstance(window, "mirrorCheckBox"),
                edgesCheckBox = (JCheckBox) getPrivateInstance(window, "edgesCheckBox"),
                noiseCheckBox = (JCheckBox) getPrivateInstance(window, "noiseCheckBox");
        JComboBox<CharSet> charsetCombo = (JComboBox<CharSet>) getPrivateInstance(window, "charsetCombo");
        JSlider resXSlider = (JSlider) getPrivateInstance(window, "resXSlider"),
                resYSlider = (JSlider) getPrivateInstance(window, "resYSlider");
        
        invertCheckBox.setSelected(inverted);
        mirrorCheckBox.setSelected(mirror);
        edgesCheckBox.setSelected(edges);
        noiseCheckBox.setSelected(noise);
        charsetCombo.setSelectedItem(charSet);
        resXSlider.setValue(resX);
        resYSlider.setValue(resY);
        
        AsciiSettings settings = new AsciiSettings(
                invertCheckBox.isSelected(),
                mirrorCheckBox.isSelected(),
                edgesCheckBox.isSelected(),
                noiseCheckBox.isSelected(),
                (CharSet) charsetCombo.getSelectedItem(),
                resXSlider.getValue(),
                resYSlider.getValue()
        );
        
        assert settings.isInverted() == inverted
                && settings.isMirrored() == mirror
                && settings.getOnlyEdges() == edges
                && settings.getNoise() == noise
                && settings.getCharSet() == charSet
                && settings.getResX() == resX
                && settings.getResY() == resY;
    }
    
    @Test
    void setAsciiArea() throws NoSuchFieldException {
        String message = "Test Text";
        window.setAsciiArea(message);
        JTextArea area = (JTextArea) getPrivateInstance(window, "asciiArea");
        assert area.getText().equals(message);
    }
    
    private Object getPrivateInstance(Object target, String name) throws NoSuchFieldException {
        for (Class<?> c = target.getClass(); c != null; c = c.getSuperclass()) {
            try {
                Field field = c.getDeclaredField(name);
                field.setAccessible(true);
                return field.get(target);
            } catch (NoSuchFieldException ignored) {} catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        
        throw new NoSuchFieldException(name);
    }
}
