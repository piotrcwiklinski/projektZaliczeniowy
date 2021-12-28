import java.util.Random;

public class Project {

    public Integer projectID;
    public String projectname;
    public Integer frontEndDays;
    public Integer backEndDays;
    public Integer databaseDays;
    public Integer mobileDays;
    public Integer wordpressDays;
    public Integer prestaShopDays;
    public String client;
    public Integer deadlineDays;
    public Integer deadlinePenalty;
    public Integer price;
    public Integer paymentDays;
    public Integer complexityLvl;
    public boolean taken;
    public String owner;

    static Random rand = new Random();


    // Warunki regulujace zasady budowania nowych Projektów

    public String[] projectNames = {"PolexBud", "AssBook", "Stonka", "Mal_content", "Reptiliana", "Avatar", "Barnaba sp. z o.o.", "Computex", "KołpaK S.A.", "P.H.U. \"Kormoran\"", "ArtemidaRex", "DVD-Project",};
    public String[] projectTypes = {"Strona-wizytówka Firmy", "Społecznościowa Aplikacja mobilna", "Forum Internetowe", "Sklep Internetowy", "Projekt Bazodanowy", "Aplikacja Streamingowa", "Projekt Gamifikacji"};
    public String[] skillNames = {"front-End", "back-End", "Bazy Danych", "Mobile", "Wordpress", "PrestaShop"};
    public Integer frontEndDaysMAX = 14;
    public Integer backEndDaysMAX = 21;
    public Integer databaseDaysMAX = 14;
    public Integer mobileDaysMAX = 14;
    public Integer wordpressDaysMAX = 7;
    public Integer prestaShopDaysMAX = 7;
    public String[] clients = {"Wyluzowany", "Wymagający", "SKRWL"};
    public Integer deadlineDaysMAX = 42;
    public Integer deadlinePenaltyPercentMAX = 50;
    public Integer priceMax = 200000;
    public Integer paymentDaysMAX = 60;
    public Integer[] complexityLvls = {1, 2, 3};

    // Generator nowych Projektów

    public Project(Integer complexityLvl) {
        this.projectname = projectTypes[rand.nextInt(projectTypes.length)] + " " + projectNames[rand.nextInt(projectNames.length)];
        this.complexityLvl = complexityLvl;
        this.client = clients[rand.nextInt(clients.length)];
        this.deadlineDays = rand.nextInt(7, deadlineDaysMAX);
        this.deadlinePenalty = rand.nextInt(deadlinePenaltyPercentMAX);
        this.paymentDays = rand.nextInt(paymentDaysMAX);
        this.taken = false;
        if (this.complexityLvl == 1) {
            this.price = rand.nextInt(10000, priceMax - 150000);
            this.frontEndDays = rand.nextInt(0, frontEndDaysMAX);
            this.backEndDays = rand.nextInt(1, backEndDaysMAX);
            this.databaseDays = rand.nextInt(0, databaseDaysMAX);
            this.mobileDays = rand.nextInt(0, mobileDaysMAX);
            this.wordpressDays = 0;
            this.prestaShopDays = 0;
        } else if (this.complexityLvl == 2) {
            this.price = rand.nextInt(25000, priceMax - 100000);
            this.frontEndDays = rand.nextInt(1, frontEndDaysMAX);
            this.backEndDays = rand.nextInt(1, backEndDaysMAX);
            this.databaseDays = rand.nextInt(0, databaseDaysMAX);
            this.mobileDays = rand.nextInt(0, mobileDaysMAX);
            this.wordpressDays = rand.nextInt(0, wordpressDaysMAX);
            this.prestaShopDays = rand.nextInt(0, prestaShopDaysMAX);
        } else {
            this.price = rand.nextInt(50000, priceMax);
            this.frontEndDays = rand.nextInt(1, frontEndDaysMAX);
            this.backEndDays = rand.nextInt(1, backEndDaysMAX);
            this.databaseDays = rand.nextInt(1, databaseDaysMAX);
            this.mobileDays = rand.nextInt(0, mobileDaysMAX);
            this.wordpressDays = rand.nextInt(0, wordpressDaysMAX);
            this.prestaShopDays = rand.nextInt(0, prestaShopDaysMAX);
        }

    }

    public static Project[] projects = new Project[50];

    public static void generateStartProjects(int complexityLvl) {

        for (int i = 0; i < 3; i++) {
            projects[i] = new Project(complexityLvl);
            projects[i].projectID = i;
        }
    }

    public String toString() {
        return "----------------------------------------------------------------------------------\n" +
                "ID Ogłoszenia: (" + projectID + ");\n" +
                "Nazwa Projektu: " + projectname + ";\n" +
                "Ilość dni na realizację: " + deadlineDays + ";\n" +
                "Kara za opóźnienie (% faktury): " + deadlinePenalty + "%;\n" +
                "Wynagrodzenie: " + price + " zł;\n" +
                "Termin płatności: " + paymentDays + " dni;\n" +
                "WYMAGANE TECHNOLOGIE:\n" +
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


}
