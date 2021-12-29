import java.util.Random;
import java.util.Scanner;

public class Player {

    public String name;
    public Boolean alive;
    public Integer cash;
    public String activeProject;
    public Integer startingCash = 20000;
    public Integer jobSearchDays;
    public boolean x = true;

    Player(String name) {
        this.name = name;
        this.alive = true;
        this.cash = startingCash;
        this.jobSearchDays = 0;
    }

    Scanner in = new Scanner(System.in);
    Random rand = new Random();

    public void doTurn() {

        System.out.println("----------------------------------------------------------------------------------");

        boolean actionChosen = false;

        if (this.alive) {
            System.out.println("\n\tWitaj " + this.name + "!");

            Menu:
            while (!actionChosen) {
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

                        switch (input) {
                            case "1":
                                System.out.println("\n\tLISTA DOSTĘPNYCH PROJEKTÓW DO REALIZACJI:");
                                Project.printActiveProjects();
                                System.out.println("\n\tKTÓRY PROJEKT WYBIERASZ? (Podaj ID Ogłoszenia lub \"stop\" by wrócić do Menu)");

                                input = in.nextLine();

                                if(Main.isNumeric(input)){
                                    try {
                                        Project.projects[Integer.parseInt(input)].owner = this.name;
                                        Project.projects[Integer.parseInt(input)].taken = true;
                                        Project.projects[Integer.parseInt(input)].status = "W trakcie realizacji";
                                        Project.projects[Integer.parseInt(input)].deadlineDate = Main.currentDate.plusDays(Project.projects[Integer.parseInt(input)].deadlineDays);
                                        System.out.println("Udało się pozyskać projekt nr. " + Integer.parseInt(input) + " do realizacji!");
                                        actionChosen = true;
                                    } catch (Exception exc)
                                    {
                                        System.out.println("Nie ma takiego projektu! Spróbuj ponownie");
                                    }
                                } else if(input.equals("stop")) continue Menu;
                                break;

                            case "2":
                                System.out.println("Czy na pewno chcesz poświęcić ten dzień na szukanie nowych Zleceń do realizacji? (Wpisz \"tak\" by potwierdzić)");

                                input = in.nextLine();

                                if (input.equals("tak")) {
                                    System.out.println("Poświęciłeś dzień na szukanie na rynku nowych Zleceń do realizacji.");
                                    this.jobSearchDays++;
                                    if(this.jobSearchDays < 5){
                                        System.out.println("Do tej pory szukałeś nowego zlecenie łącznie " + this.jobSearchDays + " dni.");
                                        System.out.println("Pamiętaj, że każde kolejne 5 dni poświęconych na ten cel to gwarancja jednego nowego zlecenia!");
                                    } else {
                                        Project.generateAdditionalProject();
                                        System.out.println("Szukałeś nowego zlecenia przez " + jobSearchDays + " dni. Opłaciło Ci się! Dodano nowy Projekt dostępny do podjęcia!");
                                        this.jobSearchDays = 0;
                                    }
                                    actionChosen = true;
                                }
                                break;

                            case "3":
                                System.out.println("\n\tKTÓRYM PROJEKTEM CHCESZ SIĘ DZIS ZAJĄC? (Podaj ID Projektu lub \"stop\" by wrócić do Menu)");
                                printOwnedProjects(x);

                                input = in.nextLine();

                                if(Main.isNumeric(input)){
                                    try {
                                        if(Project.projects[Integer.parseInt(input)].owner == this.name){
                                        Integer tempProjectID = Integer.parseInt(input);
                                        System.out.println("\n\tKtórą technologię chcesz dziś Programować w ramach tego projektu?\n");
                                        if(Project.projects[tempProjectID].frontEndDays > 0){
                                            System.out.println(">>front-End (wpisz \"fe\")");
                                        }
                                        if(Project.projects[tempProjectID].backEndDays > 0) {
                                            System.out.println(">>back-End (wpisz \"be\")");
                                        }
                                        if(Project.projects[tempProjectID].databaseDays > 0) {
                                            System.out.println(">>Bazy Danych (wpisz \"bd\")");
                                        }
                                        if(Project.projects[tempProjectID].wordpressDays > 0) {
                                            System.out.println(">>WordPress (wpisz \"wp\")");
                                        }
                                        if(Project.projects[tempProjectID].prestaShopDays > 0) {
                                            System.out.println(">>PrestaShop (wpisz \"ps\")");
                                        }

                                        input = in.nextLine();

                                        switch (input) {
                                            case "fe":
                                                Project.projects[tempProjectID].frontEndDays--;
                                                System.out.println("Udało Ci się poświęcić dzisiejszy dzień na Programowanie wybranego projektu!");
                                                actionChosen = true;
                                                break;

                                            case "be":
                                                Project.projects[tempProjectID].backEndDays--;
                                                System.out.println("Udało Ci się poświęcić dzisiejszy dzień na Programowanie wybranego projektu!");
                                                actionChosen = true;
                                                break;

                                            case "bd":
                                                Project.projects[tempProjectID].databaseDays--;
                                                System.out.println("Udało Ci się poświęcić dzisiejszy dzień na Programowanie wybranego projektu!");
                                                actionChosen = true;
                                                break;

                                            case "wp":
                                                Project.projects[tempProjectID].wordpressDays--;
                                                System.out.println("Udało Ci się poświęcić dzisiejszy dzień na Programowanie wybranego projektu!");
                                                actionChosen = true;
                                                break;

                                            case "ps":
                                                Project.projects[tempProjectID].prestaShopDays--;
                                                System.out.println("Udało Ci się poświęcić dzisiejszy dzień na Programowanie wybranego projektu!");
                                                actionChosen = true;
                                                break;

                                            default:
                                                System.out.println("\tNieprawidłowy wybór!");
                                                break;
                                        }

                                    } else {
                                            System.out.println("Podany numer ID projektu nie należy do Ciebie! Spróbuj ponownie");
                                            continue Menu;
                                        }
                                    } catch (Exception exc) {
                                        System.out.println("Podano nieprawidłowy numer ID projektu! Spróbuj ponownie");
                                    }

                                } else if(input.equals("stop")) continue Menu;
                                break;

                            case "4":
                                break;

                            case "5":
                                break;

                            case "6":
                                break;

                            case "7":
                                break;

                            case "8":
                                break;

                            case "9":
                                continue Menu;

                            default:
                                System.out.println("\tNieprawidłowy wybór!");
                                break;
                        }

                        break;

                    case "2":
                        System.out.println("\tTwoje aktualnie dostępne środki: " + this.cash + " zł.");
                        break;

                    case "3":
                        System.out.println("\n\tSTATUS REALIZACJI TWOICH PROJEKTÓW:");
                        printOwnedProjects(x);
                        break;

                    case "4":
                        System.out.println("\n\tLISTA DOSTĘPNYCH PROJEKTÓW DO REALIZACJI:");
                        Project.printActiveProjects();
                        break;

                    case "5":
                        System.out.println("Placeholder");
                        break;

                    case "0":
                        System.out.println("Czy na pewno chcesz się poddać? (Wpisz \"tak\" by potwierdzić)");

                        input = in.nextLine();

                        if (input.equals("tak")) {
                            System.out.println("Zakończyłeś rozgrywkę. Zapraszamy ponownie!");
                            this.alive = false;
                            actionChosen = true;
                        }
                        break;

                    default:
                        System.out.println("\tNieprawidłowy wybór!");
                        break;
                }
            }
        }

    }

    //wypisanie wszystkich projektów pobranych przez danego gracza

    public void printOwnedProjects(boolean x) {
        for (Project project : Project.projects) {
            while(project != null) {
                if (project.taken && project.owner.equals(this.name)) System.out.println(project.toString(x));
                break;
            }
        }
    }


}
