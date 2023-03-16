package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.ArrayList;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Member> memberList = new ArrayList<Member>();
    private static ArrayList<Nota> notaList = new ArrayList<Nota>();
    private static int countIdNota = 0;
    private static String tanggal = fmt.format(cal.getTime());

    public static void main(String[] args) {
        boolean isRunning = true;
        //implementasi menu utama
        while (isRunning) {
            printMenu();

            //input pilihan user
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleGenerateUser() {
        // menghandle generate user
        String nama = inputNama();
        String nomorHP = inputNomorHP();
        String id = generateId(nama, nomorHP);

        //mencegah duplikat id
        if (adaMember(id) != null) {
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n", nama, nomorHP);
        }

        //membuat objek member dan menambahkannya ke list member
        else {
            Member member = new Member(nama, nomorHP, id);
            memberList.add(member);
            System.out.printf("Berhasil membuat member dengan ID %s!\n", id);
        }
    }

    private static void handleGenerateNota() {
        // menghandle ambil cucian
        System.out.println("Masukan ID member: ");
        String id = input.nextLine();
        
        //menggenerate nota user
        if (adaMember(id) != null) {
            String paket = inputPaket();
            int berat = Integer.parseInt(inputBerat());
            String tanggalMasuk = fmt.format(cal.getTime());
            int sisaHariPengerjaan = hariPengerjaan(paket);
            int idNota = countIdNota;

            //membuat objek nota dan memasukkannya ke list nota
            Member member = adaMember(id);
            member.setBonusCounter();
            int bonusCounter = member.getBonusCounter();

            Nota nota = new Nota(member, paket, berat, tanggalMasuk, sisaHariPengerjaan, idNota);

            //menggenerate output nota user
            notaList.add(nota);
            countIdNota++;
            System.out.println(generateNota(id, paket, berat, tanggalMasuk, idNota, bonusCounter));
        }
        else {
            System.out.printf("Member dengan ID %s tidak ditemukan!\n", id);
        }
    }

    private static void handleListNota() {
        //menghandle list semua nota pada sistem
        if (notaList.size() == 0) {
            System.out.println("Terdaftar 0 nota dalam sistem.");
        }
        else {
            System.out.printf("Terdaftar %d nota dalam sistem.\n", notaList.size());
            for (Nota nota : notaList) {
                System.out.printf("- [%d] Status      	: %s\n", nota.getIdNota(), nota.paketIsReady());
            }
        }
    }

    private static void handleListUser() {
        //menghandle list semua member pada sistem
        if (memberList.size() == 0) {
            System.out.println("Terdaftar 0 member dalam sistem.");
        }
        else {
            System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
            for (Member member : memberList) {
                System.out.printf("- %s : %s\n", member.getId(), member.getNama());
            }
        }
    }

    private static void handleAmbilCucian() {
        //menghandle ambil cucian
        String InputIdNotaString = inputIdNotaCucian();
        int inputIdNota = Integer.parseInt(InputIdNotaString);

        //pengecekan id nota dan validasi
        for (int i = 0; i < notaList.size(); i++) {
            if (notaList.get(i).getIdNota() == inputIdNota) {
                if (notaList.get(i).getIsReady() == true) {
                    System.out.printf("Nota dengan ID %s berhasil diambil!\n", InputIdNotaString);
                    notaList.remove(i);
                    break;
                }
                else if (notaList.get(i).getIsReady() == false) {
                    System.out.printf("Nota dengan ID %s gagal diambil!\n", InputIdNotaString);
                    break;
                }
            }
            else {
                System.out.printf("Nota dengan ID %s tidak ditemukan!\n", InputIdNotaString);
                break;
            }
        }
    }

    private static void handleNextDay() {
        //menambahkan 1 hari
        try {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //output sistem
        System.out.println("Dek Depe tidur hari ini... zzz...");
        if (notaList != null) {

            //output jika ada nota yang dapat diambil
            for (Nota nota : notaList) {
                nota.setSisaHariPengerjaan();
                if (nota.getIsReady() == true) {
                    System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n", nota.getIdNota());
                }
            }
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

    private static void printMenu() {
        //mengeprint main interface program
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", tanggal);
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    public static Member adaMember(String id){
        //method untuk mengecek member dari list member
        if (memberList != null) {
            for (Member member: memberList) {
                if (member.getId().equals(id)) {
                    return member;
                }      
            }
        }
        return null;
    }


    public static String inputIdNotaCucian() {
        //validasi input id nota user ambil cucian
        System.out.println("Masukan id nota yang akan diambil:");
        String inputIdNota;
        while (true) {
            inputIdNota = input.nextLine();
            //validasi input id nota dan menghindari floating dan minus
            if (inputIdNota.contains(".") || inputIdNota.contains("-") || inputIdNota.contains("+")) {
                System.out.println("ID nota berbentuk angka!");
            }
            else {
                try {
                    Double.parseDouble(inputIdNota);
                    break;
                }               

                catch (Exception e) {
                    System.out.println("ID nota berbentuk angka!");
                }
            }
        }
        return inputIdNota;
    }
}