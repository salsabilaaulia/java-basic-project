package assignments.assignment2;

public class Nota {
    //atribut class Nota
    private int idNota;
    private String paket;
    private Member member;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;
    
    //constructor class Nota
    public Nota(Member member, String paket, int berat, String tanggalMasuk, int sisaHariPengerjaan, int idNota) {
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan = sisaHariPengerjaan;
        this.idNota = idNota;
        this.isReady = false;
    }

    //method setter getter class Nota
    public Member getMember() {
        return member;
    }

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setSisaHariPengerjaan() {
        this.sisaHariPengerjaan--;
        if (this.sisaHariPengerjaan == 0) {
            this.isReady = true;
        }
    }

    public int getIdNota() {
        return idNota;
    }

    public boolean getIsReady() {
        return isReady;
    }

    public String paketIsReady() {
        if (this.isReady == true) {
            return "Sudah dapat diambil!";
        }
        return "Belum bisa diambil :(";
    }
}