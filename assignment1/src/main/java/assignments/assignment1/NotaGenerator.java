package assignments.assignment1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = TPScanner.getScanner();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();

            System.out.println("================================");
            if (command.equals("1")) {
                System.out.printf("ID Anda : %s\n", handleMenuGenerateID());
                continue;
            }

            if (command.equals("2")) {
                handleMenuGenerateNota();
                continue;
            }

            if (command.equals("0")) {
                break;
            }

            System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
        }

        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleMenuGenerateNota() {
        String id = handleMenuGenerateID();
        System.out.println("Masukan tanggal terima:");
        String tanggalTerima = input.nextLine();

        String paket = getPaket();
        int berat = getBerat();

        System.out.println("Nota Laundry");
        System.out.println(generateNota(id, paket, berat, tanggalTerima, false));
    }

    public static int getBerat() {
        System.out.println("Masukan berat cucian Anda [Kg]: ");
        String beratInput = input.nextLine();
        while (!isNumeric(beratInput) || Integer.parseInt(beratInput) < 1) {
            System.out.println("Harap masukan berat cucian Anda dalam bentuk bilangan positif.");
            beratInput = input.nextLine();
        }
        int berat = Integer.parseInt(beratInput);

        if (berat < 2) {
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            berat = 2;
        }

        return berat;
    }

    public static String getPaket() {
        String paket = "";
        while (true) {
            System.out.println("Masukan paket laundry:");
            paket = input.nextLine();
            showPaket();

            if (paket.equals("?")) {
                showPaket();
                continue;
            }

            if (toHargaPaket(paket) < 0) {
                System.out.printf("Paket %s tidak diketahui\n", paket);
                System.out.println("[ketik ? untuk mencari tahu jenis paket]");
            } else {
                break;
            }
        }
        return paket;
    }

    private static String handleMenuGenerateID() {
        System.out.println("Masukan nama Anda:");
        String nama = input.nextLine();

        System.out.println("Masukan nomor handphone Anda:");
        String nomorHP = input.nextLine();
        while (!isNumeric(nomorHP)) {
            System.out.println("Nomor hp hanya menerima digit");
            nomorHP = input.nextLine();
        }

        return generateId(nama, nomorHP);
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP) {
        String id = "";
        id += (nama.split(" ")[0] + "-").toUpperCase();
        id += nomorHP;

        int checksum = 0;
        for (char c : id.toCharArray()) {
            if (Character.isDigit(c))
                checksum += c - '0';
            else if (Character.isLetter(c))
                checksum += (c - 'A') + 1;
            else
                checksum += 7;
        }
        id += String.format("-%02d", checksum % 100);
        return id;
    }

    /**
     *
     * Method untuk membuat Nota.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing.
     *
     * @return string nota dengan format di bawah:
     *         <p>ID    : [id]
     *         <p>Paket : [paket]
     *         <p>Harga :
     *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
     *         <p>Tanggal Terima  : [tanggalTerima]
     *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */
    public static String generateNota(String id, String paket, int berat, String tanggalTerima, boolean discount) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(tanggalTerima.substring(6));
        int month = Integer.parseInt(tanggalTerima.substring(3, 5)) - 1;
        int date = Integer.parseInt(tanggalTerima.substring(0, 2));
        cal.set(year, month, date);

        String nota = "";
        nota += "ID    : " + id + "\n";
        nota += "Paket : " + paket + "\n";
        nota += "Harga :\n";
        nota += String.format("%d kg x %d = %d", berat, toHargaPaket(paket), (berat * toHargaPaket(paket)));
        if (discount) {
            nota += String.format(" = %d (Discount member 50%%!!!)", (berat * toHargaPaket(paket)/2));
        }
        nota += "\nTanggal Terima  : " + tanggalTerima + "\n";
        cal.add(Calendar.DATE, toHariPaket(paket));
        nota += "Tanggal Selesai : " + formatter.format(cal.getTime());

        return nota;
    }

    public static int toHariPaket(String paket) {
        paket = paket.toLowerCase();
        if (paket.equals("express"))
            return 1;
        if (paket.equals("fast"))
            return 2;
        if (paket.equals("reguler"))
            return 3;
        return -1;
    }

    public static long toHargaPaket(String paket) {
        paket = paket.toLowerCase();
        if (paket.equals("express"))
            return 12000;
        if (paket.equals("fast"))
            return 10000;
        if (paket.equals("reguler"))
            return 7000;
        return -1;
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    public static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
}
