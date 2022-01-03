import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.time.LocalDate;

public class Project {

    public Integer projectID;
    public String projectName;
    public Integer frontEndDays;
    public Integer backEndDays;
    public Integer databaseDays;
    public Integer mobileDays;
    public Integer wordpressDays;
    public Integer prestaShopDays;
    public String client;
    public Integer deadlineDays;
    public LocalDate deadlineDate;
    public Integer deadlinePenalty;
    public Double price;
    public Integer paymentDays;
    public LocalDate payDay;
    public Double paySum;
    public Integer complexityLvl;
    public Boolean taken;
    public String owner;
    public String status;
    public Boolean fuckedUpCode;
    public Integer fuckUpsCount;
    public Boolean isOutsourced;
    public Integer outsourcerId;
    public Boolean touchedByBoss;
    public String whoObtained = "";
    final static Integer initialProjectsNumber = 3;
    public static Integer currentProjectsNumber;

    static Random rand = new Random();
    double priceCalc;


    // Warunki regulujace zasady budowania nowych Projektów

    public String[] projectNames = {"PolexBud", "AssBook", "Stonka", "Mal_content", "Reptiliana", "Avatar", "Barnaba sp. z o.o.", "Computex", "KołpaK S.A.", "P.H.U. \"Kormoran\"", "ArtemidaRex", "DVD-Project",};
    public String[] projectTypes = {"Strona-wizytówka Firmy", "Społecznościowa Aplikacja mobilna", "Forum Internetowe", "Sklep Internetowy", "Projekt Bazodanowy", "Aplikacja Streamingowa", "Projekt Gamifikacji"};
    public Integer frontEndDaysMAX = 14;
    public Integer backEndDaysMAX = 21;
    public Integer databaseDaysMAX = 14;
    public Integer mobileDaysMAX = 14;
    public Integer wordpressDaysMAX = 7;
    public Integer prestaShopDaysMAX = 7;
    public String[] clients = {"Wyluzowany", "Wymagający", "SKRWL"};
    public Integer deadlineDaysMAX = 42;
    public Integer deadlinePenaltyPercentMAX = 50;
    public Integer priceMax = 100000;
    public Integer paymentDaysMAX = 30;
    public static Integer[] complexityLvls = {1, 2, 3};

    // Generator nowych Projektów

    public Project(Integer complexityLvl) {
        this.projectName = projectTypes[rand.nextInt(projectTypes.length)] + " " + projectNames[rand.nextInt(projectNames.length)];
        this.complexityLvl = complexityLvl;
        this.client = clients[rand.nextInt(clients.length)];
        this.deadlineDays = rand.nextInt(7, deadlineDaysMAX);
        this.deadlinePenalty = rand.nextInt(1, deadlinePenaltyPercentMAX);
        this.paymentDays = rand.nextInt(1, paymentDaysMAX);
        this.taken = false;
        this.fuckedUpCode = false;
        this.fuckUpsCount = 0;
        this.isOutsourced = false;
        this.touchedByBoss = false;
        this.status = "Nie rozpoczęty";
        if (this.complexityLvl == 1) {
            priceCalc = Math.round(rand.nextDouble(10000.0, priceMax - 75000.0));
            this.price = priceCalc;
            this.frontEndDays = rand.nextInt(0, frontEndDaysMAX);
            this.backEndDays = rand.nextInt(1, backEndDaysMAX);
            this.databaseDays = rand.nextInt(0, databaseDaysMAX);
            this.mobileDays = rand.nextInt(0, mobileDaysMAX);
            this.wordpressDays = 0;
            this.prestaShopDays = 0;
        } else if (this.complexityLvl == 2) {
            priceCalc = Math.round(rand.nextDouble(25000.0, priceMax - 50000.0));
            this.price = priceCalc;
            this.frontEndDays = rand.nextInt(1, frontEndDaysMAX);
            this.backEndDays = rand.nextInt(1, backEndDaysMAX);
            this.databaseDays = rand.nextInt(0, databaseDaysMAX);
            this.mobileDays = rand.nextInt(0, mobileDaysMAX);
            this.wordpressDays = rand.nextInt(0, wordpressDaysMAX);
            this.prestaShopDays = rand.nextInt(0, prestaShopDaysMAX);
        } else {
            priceCalc = Math.round(rand.nextDouble(50000.0, priceMax));
            this.price = priceCalc;
            this.frontEndDays = rand.nextInt(1, frontEndDaysMAX);
            this.backEndDays = rand.nextInt(1, backEndDaysMAX);
            this.databaseDays = rand.nextInt(1, databaseDaysMAX);
            this.mobileDays = rand.nextInt(0, mobileDaysMAX);
            this.wordpressDays = rand.nextInt(0, wordpressDaysMAX);
            this.prestaShopDays = rand.nextInt(0, prestaShopDaysMAX);
        }

    }

    // Utworzenie bazy projektów oraz uzupełnienie początkowej puli dostępnych projektów

    public static Project[] projects = new Project[100];

    public static void generateStartProjects(int complexityLvl) {

        for (int i = 0; i < initialProjectsNumber; i++) {
            projects[i] = new Project(complexityLvl);
            projects[i].projectID = i;
        }
        currentProjectsNumber = initialProjectsNumber;
    }

    //Dodawanie kolejnego projektu do bazy

    public static void generateAdditionalProject(){
        projects[currentProjectsNumber] = new Project(complexityLvls[rand.nextInt(complexityLvls.length)]);
        projects[currentProjectsNumber].projectID = currentProjectsNumber;
        currentProjectsNumber++;
    }

    // format wypisywania ogłoszeń
    public String toString() {
        return "----------------------------------------------------------------------------------\n" +
                ">>DANE PROJEKTU:\n" +
                "ID Ogłoszenia: (" + projectID + ");\n" +
                "Nazwa Projektu: " + projectName + ";\n" +
                "Ilość dni na realizację: " + deadlineDays + ";\n" +
                "Kara za opóźnienie (% faktury): " + deadlinePenalty + "%;\n" +
                "Wynagrodzenie: " + price + " zł;\n" +
                "Termin płatności: " + paymentDays + " dni;\n" +
                "\n>>WYMAGANE TECHNOLOGIE:\n" +
                "front-End: " + frontEndDays + " dni;\n" +
                "back-End: " + backEndDays + " dni;\n" +
                "Bazy Danych: " + databaseDays + " dni;\n" +
                "Mobile: " + mobileDays + " dni;\n" +
                "WordPress: " + wordpressDays + " dni;\n" +
                "PrestaShop: " + prestaShopDays + " dni;\n";
    }

    //wypisanie wszystkich dostępnych w danym momencie projektów
    public static void printActiveProjects() {
        for (Project project : projects) {
            while(project != null) {
            if (!project.taken) System.out.println(project);
            break;
            }
        }
    }

    // format wypisywania statusu posiadanych ogłoszeń
    public String toString(boolean x) {

        String finalLines;

        if(status.equals("Zakończony")) {
            finalLines = "\n>>DANE DOTYCZĄCE ZAPLATY:\n" +
                        "Przewidywany termin zapłaty: " + payDay + ";\n" +
                        "Przewidywana kwota zapłaty: " + paySum + ";\n";
        } else {
            finalLines = "\n>>TERMIN ODDANIA:\n" +
                    "Deadline (data): " + deadlineDate + ";\n" +
                    "Pozostało dni: " + (ChronoUnit.DAYS.between(Main.currentDate, deadlineDate)) + ";\n";
        }

        return "----------------------------------------------------------------------------------\n" +
                ">>DANE PROJEKTU:\n" +
                "ID Projektu: (" + projectID + ");\n" +
                "Nazwa Projektu: " + projectName + ";\n" +
                "Status Projektu: " + status + ";\n" +
                "Wynagrodzenie: " + price + " zł;\n" +
                "Kara za opóźnienie (% faktury): " + deadlinePenalty + "%;\n" +
                "Termin płatności: " + paymentDays + " dni;\n" +
                "\n>>STATUS REALIZACJI:\n" +
                "front-End: " + frontEndDays + " dni;\n" +
                "back-End: " + backEndDays + " dni;\n" +
                "Bazy Danych: " + databaseDays + " dni;\n" +
                "Mobile: " + mobileDays + " dni;\n" +
                "WordPress: " + wordpressDays + " dni;\n" +
                "PrestaShop: " + prestaShopDays + " dni;\n" +
                finalLines
                ;
    }

    // SKRÓCONY format wypisywania statusu posiadanych ogłoszeń
    public String toString(boolean x, boolean y) {
        return "----------------------------------------------------------------------------------\n" +
                "(" + projectID + ") " + projectName + ";" +
                "\n>>STATUS REALIZACJI:\n" +
                "front-End: " + frontEndDays + " dni; " + "back-End: " + backEndDays + " dni; " + "Bazy Danych: " + databaseDays + " dni; " + "Mobile: " + mobileDays + " dni; " + "WordPress: " + wordpressDays + " dni; " + "PrestaShop: " + prestaShopDays + " dni;" +
                "\n>>TERMIN ODDANIA:\n" +
                "Pozostało dni: " + (ChronoUnit.DAYS.between(Main.currentDate, deadlineDate)) + ";\n"
                ;
    }


}
