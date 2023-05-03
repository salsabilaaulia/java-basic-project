package assignments.assignment3.user.menu;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import static assignments.assignment1.NotaGenerator.*;
import static assignments.assignment3.nota.NotaManager.*;

public class MemberSystem extends SystemCLI {
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
        String paket = "";
        while (true) {
            System.out.println("Masukan paket laundry:");
            showPaket();
            paket = in.nextLine();

            if (toHargaPaket(paket) < 0) {
                System.out.printf("Paket %s tidak diketahui\n", paket);
                System.out.println("[ketik ? untuk mencari tahu jenis paket]");
            } else {
                break;
            }
        }
        
        System.out.println("Masukan berat cucian Anda [Kg]: ");
        String beratInput = in.nextLine();
        int berat = Integer.parseInt(beratInput);

        if (berat < 2) {
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            berat = 2;
        }

        Nota nota = new Nota(member, berat, paket, tanggal);
        nota.addService(new CuciService());

        System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?");
        System.out.println("Hanya tambah 1000 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        if (in.nextLine().equalsIgnoreCase("x")) {
        }
        else {
            nota.addService(new SetrikaService());
        }

        System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!");
        System.out.println("Cuma 2000 / 4kg, kemudian 500 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        if (in.nextLine().equalsIgnoreCase("x")) {
        }
        else {
            nota.addService(new AntarService());
        }

        addNota(nota);
        member.addNota(nota);

        System.out.println("Nota berhasil dibuat!");
    }

    protected void lihatNota() {
        for (Nota nota : loginMember.getNotaList()) {
            System.out.print(nota);
            }
        }
    }
