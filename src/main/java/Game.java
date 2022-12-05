import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static Scanner scanner = new Scanner(System.in);
    public static Field field;
    public static int bestScore;

    /**
     * Приветственная информация в консоль
     */
    private static void Greeting() {
        System.out.println("  _____  ________      ________ _____   _______     __");
        System.out.println(" |  __ \\|  ____\\ \\    / /  ____|  __ \\ / ____\\ \\   / /");
        System.out.println(" | |__) | |__   \\ \\  / /| |__  | |__) | (___  \\ \\_/ / ");
        System.out.println(" |  _  /|  __|   \\ \\/ / |  __| |  _  / \\___ \\  \\   /  ");
        System.out.println(" | | \\ \\| |____   \\  /  | |____| | \\ \\ ____) |  | |   ");
        System.out.println(" |_|  \\_\\______|   \\/   |______|_|  \\_\\_____/   |_|   ");
        System.out.println("enter to start");
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Меню
     */
    private static void Menu() {
        while (true) {
            System.out.println("MENU");
            System.out.println("Best score: " + bestScore);
            System.out.println("1. Против компьютера");
            System.out.println("2. 2 игрока");
            System.out.println("3. Выход");
            int choice = Integer.parseInt(scanner.next());
            if (choice != 1 && choice != 2 && choice != 3) {
                System.out.println("Такого варианта нет!");
            } else {
                switch (choice) {
                    case 1 -> playVsPC();
                    case 2 -> playVsPlayer();
                    case 3 -> {
                        return;
                    }
                }
            }
        }
    }
    /**
     * Режим игры против игрока
     */
    private static void playVsPlayer() {
        field = new Field();
        int x, y;
        String color = "Ч";
        while (true) {
            ArrayList<Point> moves = findAllMoves(color);
            if (moves.size() <= 0) {
                System.out.println("Вы пропускаете");
                if (color == "Ч") {
                    color = "Б";
                } else {
                    color = "Ч";
                }
                continue;
            }
            field.Score();
            field.Draw(moves);

            System.out.print("Доступные ходы: ");
            for (Point p : moves) {
                System.out.print((p.x + 1) + "," + (p.y + 1) + " ");
            }
            System.out.println();
            boolean correctInput = false;
            do {
                if (color == "Ч") {
                    System.out.println("Черные ходят:");
                } else {
                    System.out.println("Белые ходят");
                }
                System.out.println("Ход(\"x y\"): ");
                x = Integer.parseInt(scanner.next());
                y = Integer.parseInt(scanner.next());
                --x;
                --y;
                for (Point move : moves) {
                    if (move.x == x && move.y == y) {
                        correctInput = true;
                    }
                }
            } while (!correctInput);
            if (field.MoveAndCheck(x, y, color, true)) {
                return;
            }
            if (color == "Ч") {
                color = "Б";
            } else {
                color = "Ч";
            }
        }
    }
    /**
     * Режим игры против компьютера
     */
    private static void playVsPC() {
        boolean canGoBack = false;
        field = new Field();
        int x, y;
        String color = "Ч";
        String otherColor = "Б";
        while (true) {
            ArrayList<Point> moves = findAllMoves(color);
            field.Draw(moves);
            if (moves.size() <= 0) {
                System.out.println("Вы пропускаете");
            } else {
                System.out.print("Доступные ходы: ");
                for (Point p : moves) {
                    System.out.print((p.x + 1) + "," + (p.y + 1) + " ");
                }
                System.out.println();
                boolean correctInput = false;
                do {
                    if (canGoBack) {
                        System.out.println("\"0 0\" чтобы отменить ход");
                    }
                    System.out.println("Ваш ход(\"x y\"): ");
                    x = Integer.parseInt(scanner.next());
                    y = Integer.parseInt(scanner.next());
                    --x;
                    --y;
                    if (x == -1 && y == -1 && canGoBack) {
                        correctInput = true;
                        continue;
                    }
                    for (Point move : moves) {
                        if (move.x == x && move.y == y) {
                            correctInput = true;
                        }
                    }
                } while (!correctInput);
                if (x == -1 && y == -1 && canGoBack) {
                    field.goBack();
                    canGoBack = false;
                    continue;
                }
                field.saveField();
                if (field.MoveAndCheck(x, y, color, false)) {
                    System.out.println("Winner winner chicken dinner");
                    bestScore = field.Score();
                    return;
                }
                canGoBack = true;
            }
            field.Draw();
            Point pcMove = pcMove();
            if (pcMove == null) {
                System.out.println("Компьютер пропускает");
                continue;
            }
            if (field.MoveAndCheck(pcMove.x, pcMove.y, otherColor
                    , false)) {
                System.out.println("Прости, ты проиграл");
                field.Draw();
                return;
            }
        }
    }
    /**
     * Вычисление лучшего хода для компьютера
     */
    private static Point pcMove() {
        double max = -100;
        Point maxPoint = new Point(0, 0);
        ArrayList<Point> possibleMoves = findAllMoves("Б");
        if (possibleMoves.size() <= 0) {
            return null;
        }
        for (Point p : possibleMoves) {
            if (R(p, "Б") > max) {
                max = R(p, "Б");
                maxPoint = p;
            }
        }
        return maxPoint;
    }
    /**
     * Оценочная функция
     */
    private static double R(Point p, String color) {
        String otherColor;
        if (color == "Б") {
            otherColor = "Ч";
        } else {
            otherColor = "Б";
        }
        int x = p.x;
        int y = p.y;
        double maxR = -1;
        if (x + 1 < field.getSize() && field.At(x + 1, y) == color) {
            int k = 1;
            double ss = ss(x, y);
            while (x + k < field.getSize()) {
                if (field.At(x + k, y) == otherColor) {
                    ++k;
                } else if (field.At(x + k, y) == " ") {
                    break;
                } else {
                    int sum = 0;
                    for (int m = 1; m <= k; ++m) {
                        sum += s(x + m, y);
                    }
                    if (maxR < sum + ss) {
                        maxR = sum + ss;
                    }
                    break;
                }
            }
        }
        if (x - 1 >= 0 && field.At(x - 1, y) == color) {
            int k = 1;
            double ss = ss(x, y);
            while (x - k >= 0) {
                if (field.At(x - k, y) == otherColor) {
                    ++k;
                } else if (field.At(x - k, y) == " ") {
                    break;
                } else {
                    int sum = 0;
                    for (int m = 1; m <= k; ++m) {
                        sum += s(x - m, y);
                    }
                    if (maxR < sum + ss) {
                        maxR = sum + ss;
                    }
                    break;
                }
            }
        }
        if (y + 1 < field.getSize() && field.At(x, y + 1) == color) {
            int k = 1;
            double ss = ss(x, y);
            while (y + k < field.getSize()) {
                if (field.At(x, y + k) == otherColor) {
                    ++k;
                } else if (field.At(x, y + k) == " ") {
                    break;
                } else {
                    int sum = 0;
                    for (int m = 1; m <= k; ++m) {
                        sum += s(x, y + m);
                    }
                    if (maxR < sum + ss) {
                        maxR = sum + ss;
                    }
                    break;
                }
            }
        }
        if (y - 1 >= 0 && field.At(x, y - 1) == color) {
            int k = 1;
            double ss = ss(x, y);
            while (y - k >= 0) {
                if (field.At(x, y - k) == otherColor) {
                    ++k;
                } else if (field.At(x, y - k) == " ") {
                    break;
                } else {
                    int sum = 0;
                    for (int m = 1; m <= k; ++m) {
                        sum += s(x, y - m);
                    }
                    if (maxR < sum + ss) {
                        maxR = sum + ss;
                    }
                    break;
                }
            }
        }
        if (x + 1 < field.getSize() && y + 1 < field.getSize() && field.At(x + 1, y + 1) == color) {
            int k = 1;
            double ss = ss(x, y);
            while (x + k < field.getSize() && y + k < field.getSize()) {
                if (field.At(x + k, y + k) == otherColor) {
                    ++k;
                } else if (field.At(x + k, y + k) == " ") {
                    break;
                } else {
                    int sum = 0;
                    for (int m = 1; m <= k; ++m) {
                        sum += s(x + m, y + m);
                    }
                    if (maxR < sum + ss) {
                        maxR = sum + ss;
                    }
                    break;
                }
            }
        }
        if (x - 1 >= 0 && y - 1 >= 0 && field.At(x - 1, y - 1) == color) {
            int k = 1;
            double ss = ss(x, y);
            while (x - k >= 0 && y - k >= 0) {
                if (field.At(x - k, y - k) == otherColor) {
                    ++k;
                } else if (field.At(x - k, y - k) == " ") {
                    break;
                } else {
                    int sum = 0;
                    for (int m = 1; m <= k; ++m) {
                        sum += s(x - m, y - m);
                    }
                    if (maxR < sum + ss) {
                        maxR = sum + ss;
                    }
                    break;
                }
            }
        }
        if (x - 1 >= 0 && y + 1 < field.getSize() && field.At(x - 1, y + 1) == color) {
            int k = 1;
            double ss = ss(x, y);
            while (x - k >= 0 && y + k < field.getSize()) {
                if (field.At(x - k, y + k) == otherColor) {
                    ++k;
                } else if (field.At(x - k, y + k) == " ") {
                    break;
                } else {
                    int sum = 0;
                    for (int m = 1; m <= k; ++m) {
                        sum += s(x - m, y + m);
                    }
                    if (maxR < sum + ss) {
                        maxR = sum + ss;
                    }
                    break;
                }
            }
        }
        if (x + 1 < field.getSize() && y - 1 >= 0 && field.At(x + 1, y - 1) == color) {
            int k = 1;
            double ss = ss(x, y);
            while (x + k < field.getSize() && y - k >= 0) {
                if (field.At(x + k, y + k) == otherColor) {
                    ++k;
                } else if (field.At(x + k, y - k) == " ") {
                    break;
                } else {
                    int sum = 0;
                    for (int m = 1; m <= k; ++m) {
                        sum += s(x + m, y - m);
                    }
                    if (maxR < sum + ss) {
                        maxR = sum + ss;
                    }
                    break;
                }
            }
        }
        return maxR;
    }
    /**
     * Вычисление ss
     */
    private static double ss(int x, int y) {
        int borders = 0;
        if (x + 1 > field.getSize()) {
            ++borders;
        }
        if (y + 1 > field.getSize()) {
            ++borders;
        }
        if (x - 1 < 0) {
            ++borders;
        }
        if (y - 1 < 0) {
            ++borders;
        }
        if (borders == 2) {
            return 0.8;
        }
        if (borders == 1) {
            return 0.4;
        }
        return 0;
    }
    /**
     * Вычисление S
     */
    private static int s(int x, int y) {
        if (x + 1 > field.getSize()) {
            return 2;
        }
        if (y + 1 > field.getSize()) {
            return 2;
        }
        if (x - 1 < 0) {
            return 2;
        }
        if (y - 1 < 0) {
            return 2;
        }
        return 1;
    }
    /**
     * Нахождение всех возможных ходов
     */
    private static ArrayList<Point> findAllMoves(String color) {
        String otherColor;
        ArrayList<Point> res = new ArrayList<>();
        if (color == "Б") {
            otherColor = "Ч";
        } else {
            otherColor = "Б";
        }
        for (int i = 0; i < field.getSize(); ++i) {
            for (int j = 0; j < field.getSize(); ++j) {
                if (field.At(i, j) == color) {
                    if (i + 1 < field.getSize() && i + 1 >= 0 && field.At(i + 1, j) == otherColor) {
                        int k = 2;
                        while (i + k < field.getSize() && i + k >= 0) {
                            if (field.At(i + k, j) == otherColor) {
                                k++;
                            } else {
                                if (field.At(i + k, j) == " ") {
                                    res.add(new Point(i + k, j));
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if (i - 1 < field.getSize() && i - 1 >= 0 && field.At(i - 1, j) == otherColor) {
                        int k = 2;
                        while (i - k < field.getSize() && i - k >= 0) {
                            if (field.At(i - k, j) == otherColor) {
                                k++;
                            } else {
                                if (field.At(i - k, j) == " ") {
                                    res.add(new Point(i - k, j));
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if (j + 1 < field.getSize() && j + 1 >= 0 && field.At(i, j + 1) == otherColor) {
                        int k = 2;
                        while (j + k < field.getSize() && j + k >= 0) {
                            if (field.At(i, j + k) == otherColor) {
                                k++;
                            } else {
                                if (field.At(i, j + k) == " ") {
                                    res.add(new Point(i, j + k));
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if (j - 1 < field.getSize() && j - 1 >= 0 && field.At(i, j - 1) == otherColor) {
                        int k = 2;
                        while (j - k < field.getSize() && j - k >= 0) {
                            if (field.At(i, j - k) == otherColor) {
                                k++;
                            } else {
                                if (field.At(i, j - k) == " ") {
                                    res.add(new Point(i, j - k));
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if (j + 1 < field.getSize() && j + 1 >= 0 && i + 1 < field.getSize() && i + 1 >= 0
                            && field.At(i + 1, j + 1) == otherColor) {
                        int k = 2;
                        while (j + k < field.getSize() && j + k >= 0 && i + k < field.getSize() && i + k >= 0) {
                            if (field.At(i + k, j + k) == otherColor) {
                                k++;
                            } else {
                                if (field.At(i + k, j + k) == " ") {
                                    res.add(new Point(i + k, j + k));
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if (j - 1 < field.getSize() && j - 1 >= 0 && i - 1 < field.getSize() && i - 1 >= 0
                            && field.At(i - 1, j - 1) == otherColor) {
                        int k = 2;
                        while (j - k < field.getSize() && j - k >= 0 && i - k < field.getSize() && i - k >= 0) {
                            if (field.At(i - k, j - k) == otherColor) {
                                k++;
                            } else {
                                if (field.At(i - k, j - k) == " ") {
                                    res.add(new Point(i - k, j - k));
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if (j + 1 < field.getSize() && j + 1 >= 0 && i - 1 < field.getSize() && i - 1 >= 0
                            && field.At(i - 1, j + 1) == otherColor) {
                        int k = 2;
                        while (j + k < field.getSize() && j + k >= 0 && i - k < field.getSize() && i - k >= 0) {
                            if (field.At(i - k, j + k) == otherColor) {
                                k++;
                            } else {
                                if (field.At(i - k, j + k) == " ") {
                                    res.add(new Point(i - k, j + k));
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if (j - 1 < field.getSize() && j - 1 >= 0 && i + 1 < field.getSize() && i + 1 >= 0
                            && field.At(i + 1, j - 1) == otherColor) {
                        int k = 2;
                        while (j - k < field.getSize() && j - k >= 0 && i + k < field.getSize() && i + k >= 0) {
                            if (field.At(i + k, j - k) == otherColor) {
                                k++;
                            } else {
                                if (field.At(i + k, j - k) == " ") {
                                    res.add(new Point(i + k, j - k));
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return res;
    }
    /**
     * Запуск игры
     */
    public static void Launch() {
        Game.Greeting();
        Game.Menu();
    }
}
