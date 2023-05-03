package assignments.assignment3.user.menu;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.Member;
import java.util.ArrayList;

import static assignments.assignment3.nota.NotaManager.notaList;

public class EmployeeSystem extends SystemCLI {

    /**
     * Membuat object baru EmployeeSystem dan mendaftarkan Employee pada CuciCuci
     */
    public EmployeeSystem() {
        Member employee1 = new Employee("Dek Depe", "akuDDP");
        Member employee2 = new Employee("Depram", "musiktualembut");
        Member employee3 = new Employee("Lita Duo", "gitCommitPush");
        Member employee4 = new Employee("Ivan Hoshimachi", "SuamiSahSuisei");
        memberList.add(employee1);
        memberList.add(employee2);
        memberList.add(employee3);
        memberList.add(employee4);
    }
    

    /**
     * Memproses pilihan dari employee yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice) {
            case 1 -> menuKerja();
            case 2 -> lihatListNota();
            case 3 -> logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Employee.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. It's nyuci time");
        System.out.println("2. Display List Nota");
        System.out.println("3. Logout");
    }
    protected void menuKerja() {
        System.out.println("Stand back! Depram beginning to nyuci!");
        for (Nota nota : notaList) {
            System.out.printf("Nota %d : %s\n", nota.getId(), nota.kerjakan());
        }
    }

    protected void lihatListNota() {
        for (Nota nota : notaList) {
            System.out.printf("Nota %d : %s\n", nota.getId(), nota.getNotaStatus());
        }
    }
}