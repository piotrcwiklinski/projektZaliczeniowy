package Employees;

public class SalesPerson extends Employee{
    public Integer workDayCounter;
    public Double bonus;


    // Utworzenie bazy Sprzedawców

    public static SalesPerson[] salesPpl = new SalesPerson[100];

    public SalesPerson() {
        this.empName = firstNames[rand.nextInt(firstNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];
        this.isAssigned = false;
        this.salary = 2500.0;
        double bonusCalc = Math.round(rand.nextDouble(2500.0));
        this.bonus = bonusCalc;
        this.sickChance = rand.nextInt(10);
        this.workDayCounter = 0;
    }

    // format wypisywania danych o Sprzedawcach:

    public String toString() {

        return "----------------------------------------------------------------------------------\n" +
                ">>DANE PRACOWNIKA:\n" +
                "ID Pracownika: (" + identifier + ");\n" +
                "Imię i nazwisko: " + empName + ";\n" +
                "Wynagrodzenie: " +  salary + "zł brutto;\n" +
                "Bonus od pozyskanego Projektu: " + bonus + " zł brutto;"
                ;
    }

    //wypisanie wszystkich dostępnych w danym momencie sprzedawców

    public static void printAvailableSalesPpl() {
        System.out.println("\n\tDOSTĘPNI SPRZEDAWCY:\n");
        for (SalesPerson salesPerson : salesPpl) {
            while(salesPerson != null) {
                if (!salesPerson.isAssigned) System.out.println(salesPerson);
                break;
            }
        }
    }
}
