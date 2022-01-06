package Employees;

import java.util.Random;

public class Employee {

    public static Integer empID = 0;
    public Integer identifier;
    public String empName;
    public Double salary;
    public Boolean isAssigned;
    public String employerName;
    public Integer sickChance;

    public static Random rand = new Random();

    public String[] firstNames = {"Piotr", "Paweł", "Krzysztof", "Robert", "Maciej", "Wojciech", "Gerwazy", "Albert", "Barnaba", "Gustaw", "Jacek", "Kazimierz", "Grzegorz", "Olaf", "Olgierd", "Tomasz", "Kacper"};
    public String[] lastNames = {"Iksiński", "Kowalski", "Krzyżanowski", "Kowal", "Augustyniak", "Gąska", "Szłapka", "Ryś", "Kawalec", "Karmazyniak", "Biernat", "Wokulski", "Olejniczak", "Bieniuk", "Zakrzewski", "Walaszek", "Gajos", "Rybnicki", "Truskawa"};



    //generator nowych losowych Pracowników

    public static void generateRandomEmployee() {
        if (rand.nextInt(100) < 60) {
            Coder.coders[empID] = new Coder();
            Coder.coders[empID].identifier = empID;
            empID++;
        } else if (rand.nextInt(100) < 45) {
            Tester.testers[empID] = new Tester();
            Tester.testers[empID].identifier = empID;
            empID++;
        } else {
            SalesPerson.salesPpl[empID] = new SalesPerson();
            SalesPerson.salesPpl[empID].identifier = empID;
            empID++;
        }
    }


}
