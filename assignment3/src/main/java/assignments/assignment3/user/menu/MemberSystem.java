package assignments.assignment3.user.menu;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import static assignments.assignment1.NotaGenerator.*;
import static assignments.assignment3.nota.NotaManager.*;

import java.util.Scanner;

public class MemberSystem extends SystemCLI {
    static Scanner in = new Scanner(System.in);
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice) {
            case 1 -> menuLaundry();
            case 2 -> lihatNota();
            case 3 -> logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        Member[] temp = new Member[memberList.length + 1];
        for (int i = 0; i < memberList.length; i++) {
            temp[i] = memberList[i];
        }
        temp[temp.length - 1] = member;
        memberList = temp;
    }

    protected void menuLaundry() {
        Member member = loginMember;
        String tanggal = fmt.format(cal.getTime());
        String paket = getPaket();
        
        int berat = getBerat();

        Nota nota = new Nota(member, berat, paket, tanggal);
        LaundryService cuci = new CuciService();
        nota.addService(cuci);
        getSetrika(nota);
        getAntar(nota);

        addNota(nota);
        member.addNota(nota);

        System.out.println("Nota berhasil dibuat!");

    }

    public static void getSetrika(Nota nota) {
        System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?");
        System.out.println("Hanya tambah 1000 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        String choice = in.nextLine();
        if (choice.equalsIgnoreCase("x")) {
        }
        else {
            LaundryService setrika = new SetrikaService();
            nota.addService(setrika);
        }
    }

    public static void getAntar(Nota nota) {
        System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!");
        System.out.println("Cuma 2000 / 4kg, kemudian 500 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        String choice = in.nextLine();
        if (choice.equalsIgnoreCase("x")) {
        }
        else {
            LaundryService antar = new AntarService();
            nota.addService(antar);
        }
    }

    protected void lihatNota() {
        for (Nota nota : loginMember.getNotaList()) {
            System.out.print(nota);
            }
        }
    }
