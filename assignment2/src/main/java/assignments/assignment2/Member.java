package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    String nama;
    String nomorHP;
    String id;

    public Member(String nama, String nomorHP, String id) {
        this.nama = nama;
        this.nomorHP = nomorHP;
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public String getId() {
        return id;
    }
}
