package Employees;

import java.util.Random;

public class Employee {

    public static Integer empID;
    public Integer identifier;
    public String empName;
    public static String empClass;
    public Double salary;
    public Boolean isAssigned;
    public String employerName;

    public static Random rand = new Random();

    public String[] firstNames = {"Piotr", "Paweł", "Krzysztof", "Robert", "Maciej", "Wojciech", "Gerwazy", "Albert", "Barnaba", "Gustaw", "Jacek", "Kazimierz", "Grzegorz", "Olaf", "Olgierd", "Tomasz", "Kacper"};
    public String[] lastNames = {"Iksiński", "Kowalski", "Krzyżanowski", "Kowal", "Augustyniak", "Gąska", "Szłapka", "Ryś", "Kawalec", "Karmazyniak", "Biernat", "Wokulski", "Olejniczak", "Bieniuk", "Zakrzewski", "Walaszek", "Gajos", "Rybnicki", "Truskawa"};
    public static String[] empClasses = {"Programista", "Tester", "Sprzedawca"};


    //generator nowych losowych Pracowników

    public static void generateRandomEmployee() {
        empClass = empClasses[rand.nextInt(empClasses.length)];
        if (empClass.equals("Programista")) {
            Coder.coders[empID] = new Coder();
            Coder.coders[empID].identifier = empID;
            empID++;
        } else if (empClass.equals("Tester")) {
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
