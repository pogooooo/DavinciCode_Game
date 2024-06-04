import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartUI extends JPanel {

    private JPanel titlePanel, buttonPanel;
    private JLabel lblTitle;
    private JButton btnStart, btnHowto, btnEnd;

    private boolean IsStart=false, IsEnd=false;


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

        btnEnd = new JButton("End");
        btnEnd.setBounds(10, 525, 280, 165);
        btnEnd.addActionListener(new EndAction());
        buttonPanel.add(btnEnd);
    }

    public JButton getBtnStart() {
        return btnStart;
    }

    private class EndAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            IsEnd = true;
            // 프로그램 종료
            System.exit(0);
        }
    }


}
