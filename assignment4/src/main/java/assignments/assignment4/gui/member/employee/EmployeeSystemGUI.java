package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";
    private JButton cuciButton;
    private JButton notaButton;
    private JPanel panel = new JPanel();

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }


    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        cuciButton = new JButton("It's nyuci time");
        notaButton = new JButton("Display list nota");
        return new JButton[]{
            cuciButton, notaButton
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        String notaText = "";
        //output nota sesuai ketentuan
        if (NotaManager.notaList.length == 0) {
            notaText = "Belum ada nota";
        }
        else {
            for (Nota nota : NotaManager.notaList) {
                notaText += nota.getNotaStatus() + "\n";
            }
        }
        //menampilkan dalam message dialog
        JOptionPane.showMessageDialog(panel, notaText, "List Nota", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        String notaText = "";
        JOptionPane.showMessageDialog(panel, "Stand back! " + loggedInMember.getNama() + " beginning to nyuci!", "Detail Nota", JOptionPane.INFORMATION_MESSAGE);
        //output nota yang ingin dikerjakan dan menampilkan dalam message dialog
        for (Nota nota : NotaManager.notaList) {
            notaText += nota.kerjakan() + "\n";
        }
        if (NotaManager.notaList.length == 0) {
            JOptionPane.showMessageDialog(panel, "nothing to cuci here", "Nyuci Results", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(panel, notaText, "Nyuci Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
