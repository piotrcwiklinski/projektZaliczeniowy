package Employees;

public class Tester extends Employee{


    // Utworzenie bazy Testerów

    public static Tester[] testers = new Tester[100];

    public Tester() {
        this.empName = firstNames[rand.nextInt(firstNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];
        this.isAssigned = false;
        this.salary = 3500.0;
    }

    // format wypisywania danych o Testerach:

    public String toString() {

        return "----------------------------------------------------------------------------------\n" +
                ">>DANE PRACOWNIKA:\n" +
                "ID Pracownika: (" + identifier + ");\n" +
                "Imię i nazwisko: " + empName + ";\n" +
                "Wynagrodzenie: " +  salary + "zł brutto;\n"
                ;
    }

    //wypisanie wszystkich dostępnych w danym momencie testerów

    public static void printAvailableTesters() {
        System.out.println("\n\tDOSTĘPNI TESTERZY:\n");
        for (Tester tester : testers) {
            while(tester != null) {
                if (!tester.isAssigned) System.out.println(tester);
                break;
            }
        }
    }
}
