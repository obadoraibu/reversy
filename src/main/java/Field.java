import java.util.ArrayList;

public class Field {
    private int size = 8;
    public int getSize() {
        return size;
    }
    private static String[][] data;
    private static String[][] buffer;
    public String At(int x, int y) {
        return data[x][y];
    }
    /**
     * Конструктор поля
     */
    public Field() {
        data = new String[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (i == size / 2 - 1 && j == size / 2 - 1 || i == size / 2 && j == size / 2) {
                    data[i][j] = "Б";
                } else if (i == size / 2 - 1 && j == size / 2 || i == size / 2 && j == size / 2 - 1) {
                    data[i][j] = "Ч";
                } else {
                    data[i][j] = " ";
                }
            }
        }
    }
    /**
     * Совершение хода и проверка вин-кондишна
     */
    public boolean MoveAndCheck(int x, int y, String color, boolean pvp) {
        data[x][y] = color;
        String otherColor;
        if (color == "Б") {
            otherColor = "Ч";
        } else {
            otherColor = "Б";
        }

        if (x + 1 < size && x + 1 >= 0 && data[x + 1][y] == otherColor) {
            int k = 1;
            while (x + k < size && x + k >= 0) {
                if (data[x + k][y] == otherColor) {
                    k++;
                } else {
                    if (data[x + k][y] == " ") {
                        break;
                    } else {
                        for (int m = 1; m <= k; ++m) {
                            data[x + m][y] = color;
                        }
                        break;
                    }
                }
            }
        }
        if (x - 1 < size && x - 1 >= 0 && data[x - 1][y] == otherColor) {
            int k = 1;
            while (x - k < size && x - k >= 0) {
                if (data[x - k][y] == otherColor) {
                    k++;
                } else {
                    if (data[x - k][y] == " ") {
                        break;
                    } else {
                        for (int m = 1; m <= k; ++m) {
                            data[x - m][y] = color;
                        }
                        break;
                    }
                }
            }
        }
        if (y + 1 < size && y + 1 >= 0 && data[x][y + 1] == otherColor) {
            int k = 1;
            while (y + k < size && y + k >= 0) {
                if (data[x][y + k] == otherColor) {
                    k++;
                } else {
                    if (data[x][y + k] == " ") {
                        break;
                    } else {
                        for (int m = 1; m <= k; ++m) {
                            data[x][y + m] = color;
                        }
                        break;
                    }
                }
            }
        }
        if (y - 1 < size && y - 1 >= 0 && data[x][y - 1] == otherColor) {
            int k = 1;
            while (y - k < size && y - k >= 0) {
                if (data[x][y - k] == otherColor) {
                    k++;
                } else {
                    if (data[x][y - k] == " ") {
                        break;
                    } else {
                        System.out.println(k);
                        for (int m = 1; m <= k; ++m) {
                            data[x][y - m] = color;
                        }
                        break;
                    }
                }
            }
        }
        if (y + 1 < size && y + 1 >= 0 && x + 1 < size && x + 1 >= 0 && data[x + 1][y + 1] == otherColor) {
            int k = 1;
            while (x + k < size && x + k >= 0 && y + k < size && y + k >= 0) {
                if (data[x + k][y + k] == otherColor) {
                    k++;
                } else {
                    if (data[x + k][y + k] == " ") {
                        break;
                    } else {
                        for (int m = 1; m <= k; ++m) {
                            data[x + m][y + m] = color;
                        }
                        break;
                    }
                }
            }
        }
        if (y - 1 < size && y - 1 >= 0 && x - 1 < size && x - 1 >= 0 && data[x - 1][y - 1] == otherColor) {
            int k = 1;
            while (x - k < size && x - k >= 0 && y - k < size && y - k >= 0) {
                if (data[x - k][y - k] == otherColor) {
                    k++;
                } else {
                    if (data[x - k][y - k] == " ") {
                        break;
                    } else {
                        for (int m = 1; m <= k; ++m) {
                            data[x - m][y - m] = color;
                        }
                        break;
                    }
                }
            }
        }
        if (y + 1 < size && y + 1 >= 0 && x - 1 < size && x - 1 >= 0 && data[x - 1][y + 1] == otherColor) {
            int k = 1;
            while (x - k < size && x - k >= 0 && y + k < size && y + k >= 0) {
                if (data[x - k][y + k] == otherColor) {
                    k++;
                } else {
                    if (data[x - k][y + k] == " ") {
                        break;
                    } else {
                        for (int m = 1; m <= k; ++m) {
                            data[x - m][y + m] = color;
                        }
                        break;
                    }
                }
            }
        }
        if (y - 1 < size && y - 1 >= 0 && x + 1 < size && x + 1 >= 0 && data[x + 1][y - 1] == otherColor) {
            int k = 1;
            while (x + k < size && x + k >= 0 && y - k < size && y - k >= 0) {
                if (data[x + k][y - k] == otherColor) {
                    k++;
                } else {
                    if (data[x + k][y - k] == " ") {
                        break;
                    } else {
                        for (int m = 1; m <= k; ++m) {
                            data[x + m][y - m] = color;
                        }
                        break;
                    }
                }
            }
        }

        boolean finished = true;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (data[i][j] == " ") {
                    finished = false;
                }
            }
        }
        if (finished && pvp) {
            int black = 0;
            int white = 0;
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    if (data[i][j] == "Ч") {
                        ++black;
                    } else if (data[i][j] == "Б") {
                        ++white;
                    }
                }
            }
            if (black > white) {
                System.out.println("Черные побеждают!");
            } else {
                System.out.println("Белые побеждают!");
            }
            Score();
        }
        return finished;
    }
    /**
     * Рисование поля по массиву data
     */
    public void Draw() {
            System.out.printf("     1       2       3       4       5       6       7       8       \n" +
                    "   _____   _____   _____   _____   _____   _____   _____   _____\n" +
                    "  |     | |     | |     | |     | |     | |     | |     | |     | \n" +
                    "1 |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  |\n" +
                    "  |_____| |_____| |_____| |_____| |_____| |_____| |_____| |_____|\n" +
                    "   _____   _____   _____   _____   _____   _____   _____   _____\n" +
                    "  |     | |     | |     | |     | |     | |     | |     | |     | \n" +
                    "2 |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  |\n" +
                    "  |_____| |_____| |_____| |_____| |_____| |_____| |_____| |_____|\n" +
                    "   _____   _____   _____   _____   _____   _____   _____   _____\n" +
                    "  |     | |     | |     | |     | |     | |     | |     | |     | \n" +
                    "3 |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  |\n" +
                    "  |_____| |_____| |_____| |_____| |_____| |_____| |_____| |_____|\n" +
                    "   _____   _____   _____   _____   _____   _____   _____   _____\n" +
                    "  |     | |     | |     | |     | |     | |     | |     | |     | \n" +
                    "4 |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  |\n" +
                    "  |_____| |_____| |_____| |_____| |_____| |_____| |_____| |_____|\n" +
                    "   _____   _____   _____   _____   _____   _____   _____   _____\n" +
                    "  |     | |     | |     | |     | |     | |     | |     | |     | \n" +
                    "5 |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  |\n" +
                    "  |_____| |_____| |_____| |_____| |_____| |_____| |_____| |_____|\n" +
                    "   _____   _____   _____   _____   _____   _____   _____   _____\n" +
                    "  |     | |     | |     | |     | |     | |     | |     | |     | \n" +
                    "6 |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  |\n" +
                    "  |_____| |_____| |_____| |_____| |_____| |_____| |_____| |_____|\n" +
                    "   _____   _____   _____   _____   _____   _____   _____   _____\n" +
                    "  |     | |     | |     | |     | |     | |     | |     | |     | \n" +
                    "7 |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  |\n" +
                    "  |_____| |_____| |_____| |_____| |_____| |_____| |_____| |_____|\n" +
                    "   _____   _____   _____   _____   _____   _____   _____   _____\n" +
                    "  |     | |     | |     | |     | |     | |     | |     | |     | \n" +
                    "8 |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  | |  %s  |\n" +
                    "  |_____| |_____| |_____| |_____| |_____| |_____| |_____| |_____|\n", data[0][0], data[0][1], data[0][2], data[0][3], data[0][4], data[0][5], data[0][6], data[0][7],
                    data[1][0], data[1][1], data[1][2], data[1][3], data[1][4], data[1][5], data[1][6], data[1][7],
                    data[2][0], data[2][1], data[2][2], data[2][3], data[2][4], data[2][5], data[2][6], data[2][7],
                    data[3][0], data[3][1], data[3][2], data[3][3], data[3][4], data[3][5], data[3][6], data[3][7],
                    data[4][0], data[4][1], data[4][2], data[4][3], data[4][4], data[4][5], data[4][6], data[4][7],
                    data[5][0], data[5][1], data[5][2], data[5][3], data[5][4], data[5][5], data[5][6], data[5][7],
                    data[6][0], data[6][1], data[6][2], data[6][3], data[6][4], data[6][5], data[6][6], data[6][7],
                    data[7][0], data[7][1], data[7][2], data[7][3], data[7][4], data[7][5], data[7][6], data[7][7]);
    }
    /**
     * Рисование поля по массиву data + потенциальные ходы
     */
    public void Draw(ArrayList<Point> moves) {
        for (Point move : moves) {
            data[move.x][move.y] = "!";
        }
        Draw();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (data[i][j] != "Ч" && data[i][j] != "Б") {
                    data[i][j] = " ";
                }
            }
        }
    }
    /**
     * Вывод очков
     */
    public int Score() {
        int black = 0;
        int white = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (data[i][j] == "Ч") {
                    ++black;
                } else if (data[i][j] == "Б") {
                    ++white;
                }
            }
        }
        System.out.println("Черные: " + black);
        System.out.println("Белые: " + white);
        return Math.max(black, white);
    }

    public void goBack() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                data[i][j] = buffer[i][j];
            }
        }
    }

    public void saveField() {
        buffer = new String[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                buffer[i][j] = data[i][j];
            }
        }
    }
}
