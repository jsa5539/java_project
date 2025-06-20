
import javax.swing.*;
import model.Schedule;
import view.CalendarPanel;


public class MainApp extends JFrame {
    public MainApp() {
        setTitle("Calendar App");
        setSize(600, 600);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        CalendarPanel panel = new CalendarPanel();
        Schedule schedule = panel.schedule;
        JLabel note = new JLabel();
        
        panel.setBounds(0, 0, 600, 600);
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainApp();

    }
}
