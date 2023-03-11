package assignments.assignment1;

//import library
import java.util.Scanner;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;  

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);
    private static final SimpleDateFormat formatTanggal = new SimpleDateFormat("dd/MM/yyyy");
    private static final Calendar Kalender = Calendar.getInstance();
    
    //method main untuk menjalankan program utama
    public static void main(String[] args) {
        //Implementasi menu utama
        while (true) {
            //menu utama program
            printMenu();

            //input pilihan user
            System.out.print("Pilihan : ");
            String pilihan = input.nextLine();
            System.out.println("================================");
            
            //deklarasi variabel
            String nama, nomorHP, id, tanggalTerima, paket;
            int berat;

            //pilihan untuk mengeprint id
            if (pilihan.equals("1")) {
                nama = inputNama();
                nomorHP = inputNomorHP();
                id = generateId(nama, nomorHP);  
                System.out.println("ID Anda : "+ id);            
            }

            //pilihan untuk mengeprint nota laundry
            else if (pilihan.equals("2")) {
                //pemanggilan method input dan menyimpan return value ke variabel
                nama = inputNama();
                nomorHP = inputNomorHP();
                id = generateId(nama, nomorHP);
                tanggalTerima = inputTanggalTerima();
                paket = inputPaket();
                berat = Integer.parseInt(inputBerat());

                //mengeprint nota laundry
                System.out.println("Nota Laundry");
                System.out.println(generateNota(id, paket, berat, tanggalTerima));
            }

            //pilihan untuk exit program
            else if (pilihan.equals("0")) {
                System.out.println("Terima kasih telah menggunakan NotaGenerator!");
                break;
            }

            //validasi input pilihan
            else {
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
    }

    //method untuk menampilkan menu di NotaGenerator
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    //method untuk menampilkan paket
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    //method untuk input user
    public static String inputNama() {
        System.out.println("Masukkan nama Anda:");
        String nama = input.nextLine();
        return nama;
    }
    public static String inputNomorHP() {
        System.out.println("Masukkan nomor handphone Anda:");
        String nomorHP;
        while (true) {
            nomorHP = input.nextLine();
            //validasi input nomor HP dan menghindari floating dan minus
            if (nomorHP.contains(".") || nomorHP.contains("-") || nomorHP.contains("+")) {
                System.out.println("Nomor hp hanya menerima digit");
            }
            else {
                try {
                    Double.parseDouble(nomorHP);
                    break;
                }               

                catch (Exception e) {
                    System.out.println("Nomor hp hanya menerima digit");
                }
            }
        }
        return nomorHP;
    }

    public static String inputTanggalTerima() {
        System.out.println("Masukkan tanggal terima:");
        String tanggalTerima = input.nextLine();

        return tanggalTerima;
    }

    public static String inputPaket() {
        String paket;
        while (true) {
            System.out.println("Masukkan paket laundry:");
            paket = input.nextLine();
            if (paket.equals("?")) {
                showPaket();
            }
            else if (paket.equals("express") || paket.equals("fast") || paket.equals("reguler")) {
                return paket;
            }           
            else {
                System.out.printf("Paket %s tidak diketahui\n", paket);
                System.out.println("[ketik ? untuk mencari tahu jenis paket]");
            }
        }
    }

    public static String inputBerat() {
        String berat;
        while (true) {
            System.out.println("Masukkan berat cucian Anda [Kg]:");
            berat = input.nextLine();
            //validasi input berat
            try {
                Integer.parseInt(berat);
                if (Integer.parseInt(berat) <= 0) {
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                }
                else {
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
            }
        }
        return berat;
    }

    //method untuk menentukan harga paket
    public static int hargaPaket(String paket) {
        int harga = 0;
        if (paket.equals("express")) {
            harga = 12000;
            return harga;
        }
        else if (paket.equals("fast")) {
            harga = 10000;
            return harga;
        }
        else if (paket.equals("reguler")) {
            harga = 7000;
            return harga;
        }         
        return harga;             
    }

    //method untuk menghitung tanggal sesuai jenis paket
    public static String tanggalSelesai(String tanggalTerima, String paket) {
        int hari= 0;
        if (paket.equals("express")) {
            hari = 1;
        }
        else if (paket.equals("fast")) {
            hari = 2;
        }
        else if (paket.equals("reguler")) {
            hari = 3;
        }    

        //menghitung pertambahan hari
        try {
            Kalender.setTime(formatTanggal.parse(tanggalTerima)); //mengset waktu sesuai input tanggalTerima
            Kalender.add(Calendar.DAY_OF_MONTH, hari); //menambahkan waktu dengan hari
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        String tanggalSelesai = formatTanggal.format(Kalender.getTime());
        return tanggalSelesai;
    }
    
    //method untuk membuat ID dari nama dan nomor handphone
    public static String generateId(String nama, String nomorHP) {
        //deklarasi variabel dan nilai awal
        int value = 0;
        int totalValue = 0;
        String checksum, id;

        //pemisahan nama depan dan belakang
        String[] arrayNama = nama.split(" ");

        //membuat string dengan format [NAMADEPAN]-[NOMORHP]
        String namaNomorID = arrayNama[0].toUpperCase() + "-" + nomorHP;

        //hasil string dibuat menjadi array char
        char[] ch = namaNomorID.toCharArray();
        
        //iterasi array char dan checksum
        for (int i = 0; i < ch.length; i++) {
            //nilai untuk tipe integer
            try {
                value = Integer.parseInt(Character.toString(ch[i]));
            }
            catch (Exception e) {
                //nilai untuk tipe huruf
                if (Character.isAlphabetic(ch[i])) {
                    value =  (int) ch[i] - 64; //nilai ASCII 'A' dimulai dari 65
                }        
                else {
                //nilai untuk tipe simbol
                    value = 7;
                }
            }
            totalValue += value;
        }
        String totalValueStr = Integer.toString(totalValue);

        //pemotongan checksum jika lebih dari 2
        if (totalValueStr.length() >= 2) {
            checksum = totalValueStr.substring(totalValueStr.length() - 2);
        }
        else {
            checksum = "0" + totalValueStr;
        }

        id = namaNomorID + "-" + checksum;
        return id;
    }
    public static String generateNota(String id, String paket, int berat, String tanggalTerima, int idNota) {
        int hargaPaketPerKg = hargaPaket(paket);
        String tanggalSelesai = tanggalSelesai(tanggalTerima, paket);

        //input berat cucian minimal 2 kg
        if (berat < 2) {
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            berat = 2;
        }

        System.out.println("Berhasil menambahkan nota!");
        System.out.printf("[ID Nota = %d]\n", idNota);

        //mereturn string nota
        return "ID    : " + id + "\n"+ 
            "Paket : " + paket + "\n"+ 
            "Harga :" + "\n"+  
            berat + " kg x " + hargaPaketPerKg +" = " + (berat*hargaPaketPerKg) + "\n"+ 
            "Tanggal Terima  : " + tanggalTerima + "\n" + 
            "Tanggal Selesai : " + tanggalSelesai + "\n" + 
            "Status      	: Belum bisa diambil :(";
    }
}
