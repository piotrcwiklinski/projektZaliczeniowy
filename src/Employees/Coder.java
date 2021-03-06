package Employees;

public class Coder extends Employee{

    public Boolean isOutsourcer;
    public Double dailyWage;
    public Integer projectIdIfOutsourced;
    public Integer lateChance;
    public Integer fuckUpChance;
    public Boolean frontEndSkill;
    public Boolean backEndSkill;
    public Boolean dataBaseSkill;
    public Boolean mobileSkill;
    public Boolean wordPressSkill;
    public Boolean prestaShopSkill;


    public Coder() {
        this.empName = firstNames[rand.nextInt(firstNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];
        this.isAssigned = false;
        this.isOutsourcer = false;
        this.lateChance = rand.nextInt(30);
        this.fuckUpChance = rand.nextInt(30);
        this.frontEndSkill = rand.nextBoolean();
        this.backEndSkill = rand.nextBoolean();
        this.dataBaseSkill = rand.nextBoolean();
        this.mobileSkill = rand.nextBoolean();
        this.wordPressSkill = rand.nextBoolean();
        this.prestaShopSkill = rand.nextBoolean();
        this.sickChance = rand.nextInt(10);
        double SalaryCalc = 0.0;
        if(this.frontEndSkill) SalaryCalc += 2000.0;
        if(this.backEndSkill) SalaryCalc += 3000.0;
        if(this.dataBaseSkill) SalaryCalc += 2500.0;
        if(this.mobileSkill) SalaryCalc += 3000.0;
        if(this.wordPressSkill) SalaryCalc += 2000.0;
        if(this.prestaShopSkill) SalaryCalc += 2000.0;
        SalaryCalc *= 1.0 - (this.lateChance / 100.0) - (this.fuckUpChance / 100.0);
        SalaryCalc = Math.round(SalaryCalc);
        this.salary = SalaryCalc;

    }

    public Coder(Boolean isOutsourcer) {
        this.empName = firstNames[rand.nextInt(firstNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];
        this.isAssigned = false;
        this.isOutsourcer = isOutsourcer;
        this.salary = 0.0;
        this.frontEndSkill = rand.nextBoolean();
        this.backEndSkill = rand.nextBoolean();
        this.dataBaseSkill = rand.nextBoolean();
        this.mobileSkill = rand.nextBoolean();
        this.wordPressSkill = rand.nextBoolean();
        this.prestaShopSkill = rand.nextBoolean();
        this.sickChance = rand.nextInt(10);
    }

    // Utworzenie bazy Programist??w oraz uzupe??nienie pocz??tkowej puli dost??pnych Programist??w

    public static Coder[] coders = new Coder[100];

    public static void generateInitialEmpPool(){
        coders[empID] = new Coder(true);
        coders[empID].identifier = empID;
        coders[empID].dailyWage = 750.0;
        coders[empID].lateChance = 0;
        coders[empID].fuckUpChance = 0;
        empID++;

        coders[empID] = new Coder(true);
        coders[empID].identifier = empID;
        coders[empID].dailyWage = 600.0;
        coders[empID].lateChance = 0;
        coders[empID].fuckUpChance = 10;
        empID++;

        coders[empID] = new Coder(true);
        coders[empID].identifier = empID;
        coders[empID].dailyWage = 450.0;
        coders[empID].lateChance = 20;
        coders[empID].fuckUpChance = 20;
        empID++;
    }

    // format wypisywania danych o Programistach:

    public String toString() {
        String s, z, fe, be, bd, mo, wp, ps;
        if(isOutsourcer){s = "Podwykonawca"; z = dailyWage + " z?? brutto / dzie?? pracy;";} else{s = "Sta??y Pracownik"; z = "Miesi??czna pensja " + salary + " z?? brutto;";}
        if(frontEndSkill) {fe = ">front-End;\n";} else {fe = "";}
        if(backEndSkill) {be = ">back-End;\n";} else {be = "";}
        if(dataBaseSkill) {bd = ">Bazy Danych;\n";} else {bd = "";}
        if(mobileSkill) {mo = ">Mobile;\n";} else {mo = "";}
        if(wordPressSkill) {wp = ">WordPress;\n";} else {wp = "";}
        if(prestaShopSkill) {ps = ">PrestaShop;\n";} else {ps = "";}
        return "----------------------------------------------------------------------------------\n" +
                ">>DANE PRACOWNIKA:\n" +
                "ID Pracownika: (" + identifier + ");\n" +
                "Imi?? i nazwisko: " + empName + ";\n" +
                "Rodzaj wsp????pracy: " + s + ";\n" +
                "Wynagrodzenie: " + z + "\n" +
                "Ryzyko b????d??w w kodzie: " + fuckUpChance + "%;\n" +
                "Ryzyko sp????nienia: " + lateChance + "%;\n" +
                "\n>>POSIADANE UMIEJ??TNOSCI:\n" +
                fe + be + bd + mo + wp + ps
                ;
    }

    //wypisanie wszystkich dost??pnych w danym momencie programist??w

    public static void printAvailableCoders() {
        System.out.println("\n\tDOST??PNI PROGRAMISCI:\n");
        for (Coder coder : coders) {
            while(coder != null) {
                if (!coder.isAssigned) System.out.println(coder);
                break;
            }
        }
    }


}
