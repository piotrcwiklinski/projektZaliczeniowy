import java.util.Random;
import java.util.Scanner;

public class Player {

    String name;
    Boolean alive;
    Integer cash;
    String activeProject;

    Player(String name) {
        this.name = name;
        this.alive = true;
        this.cash = 20000;
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
                System.out.println("\t3. Sprawdź stan realizacji poszczególnych projektów");
                System.out.println("\t4. Przejrzyj dostępne projekty");
                System.out.println("\t5. Przejrzyj dostępnych Pracowników");
                System.out.println("\t0. Zakończ grę");

                String input = in.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("\n\tCo chcesz dziś zrobić?");
                        System.out.println("\t1. Podpisz umowę na nowy projekt (z dostępnych projektów)");
                        System.out.println("\t2. Szukaj Klientów");
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
                                        System.out.println("Udało się pozyskać projekt nr. " + Integer.parseInt(input) + " do realizacji!");
                                        actionChosen = true;
                                    } catch (Exception exc)
                                    {
                                        System.out.println("Nie ma takiego projektu! Spróbuj ponownie");
                                    }
                                } else if(input.equals("stop")) continue Menu;
                                break;

                            case "2":
                                break;

                            case "3":
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
                        System.out.println("\tTwoje środki: " + this.cash + " zł.");
                        break;

                    case "3":
                        System.out.println("Placeholder");
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


}
