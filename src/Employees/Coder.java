package Employees;

public class Coder extends Employee{

    public Boolean isOutsourcer;
    public Integer projectPriceShareIfOutsourced;
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
        this.projectPriceShareIfOutsourced = 0;
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
        if(this.frontEndSkill) SalaryCalc += 2500.0;
        if(this.backEndSkill) SalaryCalc += 2500.0;
        if(this.dataBaseSkill) SalaryCalc += 2500.0;
        if(this.mobileSkill) SalaryCalc += 2500.0;
        if(this.wordPressSkill) SalaryCalc += 2500.0;
        if(this.prestaShopSkill) SalaryCalc += 2500.0;
        SalaryCalc *= (1.0 - (this.lateChance / 100.0) - (this.fuckUpChance / 100.0));
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

    // Utworzenie bazy Programistów oraz uzupełnienie początkowej puli dostępnych Programistów

    public static Coder[] coders = new Coder[100];

    public static void generateInitialEmpPool(){
        coders[empID] = new Coder(true);
        coders[empID].identifier = empID;
        coders[empID].projectPriceShareIfOutsourced = 75;
        coders[empID].lateChance = 0;
        coders[empID].fuckUpChance = 0;
        empID++;

        coders[empID] = new Coder(true);
        coders[empID].identifier = empID;
        coders[empID].projectPriceShareIfOutsourced = 60;
        coders[empID].lateChance = 0;
        coders[empID].fuckUpChance = 10;
        empID++;

        coders[empID] = new Coder(true);
        coders[empID].identifier = empID;
        coders[empID].projectPriceShareIfOutsourced = 45;
        coders[empID].lateChance = 20;
        coders[empID].fuckUpChance = 20;
        empID++;
    }

    // format wypisywania danych o Programistach:

    public String toString() {
        String s, z, fe, be, bd, mo, wp, ps;
        if(isOutsourcer){s = "Outsourcing"; z = projectPriceShareIfOutsourced + "% wartości realizowanego zlecenia;";} else{s = "Stały Pracownik"; z = "Miesięczna pensja " + salary + " zł brutto;";}
        if(frontEndSkill) {fe = ">front-End;\n";} else {fe = "";}
        if(backEndSkill) {be = ">back-End;\n";} else {be = "";}
        if(dataBaseSkill) {bd = ">Bazy Danych;\n";} else {bd = "";}
        if(mobileSkill) {mo = ">Mobile;\n";} else {mo = "";}
        if(wordPressSkill) {wp = ">WordPress;\n";} else {wp = "";}
        if(prestaShopSkill) {ps = ">PrestaShop;\n";} else {ps = "";}
        return "----------------------------------------------------------------------------------\n" +
                ">>DANE PRACOWNIKA:\n" +
                "ID Pracownika: (" + identifier + ");\n" +
                "Imię i nazwisko: " + empName + ";\n" +
                "Rodzaj współpracy: " + s + ";\n" +
                "Wynagrodzenie: " + z + "\n" +
                "Ryzyko błędów w kodzie: " + fuckUpChance + "%;\n" +
                "Ryzyko spóźnienia: " + lateChance + "%;\n" +
                "\n>>POSIADANE UMIEJĘTNOSCI:\n" +
                fe + be + bd + mo + wp + ps
                ;
    }

    //wypisanie wszystkich dostępnych w danym momencie programistów

    public static void printAvailableCoders() {
        System.out.println("\n\tDOSTĘPNI PROGRAMISCI:\n");
        for (Coder coder : coders) {
            while(coder != null) {
                if (!coder.isAssigned) System.out.println(coder);
                break;
            }
        }
    }


}
