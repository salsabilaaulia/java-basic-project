package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;
import assignments.assignment4.MainFrame.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
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
        // TODO
        GridBagConstraints gbc = new GridBagConstraints(); 

        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.gridy = 0;  
        gbc.gridx = 0;  
        nameLabel = new JLabel("Masukan nama Anda:");
        mainPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;  
        gbc.ipadx = 200;
        gbc.insets = new Insets(30,0,0,0); //padding
        nameTextField = new JTextField();
        mainPanel.add(nameTextField, gbc);

        gbc.gridy = 1;  
        gbc.gridx = 0;  
        phoneLabel = new JLabel("Masukan nomor handphone Anda:");
        mainPanel.add(phoneLabel, gbc);

        gbc.gridx = 1; 
        phoneTextField = new JTextField();
        mainPanel.add(phoneTextField, gbc);

        gbc.gridy = 2;  
        gbc.gridx = 0;  
        passwordLabel = new JLabel("Masukan password Anda:");
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; 
        passwordField = new JPasswordField();
        mainPanel.add(passwordField, gbc);

        gbc.fill = GridBagConstraints.NONE; 
        gbc.ipadx = 50;
        gbc.gridwidth = 2;
        gbc.gridy = 3;  
        gbc.gridx = 0;  
        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(80, 30));
        mainPanel.add(registerButton, gbc);
        handleRegister();

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
                nameTextField.setText("");
                phoneTextField.setText("");
                passwordField.setText(""); 
                MainFrame.getInstance().navigateTo(HomeGUI.KEY);
            }
        });
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                String nama = nameTextField.getText();
                String noHp = phoneTextField.getText();
                String password = new String(passwordField.getPassword());
                Member member = loginManager.register(nama, noHp, password);
         
                if (MainFrame.isNumeric(noHp) == true) {
                    if (member != null) {
                        JOptionPane.showMessageDialog(mainPanel, "Berhasil membuat user dengan ID " + member.getId(), "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                        nameTextField.setText("");
                        phoneTextField.setText("");
                        passwordField.setText("");
                        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
                    }
                    else {
                        JOptionPane.showMessageDialog(mainPanel, "User dengan nama " + nama + " dan nomor hp "+ noHp +" sudah ada!", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                        nameTextField.setText("");
                        phoneTextField.setText("");
                        passwordField.setText("");
                        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(mainPanel, "Nomor handphone harus berisi angka!", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                    phoneTextField.setText("");
                }
            }
        });
    }
}
