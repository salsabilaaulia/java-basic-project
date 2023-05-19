package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";
    private JButton laundryButton;
    private JButton notaButton;
    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane;
    private JPanel panel = new JPanel();

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        laundryButton = new JButton("Saya ingin laundry");
        notaButton = new JButton("Lihat detail nota saya");
        return new JButton[]{
            laundryButton, notaButton
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
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        String notaText = "";
        if (loggedInMember.getNotaList().length == 0) {
            notaText = "Belum pernah laundry di CuciCuci, hiks :'()";
        }
        else {
            for (Nota nota : loggedInMember.getNotaList()) {
                notaText += nota + "\n";
            }
        }
        textArea.setText(notaText);
        textArea.setEditable(false);
        // wrap a scrollpane around it
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 300));

        // display them in a message dialog
        JOptionPane.showMessageDialog(panel, scrollPane, "Detail Nota", JOptionPane.INFORMATION_MESSAGE);

        notaText = "";
    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        MainFrame.getInstance().navigateTo(CreateNotaGUI.KEY);
    }

}
