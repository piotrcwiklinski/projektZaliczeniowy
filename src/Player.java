import Employees.*;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Scanner;

public class Player {

    public String name;
    public Boolean alive;
    public Double cash;
    public Boolean winCondition;
    public Double startingCash = 20000.0;
    public Double monthlyCommitment;
    public Integer jobSearchDays;
    public Integer taxSettlementDays;
    public Double incomeToBeTaxed;
    public Integer codersCount;
    public Integer testersCount;
    public Integer winConditionProjectsClosed = 0;
    public Integer winCoditionProjectsClosedWithSalesPersonAssigned = 0;

    public boolean x, y;

    Player(String name) {
        this.name = name;
        this.alive = true;
        this.cash = startingCash;
        this.monthlyCommitment = 0.0;
        this.jobSearchDays = 0;
        this.taxSettlementDays = 0;
        this.incomeToBeTaxed = 0.0;
        this.codersCount = 0;
        this.testersCount = 0;

        this.winCondition = false;
    }

    Scanner in = new Scanner(System.in);
    Random rand = new Random();

    public void doTurn() {

        System.out.println("\n----------------------------------------------------------------------------------");

        boolean actionChosen = false;

        AliveCheck:
        if (this.alive) {
            System.out.println("\n\tWitaj " + this.name + "!");
//NOWY MIESIĄC
            if (Main.newMonthFlag) {
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println("\n\tZAMKNIĘCIE POPRZEDNIEGO MIESIĄCA:");
                if (this.taxSettlementDays < 2) {
                    System.out.println("\n>Nie poświęciłeś dość dni na rozliczenia w poprzednim miesiącu.");
                    System.out.println("\n>Urząd Skarbowy wjeżdża w Twoją firmę z siłą huraganu!");
                    System.out.println("\n>Efektem kontroli jest mandat w wysokości 1 000 000 zł!");
                    this.cash -= 1000000;
                }
                this.taxSettlementDays = 0;
                if (this.cash < (this.incomeToBeTaxed * 0.1)) {
                    System.out.println("\n>Nie stać Cię aby opłacić podatek od sprzedaży (10% miesięcznego przychodu)..");
                } else {
                    System.out.println("\n>Regulujesz podatek od sprzedaży (10% miesięcznego przychodu) w wysokości: " + (this.incomeToBeTaxed * 0.1) + " zł;");
                }
                this.cash -= (this.incomeToBeTaxed * 0.1);
                this.incomeToBeTaxed = 0.0;

                if (this.cash < this.monthlyCommitment) {
                    System.out.println("\n>Nie stać Cię na uregulowanie wynagrodzeń Pracowników..");
                } else {
                    System.out.println("\n>Ponosisz koszt wynagrodzeń swoich Pracowników w wysokości: " + (this.monthlyCommitment) + " zł;");
                }
                this.cash -= this.monthlyCommitment;

                System.out.println("----------------------------------------------------------------------------------");
            }
//PAY DAY
            for (Project project: Project.projects) {
                while(project != null) {
                    if(project.status.equals("Zakończony") && project.payDay != null ){
                    if (project.owner.equals(this.name) && project.payDay.equals(Main.currentDate)) {
                        this.cash += project.paySum;
                        this.incomeToBeTaxed += project.paySum;
                        System.out.println("\n>GRATULACJE! Otrzymałeś płatność za jeden z oddanych projektów: " + project.projectName + ";");
                        System.out.println(">Na Twoje konto bankowe wpłynęła właśnie kwota " + project.paySum + " zł;");

                        if(project.paySum >= project.price && project.complexityLvl.equals(3) && !project.touchedByBoss) this.winConditionProjectsClosed++;
                        if(project.paySum >= project.price && project.complexityLvl.equals(3) && !project.touchedByBoss && project.whoObtained.equals(this.name)) this.winCoditionProjectsClosedWithSalesPersonAssigned++;

                    }
                }
                    break;
                }
            }

//GLOWNE MENU
            Menu:
            while (!actionChosen && this.cash > 0) {
                System.out.println("\n\tMENU:");
                System.out.println("\t1. Wybierz, co chcesz dzisiaj zrobić");
                System.out.println("\t2. Sprawdź stan konta firmy");
                System.out.println("\t3. Sprawdź stan realizacji swoich projektów");
                System.out.println("\t4. Przejrzyj dostępne do podjęcia projekty");
                System.out.println("\t5. Przejrzyj dostępnych Pracowników");
                System.out.println("\t0. Zakończ grę");

                String input = in.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("\n\tCo chcesz dziś zrobić?\n");
                        System.out.println("\t1. Podpisz umowę na nowy projekt (z dostępnych projektów)");
                        System.out.println("\t2. Szukaj nowych Klientów/projektów do podjęcia");
                        System.out.println("\t3. Poświęć dzień na samodzielne programowanie");
                        System.out.println("\t4. Poświęć dzień na testowanie kodu");
                        System.out.println("\t5. Oddaj gotowy projekt Klientowi");
                        System.out.println("\t6. Zatrudnij nowego Pracownika");
                        System.out.println("\t7. Zwolnij Pracownika");
                        System.out.println("\t8. Poświęć dzień na rozliczenia z urzędami");
                        System.out.println("\t9. Wróć do Menu");

                        input = in.nextLine();
//LISTA PROJEKTÓW
                        switch (input) {
                            case "1":
                                System.out.println("\n\tLISTA DOSTĘPNYCH PROJEKTÓW DO REALIZACJI:");
                                Project.printActiveProjects();
                                System.out.println("\n\tKTÓRY PROJEKT WYBIERASZ? (Podaj ID Ogłoszenia lub \"stop\" by wrócić do Menu)");

                                input = in.nextLine();

                                if (Main.isNumeric(input)) {
                                    try {
                                        Project.projects[Integer.parseInt(input)].owner = this.name;
                                        Project.projects[Integer.parseInt(input)].taken = true;
                                        Project.projects[Integer.parseInt(input)].status = "W trakcie realizacji";
                                        Project.projects[Integer.parseInt(input)].deadlineDate = Main.currentDate.plusDays(Project.projects[Integer.parseInt(input)].deadlineDays);
                                        System.out.println("Udało się pozyskać projekt nr. " + Integer.parseInt(input) + " do realizacji!");
                                        actionChosen = true;
                                    } catch (Exception exc) {
                                        System.out.println("Nie ma takiego projektu! Spróbuj ponownie");
                                    }
                                } else if (input.equals("stop")) continue Menu;
                                break;
//SZUKANIE ZLECEN
                            case "2":
                                System.out.println("Czy na pewno chcesz poświęcić ten dzień na szukanie nowych Zleceń do realizacji? (Wpisz \"tak\" by potwierdzić)");

                                input = in.nextLine();

                                if (input.equals("tak")) {
                                    System.out.println("\n>Poświęciłeś dzień na szukanie na rynku nowych Zleceń do realizacji.");
                                    this.jobSearchDays++;
                                    if (this.jobSearchDays < 5) {
                                        System.out.println(">Do tej pory szukałeś nowego zlecenie łącznie " + this.jobSearchDays + " dni.");
                                        System.out.println(">Pamiętaj, że każde kolejne 5 dni poświęconych na ten cel to gwarancja jednego nowego zlecenia!");
                                    } else {
                                        Project.generateAdditionalProject();
                                        System.out.println("Szukałeś nowego zlecenia przez " + jobSearchDays + " dni. Opłaciło Ci się! Dodano nowy Projekt dostępny do podjęcia!");
                                        this.jobSearchDays = 0;
                                    }
                                    actionChosen = true;
                                }
                                break;
//SAMODZIELNE KODOWANIE
                            case "3":
                                System.out.println("\n\tKTÓRYM PROJEKTEM CHCESZ SIĘ DZIS ZAJĄC? (Podaj ID Projektu lub \"stop\" by wrócić do Menu)");
                                printOwnedActiveProjectsShort(x, y);

                                input = in.nextLine();

                                if (Main.isNumeric(input)) {
                                    try {
                                        if (Project.projects[Integer.parseInt(input)].owner.equals(this.name)) {
                                            int tempProjectID = Integer.parseInt(input);
                                            System.out.println("\n\tKtórą technologię chcesz dziś Programować w ramach tego projektu?\n");
                                            if (Project.projects[tempProjectID].frontEndDays > 0) {
                                                System.out.println(">>front-End (wpisz \"fe\")");
                                            }
                                            if (Project.projects[tempProjectID].backEndDays > 0) {
                                                System.out.println(">>back-End (wpisz \"be\")");
                                            }
                                            if (Project.projects[tempProjectID].databaseDays > 0) {
                                                System.out.println(">>Bazy Danych (wpisz \"bd\")");
                                            }
                                            if (Project.projects[tempProjectID].wordpressDays > 0) {
                                                System.out.println(">>WordPress (wpisz \"wp\")");
                                            }
                                            if (Project.projects[tempProjectID].prestaShopDays > 0) {
                                                System.out.println(">>PrestaShop (wpisz \"ps\")");
                                            }

                                            input = in.nextLine();

                                            switch (input) {
                                                case "fe":
                                                    Project.projects[tempProjectID].frontEndDays--;
                                                    System.out.println(">Udało Ci się poświęcić dzisiejszy dzień na Programowanie wybranego projektu!");
                                                    Project.projects[tempProjectID].touchedByBoss = true;
                                                    actionChosen = true;
                                                    break;

                                                case "be":
                                                    Project.projects[tempProjectID].backEndDays--;
                                                    System.out.println(">Udało Ci się poświęcić dzisiejszy dzień na Programowanie wybranego projektu!");
                                                    Project.projects[tempProjectID].touchedByBoss = true;
                                                    actionChosen = true;
                                                    break;

                                                case "bd":
                                                    Project.projects[tempProjectID].databaseDays--;
                                                    System.out.println(">Udało Ci się poświęcić dzisiejszy dzień na Programowanie wybranego projektu!");
                                                    Project.projects[tempProjectID].touchedByBoss = true;
                                                    actionChosen = true;
                                                    break;

                                                case "wp":
                                                    Project.projects[tempProjectID].wordpressDays--;
                                                    System.out.println(">Udało Ci się poświęcić dzisiejszy dzień na Programowanie wybranego projektu!");
                                                    Project.projects[tempProjectID].touchedByBoss = true;
                                                    actionChosen = true;
                                                    break;

                                                case "ps":
                                                    Project.projects[tempProjectID].prestaShopDays--;
                                                    System.out.println(">Udało Ci się poświęcić dzisiejszy dzień na Programowanie wybranego projektu!");
                                                    Project.projects[tempProjectID].touchedByBoss = true;
                                                    actionChosen = true;
                                                    break;

                                                default:
                                                    System.out.println("\tNieprawidłowy wybór!");
                                                    break;
                                            }

                                        } else {
                                            System.out.println(">Podany numer ID projektu nie należy do Ciebie! Spróbuj ponownie");
                                            continue Menu;
                                        }
                                    } catch (Exception exc) {
                                        System.out.println(">Podano nieprawidłowy numer ID projektu! Spróbuj ponownie");
                                    }

                                } else if (input.equals("stop")) continue Menu;
                                break;
//SAMODZIELNE TESTOWANIE
                            case "4":
                                System.out.println("\n\tKTÓRY PROJEKT CHCESZ DZIS PRZETESTOWAC? (Podaj ID Projektu lub \"stop\" by wrócić do Menu)");
                                printOwnedActiveProjectsShort(x, y);

                                input = in.nextLine();

                                if (Main.isNumeric(input)) {
                                    try {
                                        if (Project.projects[Integer.parseInt(input)].owner.equals(this.name) && !Project.projects[Integer.parseInt(input)].status.equals("Zakończony")) {
                                            int tempProjectID = Integer.parseInt(input);
                                            Project.projects[tempProjectID].touchedByBoss = true;

                                            if (Project.projects[tempProjectID].fuckUpsCount > 0) {
                                                Project.projects[tempProjectID].fuckUpsCount--;
                                                System.out.println(">Przetestowano kod wskazanego Projektu i usunięto jeden z błędów.\n");
                                            }
                                            if (Project.projects[tempProjectID].fuckUpsCount.equals(0)) {
                                                Project.projects[tempProjectID].fuckedUpCode = false;
                                                System.out.println(">Kod wskazanego Projektu nie ma żadnych błędów.\n");
                                            }
                                            actionChosen = true;
                                        } else {
                                            System.out.println(">Podany numer ID projektu nie należy do Ciebie! Spróbuj ponownie");
                                            continue Menu;
                                        }
                                    } catch (Exception exc) {
                                        System.out.println(">Podano nieprawidłowy numer ID projektu! Spróbuj ponownie");
                                    }

                                } else if (input.equals("stop")) continue Menu;
                                break;
//ODDAWANIE GOTOWE PROJEKTU
                            case "5":
                                System.out.println("\n\tLISTA PROJEKTÓW GOTOWYCH DO ODDANIA KLIENTOWI:");
                                printOwnedReadyProjects(x);
                                System.out.println("\n\tKTÓRY PROJEKT WYBIERASZ? (Podaj ID Ogłoszenia lub \"stop\" by wrócić do Menu)");

                                input = in.nextLine();

                                if (Main.isNumeric(input)) {
                                    try {
                                        if (Project.projects[Integer.parseInt(input)].owner.equals(this.name) && Project.projects[Integer.parseInt(input)].status.equals("Gotowy do oddania Klientowi")) {
                                            int tempProjectID = Integer.parseInt(input);
                                            Project.projects[tempProjectID].payDay = Main.currentDate.plusDays(Project.projects[tempProjectID].paymentDays);
                                            if ((ChronoUnit.DAYS.between(Main.currentDate, Project.projects[tempProjectID].deadlineDate)) >= 0) {
                                                Project.projects[tempProjectID].paySum = Project.projects[tempProjectID].price;
                                            } else {
                                                Project.projects[tempProjectID].paySum = (Project.projects[tempProjectID].price) * (1.0 - (Project.projects[tempProjectID].deadlinePenalty / 100.0));
                                            }

                                            switch (Project.projects[tempProjectID].client) {
                                                case "Wyluzowany" -> {
                                                    System.out.println("\n>Oddajesz projekt Klientowi, który okazał się wyluzowanym gościem...");
                                                    if (rand.nextInt(100) < 30) {
                                                        Project.projects[tempProjectID].payDay = Project.projects[tempProjectID].payDay.plusDays(7);
                                                        System.out.println(">Poinformował Cię, że może spóźnić się z płatnością o tydzień..\n");
                                                    }
                                                    if ((ChronoUnit.DAYS.between(Main.currentDate, Project.projects[tempProjectID].deadlineDate)) < 0 && (ChronoUnit.DAYS.between(Main.currentDate, Project.projects[tempProjectID].deadlineDate)) >= -7 && rand.nextInt(100) < 20) {
                                                        Project.projects[tempProjectID].paySum = Project.projects[tempProjectID].price;
                                                        System.out.println(">Dał znać, że nie robi mu różnicy, że spóźniłeś się z oddaniem projektu o kilka dni i nie będzie robić z tego tytułu problemów..\n");
                                                    }
                                                    if (Project.projects[tempProjectID].fuckedUpCode) {
                                                        System.out.println(">Powiedział, że wie, że projekt ma kilka błędów, ale specjalnie mu to nie przeszkadza..\n");
                                                    }
                                                }
                                                case "Wymagający" -> {
                                                    System.out.println("\n>Oddajesz projekt Klientowi, który okazał się być wymagającym...");
                                                    if (Project.projects[tempProjectID].fuckedUpCode && rand.nextInt(100) < 50) {
                                                        Project.projects[tempProjectID].payDay = null;
                                                        Project.projects[tempProjectID].paySum = 0.0;
                                                        System.out.println(">Niestety, okazało się, że w kodzie Programu były błędy - Klient informuje, że nie zapłaci za niego ani grosza..\n");
                                                    }
                                                }
                                                case "SKRWL" -> {
                                                    System.out.println("\n>Oddajesz projekt Klientowi, który okazał się całkiem nieprzyjemnym gościem...");
                                                    if (Project.projects[tempProjectID].fuckedUpCode) {
                                                        Project.projects[tempProjectID].payDay = null;
                                                        Project.projects[tempProjectID].paySum = 0.0;
                                                        System.out.println(">Niestety, okazało się, że w kodzie Programu były błędy - Klient informuje, że nie zapłaci za niego ani grosza..\n");
                                                    } else if (rand.nextInt(100) < 1) {
                                                        Project.projects[tempProjectID].payDay = null;
                                                        Project.projects[tempProjectID].paySum = 0.0;
                                                        System.out.println(">Bez podawania żadnej konkretnej przyczyny, powiedział że po prostu Ci za ten projekt nie zapłaci..\n");
                                                    } else {
                                                        int i = rand.nextInt(100);
                                                        if (i < 5) {
                                                            Project.projects[tempProjectID].payDay = Project.projects[tempProjectID].payDay.plusMonths(1);
                                                            System.out.println(">Poinformował Cię, że może spóźnić się z płatnością o miesiąc..\n");
                                                        } else if (i < 30) {
                                                            Project.projects[tempProjectID].payDay = Project.projects[tempProjectID].payDay.plusDays(7);
                                                            System.out.println(">Poinformował Cię, że może spóźnić się z płatnością o tydzień..\n");
                                                        }
                                                    }
                                                }
                                            }

                                            if(Project.projects[tempProjectID].isOutsourced && Project.projects[tempProjectID].payDay != null) {
                                                double outSourceFee = Project.projects[tempProjectID].paySum * (Coder.coders[Project.projects[tempProjectID].outsourcerId].projectPriceShareIfOutsourced / 100.0);
                                                outSourceFee = Math.round(outSourceFee);
                                                System.out.println("\n>Projekt realizowany był z pomocą Podwykonawcy, który pobierze za niego kwotę " + outSourceFee + " zł z faktury końcowej;\n");
                                                Project.projects[tempProjectID].paySum -= outSourceFee;
                                            }

                                            if(Project.projects[tempProjectID].payDay != null && Project.projects[tempProjectID].paySum != 0.0) {
                                                System.out.println("\n>PODSUMOWUJĄC:");
                                                System.out.println(">Otrzymasz za ten projekt " + Project.projects[tempProjectID].paySum + " zł;");
                                                System.out.println(">Możesz spodziewać się płatności w dniu: " + Project.projects[tempProjectID].payDay + ";\n");
                                            }

                                            Project.projects[tempProjectID].status = "Zakończony";

                                            actionChosen = true;

                                        } else {
                                            System.out.println("Projekt o wskazanym ID nie należy do Ciebie, lub prace nad nim nie zostały jeszcze ukończone!");
                                        }
                                    } catch (Exception exc) {
                                        System.out.println("Nie ma takiego ID projektu! Spróbuj ponownie");
                                    }
                                } else if (input.equals("stop")) continue Menu;
                                break;
//ZATRUDNIANIE
                            case "6":
                                System.out.println("\n\tJAKIEGO RODZAJU PRACOWNIKA CHCESZ ZATRUDNIC? (podaj numer z listy poniżej)\n");
                                System.out.println("\t1. >PROGRAMISTĘ");
                                System.out.println("\t2. >SPRZEDAWCĘ");
                                System.out.println("\t3. >TESTERA");

                                input = in.nextLine();

                                switch (input) {
                                    case "1":
                                        System.out.println("\n\tLISTA DOSTĘPNYCH DO ZATRUDNIENIA PRACOWNIKÓW:");
                                        Coder.printAvailableCoders();

                                        System.out.println("\n\tKTÓREGO PRACOWNIKA CHCESZ ZATRUDNIC? (Podaj ID Pracownika lub \"stop\" by wrócić do Menu)\n");

                                        input = in.nextLine();

                                        if (Main.isNumeric(input)) {
                                            try {
                                                if (Coder.coders[Integer.parseInt(input)].isAssigned) {
                                                    System.out.println(">Pracownik o podanym przez Ciebie ID jest już przez kogoś zatrudniony!");
                                                } else {
                                                    if (!Coder.coders[Integer.parseInt(input)].isOutsourcer) {
                                                        Coder.coders[Integer.parseInt(input)].employerName = this.name;
                                                        Coder.coders[Integer.parseInt(input)].isAssigned = true;
                                                        this.monthlyCommitment += Coder.coders[Integer.parseInt(input)].salary;
                                                        this.codersCount++;
                                                        System.out.println(">Udało Ci się zatrudnić nowego Pracownika! " + Coder.coders[Integer.parseInt(input)].empName + " zasila Twoje szeregi!");
                                                        actionChosen = true;
                                                    } else {
                                                        int tempEmpID = Integer.parseInt(input);

                                                        System.out.println("""
                                                                                                                                
                                                                >Wybrany przez Ciebie Pracownik to Podwykonawca, musisz zatem przypisać go do jakiegoś projektu.
                                                                >Do którego z obecnie realizowanych projektów chciałbyś go przypisać?\s
                                                                >(Podaj ID Projektu lub wpisz "stop" by powrócić do Menu)
                                                                """);

                                                        printOwnedActiveProjectsShortNonOutsourced(x, y);

                                                        input = in.nextLine();

                                                        if (Main.isNumeric(input)) {
                                                            try {
                                                                if (Project.projects[Integer.parseInt(input)].owner.equals(this.name) && !Project.projects[Integer.parseInt(input)].isOutsourced) {
                                                                    Coder.coders[tempEmpID].employerName = this.name;
                                                                    Coder.coders[tempEmpID].isAssigned = true;
                                                                    Coder.coders[tempEmpID].projectIdIfOutsourced = Integer.parseInt(input);
                                                                    Project.projects[Integer.parseInt(input)].isOutsourced = true;
                                                                    Project.projects[Integer.parseInt(input)].outsourcerId = tempEmpID;
                                                                    this.codersCount++;
                                                                    System.out.println(">Udało Ci się pozyskać Podwykonawcę! " + Coder.coders[tempEmpID].empName + " będzie realizować projekt o ID (" + Integer.parseInt(input) + ");");
                                                                    actionChosen = true;
                                                                } else {
                                                                    System.out.println(">Nie możesz przypisać tego Podwykonawcy do podanego numeru ID.");
                                                                    continue Menu;
                                                                }
                                                            } catch (Exception exc) {
                                                                System.out.println(">Podano nieprawidłowy numer ID projektu! Spróbuj ponownie");
                                                            }

                                                        } else if (input.equals("stop")) continue Menu;

                                                    }
                                                }
                                            } catch (Exception exc) {
                                                System.out.println(">Nieprawidłowy format ID! Spróbuj ponownie");
                                            }
                                        } else if (input.equals("stop")) continue Menu;
                                        break;

                                    case "2":
                                        System.out.println("\n\tLISTA DOSTĘPNYCH DO ZATRUDNIENIA PRACOWNIKÓW:");
                                        SalesPerson.printAvailableSalesPpl();

                                        System.out.println("\n\tKTÓREGO PRACOWNIKA CHCESZ ZATRUDNIC? (Podaj ID Pracownika lub \"stop\" by wrócić do Menu)\n");

                                        input = in.nextLine();

                                        if (Main.isNumeric(input)) {
                                            try {
                                                if (SalesPerson.salesPpl[Integer.parseInt(input)].isAssigned) {
                                                    System.out.println(">Pracownik o podanym przez Ciebie ID jest już przez kogoś zatrudniony!");
                                                } else {
                                                    SalesPerson.salesPpl[Integer.parseInt(input)].employerName = this.name;
                                                    SalesPerson.salesPpl[Integer.parseInt(input)].isAssigned = true;
                                                    this.monthlyCommitment += SalesPerson.salesPpl[Integer.parseInt(input)].salary;
                                                    System.out.println(">Udało Ci się zatrudnić nowego Pracownika! " + SalesPerson.salesPpl[Integer.parseInt(input)].empName + " zasila Twoje szeregi!");
                                                    actionChosen = true;
                                                }
                                            } catch (Exception exc) {
                                                System.out.println(">Nieprawidłowy format ID! Spróbuj ponownie");
                                            }
                                        } else if (input.equals("stop")) continue Menu;
                                        break;

                                    case "3":
                                        System.out.println("\n\tLISTA DOSTĘPNYCH DO ZATRUDNIENIA PRACOWNIKÓW:");
                                        Tester.printAvailableTesters();

                                        System.out.println("\n\tKTÓREGO PRACOWNIKA CHCESZ ZATRUDNIC? (Podaj ID Pracownika lub \"stop\" by wrócić do Menu)\n");

                                        input = in.nextLine();

                                        if (Main.isNumeric(input)) {
                                            try {
                                                if (Tester.testers[Integer.parseInt(input)].isAssigned) {
                                                    System.out.println(">Pracownik o podanym przez Ciebie ID jest już przez kogoś zatrudniony!");
                                                } else {
                                                    Tester.testers[Integer.parseInt(input)].employerName = this.name;
                                                    Tester.testers[Integer.parseInt(input)].isAssigned = true;
                                                    this.monthlyCommitment += Tester.testers[Integer.parseInt(input)].salary;
                                                    this.testersCount++;
                                                    System.out.println(">Udało Ci się zatrudnić nowego Pracownika!" + Tester.testers[Integer.parseInt(input)].empName + " zasila Twoje szeregi!");
                                                    actionChosen = true;
                                                }
                                            } catch (Exception exc) {
                                                System.out.println(">Nieprawidłowy format ID! Spróbuj ponownie");
                                            }
                                        } else if (input.equals("stop")) continue Menu;
                                        break;

                                    default:
                                        System.out.println("\tNieprawidłowy wybór!");
                                        break;
                                }
                                break;
//ZWALNIANIE
                            case "7":
                                System.out.println("\n\tJAKIEGO RODZAJU PRACOWNIKA CHCESZ ZWOLNIC? (podaj numer z listy poniżej)\n");
                                System.out.println("\t1. >PROGRAMISTĘ");
                                System.out.println("\t2. >SPRZEDAWCĘ");
                                System.out.println("\t3. >TESTERA");

                                input = in.nextLine();

                                switch (input) {
                                    case "1":
                                        System.out.println("\n\tLISTA DOSTĘPNYCH DO ZWOLNIENIA PROGRAMISTÓW:");
                                        printHiredCoders();

                                        System.out.println("\n\tKTÓREGO PRACOWNIKA CHCESZ ZWOLNIC? (Podaj ID Pracownika lub \"stop\" by wrócić do Menu)\n");

                                        input = in.nextLine();

                                        if (Main.isNumeric(input)) {
                                            try {
                                                if (!Coder.coders[Integer.parseInt(input)].employerName.equals(this.name)) {
                                                    System.out.println(">Pracownik o podanym ID nie jest zatrudniony przez Ciebie!");
                                                } else {
                                                    Coder.coders[Integer.parseInt(input)].employerName = null;
                                                    Coder.coders[Integer.parseInt(input)].isAssigned = false;
                                                    this.monthlyCommitment -= Coder.coders[Integer.parseInt(input)].salary;
                                                    this.codersCount--;
                                                    this.cash -= Coder.coders[Integer.parseInt(input)].salary;
                                                    System.out.println(">Udało Ci się zwolnić Pracownika: " + Coder.coders[Integer.parseInt(input)].empName + ";");
                                                    System.out.println(">Ponosisz w związku z tym koszt jego 1-miesięcznego wynagrodzenia jako odprawy: " + Coder.coders[Integer.parseInt(input)].salary + " zł;");
                                                    actionChosen = true;
                                                }
                                            } catch (Exception exc) {
                                                System.out.println(">Nieprawidłowy format ID! Spróbuj ponownie");
                                            }
                                        } else if (input.equals("stop")) continue Menu;
                                        break;

                                    case "2":
                                        System.out.println("\n\tLISTA DOSTĘPNYCH DO ZWOLNIENIA SPRZEDAWCÓW:");
                                        printHiredSalesPpl();

                                        System.out.println("\n\tKTÓREGO PRACOWNIKA CHCESZ ZWOLNIC? (Podaj ID Pracownika lub \"stop\" by wrócić do Menu)\n");

                                        input = in.nextLine();

                                        if (Main.isNumeric(input)) {
                                            try {
                                                if (!SalesPerson.salesPpl[Integer.parseInt(input)].employerName.equals(this.name)) {
                                                    System.out.println(">Pracownik o podanym ID nie jest zatrudniony przez Ciebie!");
                                                } else {
                                                    SalesPerson.salesPpl[Integer.parseInt(input)].employerName = null;
                                                    SalesPerson.salesPpl[Integer.parseInt(input)].isAssigned = false;
                                                    this.monthlyCommitment -= SalesPerson.salesPpl[Integer.parseInt(input)].salary;
                                                    this.cash -= SalesPerson.salesPpl[Integer.parseInt(input)].salary;
                                                    System.out.println(">Udało Ci się zwolnić Pracownika: " + SalesPerson.salesPpl[Integer.parseInt(input)].empName + ";");
                                                    System.out.println(">Ponosisz w związku z tym koszt jego 1-miesięcznego wynagrodzenia jako odprawy: " + SalesPerson.salesPpl[Integer.parseInt(input)].salary + " zł;");
                                                    actionChosen = true;
                                                }
                                            } catch (Exception exc) {
                                                System.out.println(">Nieprawidłowy format ID! Spróbuj ponownie");
                                            }
                                        } else if (input.equals("stop")) continue Menu;
                                        break;

                                    case "3":
                                        System.out.println("\n\tLISTA DOSTĘPNYCH DO ZWOLNIENIA TESTERÓW:");
                                        printHiredTesters();

                                        System.out.println("\n\tKTÓREGO PRACOWNIKA CHCESZ ZWOLNIC? (Podaj ID Pracownika lub \"stop\" by wrócić do Menu)\n");

                                        input = in.nextLine();

                                        if (Main.isNumeric(input)) {
                                            try {
                                                if (!Tester.testers[Integer.parseInt(input)].employerName.equals(this.name)) {
                                                    System.out.println(">Pracownik o podanym ID nie jest zatrudniony przez Ciebie!");
                                                } else {
                                                    Tester.testers[Integer.parseInt(input)].employerName = null;
                                                    Tester.testers[Integer.parseInt(input)].isAssigned = false;
                                                    this.monthlyCommitment -= Tester.testers[Integer.parseInt(input)].salary;
                                                    this.testersCount--;
                                                    this.cash -= Tester.testers[Integer.parseInt(input)].salary;
                                                    System.out.println(">Udało Ci się zwolnić Pracownika: " + Tester.testers[Integer.parseInt(input)].empName + ";");
                                                    System.out.println(">Ponosisz w związku z tym koszt jego 1-miesięcznego wynagrodzenia jako odprawy: " + Tester.testers[Integer.parseInt(input)].salary + " zł;");
                                                    actionChosen = true;
                                                }
                                            } catch (Exception exc) {
                                                System.out.println(">Nieprawidłowy format ID! Spróbuj ponownie");
                                            }
                                        } else if (input.equals("stop")) continue Menu;
                                        break;
                                }
                                break;
//ROZLICZENIE Z US
                            case "8":
                                System.out.println("Czy na pewno chcesz poświęcić ten dzień na Rozliczenia z Urzędem Skarbowym? (Wpisz \"tak\" by potwierdzić)");

                                input = in.nextLine();

                                if (input.equals("tak")) {
                                    System.out.println("\n>Poświęciłeś dzień na rozliczenia z Urzędem Skarbowym.");
                                    this.taxSettlementDays++;
                                    if (this.taxSettlementDays < 2) {
                                        System.out.println(">W tym miesiącu poświęciłeś na rozliczenia " + this.taxSettlementDays + " dni.");
                                        System.out.println(">Pamiętaj, żeby w każdym miesiącu poświęcić na ten cel minimum 2 dni!");
                                    } else {
                                        System.out.println(">W tym miesiącu poświęciłeś na rozliczenia " + this.taxSettlementDays + " dni.");
                                        System.out.println(">Brawo! Możesz być spokojny o swoją firmę, ZUS nie wjedzie z kontrolą.");
                                    }
                                    actionChosen = true;
                                }
                                break;
//WROC DO MENU
                            case "9":
                                continue Menu;

                            default:
                                System.out.println("\tNieprawidłowy wybór!");
                                break;
                        }

                        break;
//STAN KONTA
                    case "2":
                        System.out.println("\tTwoje aktualnie dostępne środki: " + this.cash + " zł.\n");
                        System.out.println("\tTwoje obecne zobowiązania miesięczne wynoszą: " + this.monthlyCommitment + " zł.\n");
                        break;
//STATUS REALIZACJI
                    case "3":
                        System.out.println("\n\tSTATUS REALIZACJI TWOICH PROJEKTÓW:");
                        printOwnedProjects(x);
                        break;
//DOSTĘPNE PROJEKTY
                    case "4":
                        System.out.println("\n\tLISTA DOSTĘPNYCH PROJEKTÓW DO REALIZACJI:");
                        Project.printActiveProjects();
                        break;
//DOSTĘPNI PRACOWNICY
                    case "5":
                        System.out.println("\n\tLISTA DOSTĘPNYCH DO ZATRUDNIENIA PRACOWNIKÓW:");
                        Coder.printAvailableCoders();
                        SalesPerson.printAvailableSalesPpl();
                        Tester.printAvailableTesters();
                        break;
//CONCEDE
                    case "0":
                        System.out.println("Czy na pewno chcesz się poddać? (Wpisz \"tak\" by potwierdzić)");

                        input = in.nextLine();

                        if (input.equals("tak")) {
                            System.out.println("Zakończyłeś rozgrywkę. Zapraszamy ponownie!");
                            this.alive = false;
                        }
                        break AliveCheck;

                    default:
                        System.out.println("\tNieprawidłowy wybór!");
                        break;
                }
            }

//SPRAWDZENIE BANKRUCTWA
            if (this.cash <= 0) {
                System.out.println(">TWOJA FIRMA ZBANKRUTOWALA - To koniec gry dla Ciebie!\n");
                this.alive = false;
                break AliveCheck;
            }

//PRACOWNICY PRACUJĄ (chyba że jest weekend)
            if (Main.currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || Main.currentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                System.out.println("\n>Jest Weekend, Twoi Pracownicy i Podwykonawcy mają wolne.");
            } else {
                makeWorkersWorkAgain();
            }

//SPRAWDZENIE CZY JAKIES PROJEKTY SA UKONCZONE
            checkCompletedProjects();

//SPRAWDZENIE WIN CONDITIONS
            winConditionCheck();

        }
    }


    //wypisanie WSZYSTKICH projektów pobranych przez danego gracza

    public void printOwnedProjects(boolean x) {
        for (Project project : Project.projects) {
            while (project != null) {
                if (project.taken && project.owner.equals(this.name)) System.out.println(project.toString(x));
                break;
            }
        }
    }

    //wypisanie projektów pobranych przez danego gracza KTÓRE SĄ GOTOWE DO ODDANIA KLIENTOWI

    public void printOwnedReadyProjects(boolean x) {
        for (Project project : Project.projects) {
            while (project != null) {
                if (project.taken && project.owner.equals(this.name) && project.status.equals("Gotowy do oddania Klientowi"))
                    System.out.println(project.toString(x));
                break;
            }
        }
    }

    //wypisanie projektów pobranych przez danego gracza KTÓRE SĄ WCIĄZ W TRAKCIE REALIZACJI - Wersja Skrótowa

    public void printOwnedActiveProjectsShort(boolean x, boolean y) {
        for (Project project : Project.projects) {
            while (project != null) {
                if (project.taken && project.owner.equals(this.name) && !project.status.equals("Zakończony"))
                    System.out.println(project.toString(x, y));
                break;
            }
        }
    }

    //wypisanie projektów pobranych przez danego gracza KTÓRE SĄ WCIĄZ W TRAKCIE REALIZACJI BEZ PRZYPISANEGO OUTSOURCERA - Wersja Skrótowa

    public void printOwnedActiveProjectsShortNonOutsourced(boolean x, boolean y) {
        for (Project project : Project.projects) {
            while (project != null) {
                if (project.taken && project.owner.equals(this.name) && project.status.equals("W trakcie realizacji") && !project.isOutsourced)
                    System.out.println(project.toString(x, y));
                break;
            }
        }
    }

    //wypisanie wszystkich zatrudnionych Programistów (oprócz Podwykonawców!)

    public void printHiredCoders() {
        for (Coder coder : Coder.coders) {
            while (coder != null) {
                if (coder.isAssigned && coder.employerName.equals(this.name) && !coder.isOutsourcer)
                    System.out.println(coder);
                break;
            }
        }
    }

    //wypisanie wszystkich zatrudnionych Sprzedawców

    public void printHiredSalesPpl() {
        for (SalesPerson salesPerson : SalesPerson.salesPpl) {
            while (salesPerson != null) {
                if (salesPerson.isAssigned && salesPerson.employerName.equals(this.name))
                    System.out.println(salesPerson);
                break;
            }
        }
    }

    //wypisanie wszystkich zatrudnionych Testerów

    public void printHiredTesters() {
        for (Tester tester : Tester.testers) {
            while (tester != null) {
                if (tester.isAssigned && tester.employerName.equals(this.name)) System.out.println(tester);
                break;
            }
        }
    }

    //Dodawanie do bazy projektu POZYSKANEGO PRZED TWEGO SPRZEDAWCĘ

    public void generateAdditionalProjectThroughSalesPerson(){
        Project.projects[Project.currentProjectsNumber] = new Project(Project.complexityLvls[rand.nextInt(Project.complexityLvls.length)]);
        Project.projects[Project.currentProjectsNumber].projectID = Project.currentProjectsNumber;
        Project.projects[Project.currentProjectsNumber].whoObtained = this.name;
        Project.currentProjectsNumber++;
    }

    // automatyczna zmiana statusu projektu w przypadku jeśli zakończono realizację wszystkich technologii

    public void checkCompletedProjects() {
        for (Project project : Project.projects) {
            while (project != null) {
                if (project.taken && project.owner.equals(this.name) && project.status.equals("W trakcie realizacji") && project.frontEndDays.equals(0) && project.backEndDays.equals(0) && project.databaseDays.equals(0) && project.mobileDays.equals(0) && project.wordpressDays.equals(0) && project.prestaShopDays.equals(0)) {
                    System.out.println("\n>Zakończono pracę nad jednym z Twoich projektów: (" + project.projectID + "). " + project.projectName + "!\n" +
                            ">Gratulacje!\n");
                    project.status = "Gotowy do oddania Klientowi";
                    if (project.isOutsourced) {
                        Coder.coders[project.outsourcerId].isAssigned = false;
                        Coder.coders[project.outsourcerId].employerName = null;
                        Coder.coders[project.outsourcerId].projectIdIfOutsourced = null;
                        this.codersCount--;
                    }
                }
                break;
            }
        }
    }

    // Sprawdzanie czy Warunki zwycięstwa zostały spełnione

    public void winConditionCheck(){
        if(this.winConditionProjectsClosed >= 3 && this.winCoditionProjectsClosedWithSalesPersonAssigned >= 1 && this.cash > startingCash){
            this.winCondition = true;
            Main.winnerName = this.name;
        }
    }

    // metoda wykonywania codziennej Pracy przez zatrudnionych Pracowników (i sprawdzanie czy L4 wpadnie)

    public void makeWorkersWorkAgain() {
        for (Coder coder : Coder.coders) {
            while (coder != null) {

                //Zwykli Koderzy na Umowie o Prace

                if (coder.isAssigned && coder.employerName.equals(this.name) && !coder.isOutsourcer) {
                    if (rand.nextInt(100) < coder.sickChance) {
                        System.out.println("\n>" + coder.empName + " nie pojawił się dziś w pracy - L4.\n");
                    } else {
                        System.out.println("\t\nKtórym Projektem ma zająć się dziś " + coder.empName + "? (Podaj ID projektu)");

                        printOwnedActiveProjectsShort(x, y);

                        String input = in.nextLine();

                        if (Main.isNumeric(input)) {
                            int tempProjectID = Integer.parseInt(input);
                            boolean jobsDone = false;
                            if (Project.projects[tempProjectID].owner.equals(this.name) && Project.projects[tempProjectID].status.equals("W trakcie realizacji")) {
                                while (!jobsDone) {
                                    if (Project.projects[tempProjectID].frontEndDays > 0 && coder.frontEndSkill) {
                                        Project.projects[tempProjectID].frontEndDays--;
                                        if (rand.nextInt(100) < coder.fuckUpChance) {
                                            Project.projects[tempProjectID].fuckedUpCode = true;
                                            Project.projects[tempProjectID].fuckUpsCount++;
                                        }
                                        System.out.println("\n>" + coder.empName + " robił dziś front-End w ramach projektu o ID (" + tempProjectID + ");\n");
                                        jobsDone = true;
                                    } else if (Project.projects[tempProjectID].backEndDays > 0 && coder.backEndSkill) {
                                        Project.projects[tempProjectID].backEndDays--;
                                        if (rand.nextInt(100) < coder.fuckUpChance) {
                                            Project.projects[tempProjectID].fuckedUpCode = true;
                                            Project.projects[tempProjectID].fuckUpsCount++;
                                        }
                                        System.out.println("\n>" + coder.empName + " robił dziś Back-End w ramach projektu o ID (" + tempProjectID + ");\n");
                                        jobsDone = true;
                                    } else if (Project.projects[tempProjectID].databaseDays > 0 && coder.dataBaseSkill) {
                                        Project.projects[tempProjectID].databaseDays--;
                                        if (rand.nextInt(100) < coder.fuckUpChance) {
                                            Project.projects[tempProjectID].fuckedUpCode = true;
                                            Project.projects[tempProjectID].fuckUpsCount++;
                                        }
                                        System.out.println("\n>" + coder.empName + " robił dziś Bazy Danych w ramach projektu o ID (" + tempProjectID + ");\n");
                                        jobsDone = true;
                                    } else if (Project.projects[tempProjectID].mobileDays > 0 && coder.mobileSkill) {
                                        Project.projects[tempProjectID].mobileDays--;
                                        if (rand.nextInt(100) < coder.fuckUpChance) {
                                            Project.projects[tempProjectID].fuckedUpCode = true;
                                            Project.projects[tempProjectID].fuckUpsCount++;
                                        }
                                        System.out.println("\n>" + coder.empName + " robił dziś Mobile w ramach projektu o ID (" + tempProjectID + ");\n");
                                        jobsDone = true;
                                    } else if (Project.projects[tempProjectID].wordpressDays > 0 && coder.wordPressSkill) {
                                        Project.projects[tempProjectID].wordpressDays--;
                                        if (rand.nextInt(100) < coder.fuckUpChance) {
                                            Project.projects[tempProjectID].fuckedUpCode = true;
                                            Project.projects[tempProjectID].fuckUpsCount++;
                                        }
                                        System.out.println("\n>" + coder.empName + " robił dziś WordPressa w ramach projektu o ID (" + tempProjectID + ");\n");
                                        jobsDone = true;
                                    } else if (Project.projects[tempProjectID].prestaShopDays > 0 && coder.prestaShopSkill) {
                                        Project.projects[tempProjectID].prestaShopDays--;
                                        if (rand.nextInt(100) < coder.fuckUpChance) {
                                            Project.projects[tempProjectID].fuckedUpCode = true;
                                            Project.projects[tempProjectID].fuckUpsCount++;
                                        }
                                        System.out.println("\n>" + coder.empName + " robił dziś PrestaShop w ramach projektu o ID (" + tempProjectID + ");\n");
                                        jobsDone = true;
                                    } else {
                                        System.out.println("\n>" + coder.empName + " przy tym projekcie nie jest w stanie wykonać niczego sensownego...");
                                        jobsDone = true;
                                    }
                                }

                            } else {
                                System.out.println(">Podany numer ID projektu nie należy do Ciebie bądź jest już zamknięty! Spróbuj ponownie");
                            }

                        } else {
                            System.out.println("Numer ID projektu musi mieć postać numeryczną!");
                        }
                    }
                    //Podwykonawcy

                } else if (coder.isAssigned && coder.employerName.equals(this.name) && coder.isOutsourcer && coder.projectIdIfOutsourced != null) {
                    if (rand.nextInt(100) < coder.sickChance) {
                        System.out.println("\n>" + coder.empName + " nie pojawił się dziś w pracy - L4.\n");
                    } else {
                        boolean jobsDone = false;

                        while (!jobsDone) {
                            if (Project.projects[coder.projectIdIfOutsourced].frontEndDays > 0 && coder.frontEndSkill) {
                                Project.projects[coder.projectIdIfOutsourced].frontEndDays--;
                                if (rand.nextInt(100) < coder.fuckUpChance) {
                                    Project.projects[coder.projectIdIfOutsourced].fuckedUpCode = true;
                                    Project.projects[coder.projectIdIfOutsourced].fuckUpsCount++;
                                }
                                System.out.println("\n>" + coder.empName + " robił dziś front-End w ramach projektu o ID (" + coder.projectIdIfOutsourced + ");\n");
                                jobsDone = true;
                            } else if (Project.projects[coder.projectIdIfOutsourced].backEndDays > 0 && coder.backEndSkill) {
                                Project.projects[coder.projectIdIfOutsourced].backEndDays--;
                                if (rand.nextInt(100) < coder.fuckUpChance) {
                                    Project.projects[coder.projectIdIfOutsourced].fuckedUpCode = true;
                                    Project.projects[coder.projectIdIfOutsourced].fuckUpsCount++;
                                }
                                System.out.println("\n>" + coder.empName + " robił dziś Back-End w ramach projektu o ID (" + coder.projectIdIfOutsourced + ");\n");
                                jobsDone = true;
                            } else if (Project.projects[coder.projectIdIfOutsourced].databaseDays > 0 && coder.dataBaseSkill) {
                                Project.projects[coder.projectIdIfOutsourced].databaseDays--;
                                if (rand.nextInt(100) < coder.fuckUpChance) {
                                    Project.projects[coder.projectIdIfOutsourced].fuckedUpCode = true;
                                    Project.projects[coder.projectIdIfOutsourced].fuckUpsCount++;
                                }
                                System.out.println("\n>" + coder.empName + " robił dziś Bazy Danych w ramach projektu o ID (" + coder.projectIdIfOutsourced + ");\n");
                                jobsDone = true;
                            } else if (Project.projects[coder.projectIdIfOutsourced].mobileDays > 0 && coder.mobileSkill) {
                                Project.projects[coder.projectIdIfOutsourced].mobileDays--;
                                if (rand.nextInt(100) < coder.fuckUpChance) {
                                    Project.projects[coder.projectIdIfOutsourced].fuckedUpCode = true;
                                    Project.projects[coder.projectIdIfOutsourced].fuckUpsCount++;
                                }
                                System.out.println("\n>" + coder.empName + " robił dziś Mobile w ramach projektu o ID (" + coder.projectIdIfOutsourced + ");\n");
                                jobsDone = true;
                            } else if (Project.projects[coder.projectIdIfOutsourced].wordpressDays > 0 && coder.wordPressSkill) {
                                Project.projects[coder.projectIdIfOutsourced].wordpressDays--;
                                if (rand.nextInt(100) < coder.fuckUpChance) {
                                    Project.projects[coder.projectIdIfOutsourced].fuckedUpCode = true;
                                    Project.projects[coder.projectIdIfOutsourced].fuckUpsCount++;
                                }
                                System.out.println("\n>" + coder.empName + " robił dziś WordPressa w ramach projektu o ID (" + coder.projectIdIfOutsourced + ");\n");
                                jobsDone = true;
                            } else if (Project.projects[coder.projectIdIfOutsourced].prestaShopDays > 0 && coder.prestaShopSkill) {
                                Project.projects[coder.projectIdIfOutsourced].prestaShopDays--;
                                if (rand.nextInt(100) < coder.fuckUpChance) {
                                    Project.projects[coder.projectIdIfOutsourced].fuckedUpCode = true;
                                    Project.projects[coder.projectIdIfOutsourced].fuckUpsCount++;
                                }
                                System.out.println("\n>" + coder.empName + " robił dziś PrestaShop w ramach projektu o ID (" + coder.projectIdIfOutsourced + ");\n");
                                jobsDone = true;
                            } else {
                                System.out.println("\n>" + coder.empName + " przy tym projekcie nie jest w stanie wykonać niczego sensownego...");
                                jobsDone = true;
                            }
                        }

                    }
                }

                break;
            }
        }

        //Sprzedawcy

        for (SalesPerson salesPerson : SalesPerson.salesPpl) {
            while (salesPerson != null) {

                if (salesPerson.isAssigned && salesPerson.employerName.equals(this.name)) {
                    if (rand.nextInt(100) < salesPerson.sickChance) {
                        System.out.println("\n>" + salesPerson.empName + " nie pojawił się dziś w pracy - L4.\n");
                    } else {
                        salesPerson.workDayCounter++;
                        if (salesPerson.workDayCounter < 5) {
                            System.out.println("\n>" + salesPerson.empName + " szuka nowego potencjalnego zlecenie już od " + salesPerson.workDayCounter + " dni.\n");
                        } else {
                            generateAdditionalProjectThroughSalesPerson();
                            System.out.println("\n>" + salesPerson.empName + " znalazł dla Ciebie nowe potencjalne zlecenie do podjęcia!\n");
                            salesPerson.workDayCounter = 0;
                        }
                    }
                }
                break;
            }
        }

        //Testerzy

        if (this.codersCount != 0 && this.testersCount != 0 && this.codersCount / this.testersCount <= 3) {
            for (Project project : Project.projects) {
                while (project != null) {
                    if (project.taken && project.owner.equals(this.name) && ((project.status.equals("W trakcie realizacji") || (project.status.equals("Gotowy do oddania Klientowi"))))) {
                        project.fuckUpsCount = 0;
                        project.fuckedUpCode = false;
                    }
                    break;
                }
            }
            System.out.println(">Twoi testerzy wnikliwie przebadali cały kod i możesz mieć pewność, że nie oddasz Klientowi Projektu z błędami.\n");
        }
    }

}
