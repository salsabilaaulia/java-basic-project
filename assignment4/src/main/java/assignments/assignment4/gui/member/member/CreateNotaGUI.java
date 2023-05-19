package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JPanel mainPanel;
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        super(new BorderLayout());
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel
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
        gbc.gridx = 0;  
        paketLabel = new JLabel("Paket Laundry:");
        mainPanel.add(paketLabel, gbc);

        gbc.gridx = 1;
        paketComboBox = new JComboBox<>(new String[]{"Express", "Fast", "Reguler"});
        mainPanel.add(paketComboBox, gbc);

        gbc.insets = new Insets(0,10,0,0); //padding
        gbc.gridx = 2;
        showPaketButton = new JButton("show paket");
        mainPanel.add(showPaketButton, gbc);
        showPaket();

        gbc.insets = new Insets(10,0,0,0); //padding
        gbc.gridy = 1; 
        gbc.gridx = 0; 
        beratLabel = new JLabel("Berat Cucian (Kg)");
        mainPanel.add(beratLabel, gbc);
        
        gbc.gridx = 1; 
        beratTextField = new JTextField();
        mainPanel.add(beratTextField, gbc);

        gbc.gridy = 2; 
        gbc.gridx = 0; 
        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000 / Kg)");
        mainPanel.add(setrikaCheckBox, gbc);

        gbc.gridy = 3; 
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000 / 4 Kg pertama, kemudian 500 / Kg)");
        mainPanel.add(antarCheckBox, gbc);

        gbc.gridwidth = 3;
        gbc.gridy = 4; 
        createNotaButton = new JButton("Buat Nota");
        mainPanel.add(createNotaButton, gbc);
        createNota();

        gbc.gridy = 5; 
        backButton = new JButton("Kembali");
        mainPanel.add(backButton, gbc);
        handleBack();
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        showPaketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(mainPanel, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        createNotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                String tanggal = fmt.format(cal.getTime());
                String paket = (String) paketComboBox.getSelectedItem();
                String strBerat = beratTextField.getText();
                //validasi input
                if (strBerat.equals("")) {
                    JOptionPane.showMessageDialog(mainPanel, "Input tidak boleh kosong!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
                else if(MainFrame.isNumeric(strBerat) == false || strBerat.equals("0")) {
                    JOptionPane.showMessageDialog(mainPanel, "Berat cucian harus berisi angka bulat positif", "Info", JOptionPane.INFORMATION_MESSAGE);
                    beratTextField.setText("");
                }
                else {
                    int berat = Integer.parseInt(strBerat);
                    if (berat < 2) {
                        berat = 2;
                        JOptionPane.showMessageDialog(mainPanel, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                    Nota nota = new Nota(memberSystemGUI.getLoggedInMember(), berat, paket, tanggal);
                    //menambahkan servis dalam nota
                    if (setrikaCheckBox.isSelected()) {
                        nota.addService(new SetrikaService());
                    }

                    if (antarCheckBox.isSelected()) {
                        nota.addService(new AntarService());
                    }
                    NotaManager.addNota(nota);
                    memberSystemGUI.getLoggedInMember().addNota(nota);

                    JOptionPane.showMessageDialog(mainPanel, "Nota berhasil dibuat!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    //reset isi komponen
                    beratTextField.setText("");
                    paketComboBox.setSelectedIndex(0);
                    setrikaCheckBox.setSelected(false);
                    antarCheckBox.setSelected(false);
                }
            }
        });
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                beratTextField.setText("");
                paketComboBox.setSelectedIndex(0);
                setrikaCheckBox.setSelected(false);
                antarCheckBox.setSelected(false);
                MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
            }
        });
    }
}
