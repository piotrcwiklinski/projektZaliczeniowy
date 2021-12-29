import java.util.Scanner;
import java.time.LocalDate;

public class Main {


    public static boolean isNumeric(String input) {
        if (input == null) {
            return false;
        }
        try {
            double i = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //Obiekty Systemowe
    static Scanner in = new Scanner(System.in);

    //Przygotowanie zasobów nowej rozgrywki

    public static boolean gameRunning = true;
    final static LocalDate startDate = LocalDate.parse("2020-01-01");
    public static LocalDate currentDate = startDate;


    public static void main(String[] args) {

        //Początek rozgrywki

        System.out.println("Witamy w grze \"Appstore\"!\n");

        //Ustalenie liczby graczy

        System.out.println("Ile osób dzisiaj zagra?");

        String input = in.nextLine();

        while (!isNumeric(input)) {
            System.out.println("Nieprawidłowa ilość graczy! Spróbuj ponownie");
            input = in.nextLine();
        }
        Player[] players = new Player[Integer.parseInt(input)];

        for (int i = 0; i < players.length; i++) {
            System.out.println("Witamy gracza numer " + (i + 1) + ". Jak masz na imię?");
            input = in.nextLine();
            players[i] = new Player(input);
        }

        System.out.println("\nWszyscy gracze na pokładzie. Zaczynamy rozgrywkę!");

        Project.generateStartProjects(1);

        //Główna pętla gry

        GAME:
        while (gameRunning) {
            for (Player player : players) {
                player.doTurn();
            }

            Integer aliveChecker = 0;
            for (Player player : players)
                if (player.alive) {
                    aliveChecker++;
                }

            if (aliveChecker > 0) {
                currentDate = currentDate.plusDays(1);
                System.out.println(
                                "\n----------------------------------------------------------------------------------" +
                                "\n>Upłynął kolejny dzień. Dzisiaj mamy " + currentDate + ".");

            } else {
                System.out.println("\nWszyscy gracze zakończyli rozgrywkę.\n");
                gameRunning = false;
            }
        }

        System.out.println("\t######################");
        System.out.println("\t# DZIĘKUJEMY ZA GRĘ! #");
        System.out.println("\t#       Autor:       #");
        System.out.println("\t#  Piotr Cwikliński  #");
        System.out.println("\t######################");
    }
}
