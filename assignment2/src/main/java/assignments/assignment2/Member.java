package assignments.assignment2;

public class Member {
    private String nama;
    private String nomorHP;
    private String id;
    private int bonusCounter;

    public Member(String nama, String nomorHP, String id) {
        this.nama = nama;
        this.nomorHP = nomorHP;
        this.id = id;
        this.bonusCounter = 0;
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

    public int getBonusCounter() {
        return bonusCounter;
    }

    public void setBonusCounter() {
        this.bonusCounter++;
    }
}
