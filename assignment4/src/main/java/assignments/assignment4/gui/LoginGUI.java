package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

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
        GridBagConstraints gbc = new GridBagConstraints();  //mengatur posisi komponen

        // konfigurasi komponen
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridy = 0;  
        idLabel = new JLabel("Masukan ID Anda:", SwingConstants.LEFT);
        mainPanel.add(idLabel, gbc);

        gbc.gridx = 1;  
        gbc.ipadx = 200;
        idTextField = new JTextField();
        mainPanel.add(idTextField, gbc);

        gbc.gridy = 1;  
        gbc.gridx = 0; 
        gbc.insets = new Insets(30,0,0,0); //padding
        passwordLabel = new JLabel("Masukan password Anda");
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; 
        passwordField = new JPasswordField();
        mainPanel.add(passwordField, gbc);
        
        gbc.fill = GridBagConstraints.NONE; 
        gbc.ipadx = 50;
        gbc.gridwidth = 2;
        gbc.gridy = 2;  
        gbc.gridx = 0;  
        gbc.insets = new Insets(30,20,0,0);
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(80, 30));
        mainPanel.add(loginButton, gbc);
        handleLogin();  

        gbc.gridy = 4;
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(80, 30));
        mainPanel.add(backButton, gbc);
        handleBack();  
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                idTextField.setText("");
                passwordField.setText("");
                MainFrame.getInstance().navigateTo(HomeGUI.KEY);
            }
        });
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                String password = new String(passwordField.getPassword());
                String id = idTextField.getText();
                //validasi member duplikat
                if (loginManager.getSystem(id) != null) {
                    MainFrame.getInstance().login(id, password);
                    idTextField.setText("");
                    passwordField.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(mainPanel, "ID atau password invalid", "Invalid ID or Password", JOptionPane.ERROR_MESSAGE);
                    idTextField.setText("");
                    passwordField.setText("");
                }
            }
        });
    }
}
