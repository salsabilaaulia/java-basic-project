package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    private boolean isDone = false;
    @Override
    public String doWork() {
        isDone = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        long harga = berat * 1000;
        return harga;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
