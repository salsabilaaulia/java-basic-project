package assignments.assignment3.nota;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import static assignments.assignment1.NotaGenerator.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Nota {
    private Member member;
    private String paket;
    private LaundryService[] services = new LaundryService[0];
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone;
    static public int totalNota = 0;

    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggal;
        this.sisaHariPengerjaan = toHariPaket(paket);
        this.isDone = false;
        this.id = totalNota;
        this.baseHarga = toHargaPaket(paket);
        addService(new CuciService());
        totalNota++;
    }

    public void addService(LaundryService service){
        LaundryService[] temp = new LaundryService[services.length + 1];
        for (int i = 0; i < services.length; i++) {
            temp[i] = services[i];
        }
        temp[temp.length - 1] = service;
        services = temp;
    }

    public String kerjakan(){
        for (int i = 0; i < services.length; i++) {
            if (services[i].isDone() == true) {}
            else {
                return String.format("Nota %d : %s", id, services[i].doWork());
            }
        }
        return String.format("Nota %d : Sudah selesai.", id);
    }

    public void toNextDay() {
        sisaHariPengerjaan--;
    }

    public long calculateHarga(){
        long harga = baseHarga * berat;
        for (LaundryService service : services) {
            harga += service.getHarga(berat);
        }
        if (sisaHariPengerjaan < 0 && isDone == false) {
            harga -= (Math.abs(sisaHariPengerjaan)*2000);
            if (harga < 0) {
                harga = 0;
            }
        }
        return harga;
    }

    public String getNotaStatus(){
        isDone = isDone();
        if (isDone == true) {
            return String.format("Nota %d : Sudah selesai.", id);
        }
        else {
            return String.format("Nota %d : Belum selesai.", id);
        }
    }

    @Override
    public String toString(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(tanggalMasuk.substring(6));
        int month = Integer.parseInt(tanggalMasuk.substring(3, 5)) - 1;
        int date = Integer.parseInt(tanggalMasuk.substring(0, 2));
        cal.set(year, month, date);
        
        String output = "";
        output += String.format("[ID Nota = %d]\n", id);
        output += String.format("ID    : %s\n", member.getId());
        output += String.format("Paket : %s\n", paket);
        output += String.format("Harga :\n");
        output += String.format("%d kg x %d = %d\n", berat, baseHarga, berat * baseHarga);
        output += String.format("tanggal terima  : %s\n", tanggalMasuk);
        cal.add(Calendar.DATE, toHariPaket(paket));
        output += String.format("tanggal selesai : %s\n", formatter.format(cal.getTime()));
        output += String.format("--- SERVICE LIST ---\n");
        for (int i = 0; i < services.length; i++) {
            output += String.format("-%s @ Rp.%d\n", services[i].getServiceName(), services[i].getHarga(berat));
        }

        output += String.format("Harga Akhir: %d\n", calculateHarga());

        if (sisaHariPengerjaan < 0 && isDone == false ) {
            output += String.format(" Ada kompensasi keterlambatan %d * 2000 hari", Math.abs(sisaHariPengerjaan));        
            }
        
        return output;
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        for (LaundryService service : services) {
            if (service.isDone() == false) {
                isDone = false;
                break;
            }
            else {
                isDone = true;
            }
        }
        return isDone;
    }

    public LaundryService[] getServices(){
        return services;
    }
    
    public int getId(){
        return id;
    }

    public Member getMember() {
        return member;
    }
}
