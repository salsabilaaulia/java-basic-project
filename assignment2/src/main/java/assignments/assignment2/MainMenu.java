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

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
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

        if (adaMember(id) == true) {
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n", nama, nomorHP);
        }
        else {
            Member member = new Member(nama, nomorHP, id);
            memberList.add(member);
            System.out.printf("Berhasil membuat member dengan ID %s!\n", id);
        }
    }

    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
    }

    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
        if (notaList.size() == 0) {
            System.out.println("Terdaftar 0 nota dalam sistem.");
        }
        else {
            
        }
    }

    private static void handleListUser() {
        if (memberList.size() == 0) {
            System.out.println("Terdaftar 0 member dalam sistem.");
        }
        else {
            System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
            for (Member member : memberList) {
                System.out.printf("- %s : %s\n", member.getId(), member.getId());
            }
        }
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    public static boolean adaMember(String id){
        if (memberList != null) {
            for (Member member: memberList) {
                if (member.getId().equals(id)) {
                    return true;
                }      
            }
        }
        return false;
    }
}
