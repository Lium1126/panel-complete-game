import javax.swing.*;
import java.util.List;

public class PanelLabelData {
    private JPanel panel;
    private JLabel label;
    private List<Integer> toggles;

    private PanelLabelData() {}

    public PanelLabelData(JPanel panel, JLabel label, List<Integer> toggles) {
        setPanel(panel);
        setLabel(label);
        setToggles(toggles);
    }

    public JPanel getPanel() {
        return panel;
    }
    public JLabel getLabel() {
        return label;
    }
    public List<Integer> getToggles() { return toggles; }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
    public void setLabel(JLabel label) {
        this.label = label;
    }
    public void setToggles(List<Integer> toggles) { this.toggles = toggles; }
}
