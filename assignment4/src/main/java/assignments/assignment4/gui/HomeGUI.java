package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static assignments.assignment3.nota.NotaManager.toNextDay;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        GridBagConstraints gbc = new GridBagConstraints(); //mengatur posisi komponen

        // konfigurasi komponen
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.gridy = 0;  
        titleLabel = new JLabel("Selamat datang di CuciCuci System!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        mainPanel.add(titleLabel, gbc);

        gbc.gridy = 1;  
        gbc.insets = new Insets(30,0,0,0); //padding
        loginButton = new JButton("Login");
        mainPanel.add(loginButton, gbc);
        handleToLogin(loginButton);

        gbc.gridy = 2;  
        registerButton = new JButton("Register");
        mainPanel.add(registerButton, gbc);
        handleToRegister(registerButton);

        gbc.gridy = 3;  
        toNextDayButton = new JButton("Next Day");
        mainPanel.add(toNextDayButton, gbc);
        handleNextDay(toNextDayButton);

        gbc.gridy = 4;  
        dateLabel = new JLabel("Hari ini: " + NotaManager.fmt.format(NotaManager.cal.getTime()), JLabel.CENTER);
        mainPanel.add(dateLabel, gbc);
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister(JButton registerButton) {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                MainFrame.getInstance().navigateTo(RegisterGUI.KEY);
            }
        });
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin(JButton loginButton ) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().navigateTo(LoginGUI.KEY);
            }
        });
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay(JButton toNextDayButton) {
        toNextDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainPanel, "Kamu tidur hari ini... zzz...", "Next Day!!!", JOptionPane.INFORMATION_MESSAGE);
                toNextDay();
                dateLabel.setText("Hari ini: " + NotaManager.fmt.format(NotaManager.cal.getTime())); //mengganti label hari
                MainFrame.getInstance().navigateTo(HomeGUI.KEY);
            }
        });
    }
}
