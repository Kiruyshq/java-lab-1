import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: нужно ввести целое число");
            }
        }
    }

    static Complex readComplex(String prompt) {
        while (true) {
            System.out.print(prompt);
            String[] parts = scanner.nextLine().trim().replace(',', '.').split("\\s+");
            try {
                if (parts.length == 1) {
                    return new Complex(Double.parseDouble(parts[0]), 0);
                }
                if (parts.length == 2) {
                    return new Complex(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Ошибка: введите одно число (3) или два через пробел (3 -2 означает 3-2i)");
        }
    }

    static ComplexMatrix readMatrix(String name) {
        int rows = readInt("Число строк: ");
        int cols = readInt("Число столбцов: ");
        ComplexMatrix m = new ComplexMatrix(rows, cols);

        System.out.println("Введите элементы: n или n k (=n-ki), где n и k - действительные числа");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.set(i, j, readComplex(name + "[" + (i + 1) + "][" + (j + 1) + "] = "));
            }
        }
        System.out.println("Матрица " + name + ":");
        m.print();
        return m;
    }

    public static void main(String[] args) {
        ComplexMatrix a = null;
        ComplexMatrix b = null;

        while (true) {
            System.out.println("\n[1] ввести м-цу A   [2] ввести м-цу B   [3] A + B   [4] A * B   [5] A / B");
            System.out.println("[6] транспонировать A   [7] определитель A   [8] транспонировать B   [9] определитель B   [0] выход");
            try {
                int choice = readInt("Выберите пункт: ");
                System.out.println();
                if (choice == 0) {
                    break;
                }
                switch (choice) {
                    case 1:
                        a = readMatrix("A");
                        break;
                    case 2:
                        b = readMatrix("B");
                        break;
                    case 3:
                        if (a == null || b == null) {
                            System.out.println("Сначала введите матрицы A и B");
                        } else {
                            ComplexMatrix sum = a.add(b);
                            System.out.println("A + B =");
                            sum.print();
                        }
                        break;
                    case 4:
                        if (a == null || b == null) {
                            System.out.println("Сначала введите матрицы A и B");
                        } else {
                            ComplexMatrix product = a.multiply(b);
                            System.out.println("A * B =");
                            product.print();
                        }
                        break;
                    case 5:
                        if (a == null || b == null) {
                            System.out.println("Сначала введите матрицы A и B");
                        } else {
                            ComplexMatrix quotient = a.divide(b);
                            System.out.println("A / B =");
                            quotient.print();
                        }
                        break;
                    case 6:
                        if (a == null) {
                            System.out.println("Сначала введите матрицу A");
                        } else {
                            System.out.println("Транспонированная A:");
                            a.transpose().print();
                        }
                        break;
                    case 7:
                        if (a == null) {
                            System.out.println("Сначала введите матрицу A");
                        } else {
                            System.out.println("Определитель A = " + a.determinant());
                        }
                        break;
                    case 8:
                        if (b == null) {
                            System.out.println("Сначала введите матрицу B");
                        } else {
                            System.out.println("Транспонированная B:");
                            b.transpose().print();
                        }
                        break;
                    case 9:
                        if (b == null) {
                            System.out.println("Сначала введите матрицу B");
                        } else {
                            System.out.println("Определитель B = " + b.determinant());
                        }
                        break;
                    default:
                        System.out.println("Нет такого пункта");
                }
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}
