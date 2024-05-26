import java.awt.*;
import javax.swing.*;

public class StartUI extends JPanel {

    private JPanel titlePanel, buttonPanel;
    private JLabel lblTitle;
    private JButton btnStart, btnHowto, btnEnd;

    public StartUI() {
        setBackground(Color.white);
        setLayout(null);


        // set Panel
        titlePanel = new JPanel();
        titlePanel.setBounds(0, 0, 700, 700); // x, y, w, h
        titlePanel.setBackground(new Color(220, 220, 220));
        titlePanel.setLayout(null);
        add(titlePanel);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(700, 0, 300, 700);
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setLayout(null);
        add(buttonPanel);

        // set Label
        lblTitle = new JLabel("DaVinci Code");
        lblTitle.setBounds(0, 0, 500, 50);
        lblTitle.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 50));
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(lblTitle);

        // set Button
        btnStart = new JButton("Start");
        btnStart.setBounds(10, 10, 280, 330);
        buttonPanel.add(btnStart);

        btnHowto = new JButton("How To");
        btnHowto.setBounds(10, 350, 280, 165);
        buttonPanel.add(btnHowto);

        btnEnd = new JButton("End");
        btnEnd.setBounds(10, 525, 280, 165);
        buttonPanel.add(btnEnd);
    }
}
