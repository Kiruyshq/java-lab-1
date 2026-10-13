public class Complex {
    private double re;
    private double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex add(Complex other) {
        return new Complex(re + other.re, im + other.im);
    }

    public Complex multiply(Complex other) {
        return new Complex(re * other.re - im * other.im,
                           re * other.im + im * other.re);
    }

    public Complex divide(Complex other) {
        double d = other.re * other.re + other.im * other.im;
        if (d == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Complex((re * other.re + im * other.im) / d,
                           (im * other.re - re * other.im) / d);
    }

    public boolean isZero() {
        return Math.abs(re) < 1e-9 && Math.abs(im) < 1e-9;
    }

    @Override
    public String toString() {
        double r = Math.round(re * 100) / 100.0;
        double i = Math.round(im * 100) / 100.0;
        if (i < 0) {
            return r + "-" + (-i) + "i";
        }
        return r + "+" + i + "i";
    }
}
