import java.util.Random;
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


    public static void main(String[] args) {

        //System Objects
        Scanner in = new Scanner(System.in);

        //Przygotowanie nowej rozgrywki

        boolean gameRunning = true;
        final LocalDate startDate = LocalDate.parse("2020-01-01");
        LocalDate currentDate = startDate;
        Project.generateStartProjects(1);

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


        //Główna pętla gry

        GAME:
        while (gameRunning) {
            for (Player player : players) {
                player.doTurn();
            }

            Integer winChecker = 0;
            for (Player player : players)
                if (player.alive) {
                    winChecker++;
                }

            if (winChecker > 0) {
                currentDate = currentDate.plusDays(1);
                System.out.println(">Upłynął kolejny dzień. Dzisiaj mamy " + currentDate + ".");


            } else {
                gameRunning = false;
            }
        }

        System.out.println("######################");
        System.out.println("# DZIĘKUJEMY ZA GRĘ! #");
        System.out.println("######################");
    }
}
