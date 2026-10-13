public class ComplexMatrix {
    private int rows;
    private int cols;
    private Complex[][] data;

    public ComplexMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Размеры матрицы должны быть больше нуля");
        }
        this.rows = rows;
        this.cols = cols;
        data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new Complex(0, 0);
            }
        }
    }

    public void set(int i, int j, Complex value) {
        data[i][j] = value;
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException("Сложить можно только матрицы одинакового размера");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = data[i][j].add(other.data[i][j]);
            }
        }
        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (cols != other.rows) {
            throw new IllegalArgumentException(
                    "Умножить можно, только если число столбцов первой матрицы равно числу строк второй");
        }
        ComplexMatrix result = new ComplexMatrix(rows, other.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < cols; k++) {
                    sum = sum.add(data[i][k].multiply(other.data[k][j]));
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        if (other.rows != other.cols) {
            throw new IllegalArgumentException("Делить можно только на квадратную матрицу");
        }
        return multiply(other.inverse());
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = data[i][j];
            }
        }
        return result;
    }

    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Определитель есть только у квадратной матрицы");
        }
        if (rows == 1) {
            return data[0][0];
        }
        Complex result = new Complex(0, 0);
        for (int j = 0; j < cols; j++) {
            Complex term = data[0][j].multiply(minor(0, j).determinant());
            if (j % 2 == 1) {
                term = term.multiply(new Complex(-1, 0));
            }
            result = result.add(term);
        }
        return result;
    }

    public ComplexMatrix inverse() {
        Complex det = determinant();
        if (det.isZero()) {
            throw new IllegalArgumentException("Определитель равен нулю, обратной матрицы нет");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        if (rows == 1) {
            result.data[0][0] = new Complex(1, 0).divide(det);
            return result;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Complex c = minor(i, j).determinant();
                if ((i + j) % 2 == 1) {
                    c = c.multiply(new Complex(-1, 0));
                }
                result.data[j][i] = c.divide(det);
            }
        }
        return result;
    }

    private ComplexMatrix minor(int row, int col) {
        ComplexMatrix result = new ComplexMatrix(rows - 1, cols - 1);
        int r = 0;
        for (int i = 0; i < rows; i++) {
            if (i == row) {
                continue;
            }
            int c = 0;
            for (int j = 0; j < cols; j++) {
                if (j == col) {
                    continue;
                }
                result.data[r][c] = data[i][j];
                c++;
            }
            r++;
        }
        return result;
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
