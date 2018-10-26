import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        NZlotowka[] case1 = {
                new NZlotowka(5, true),
                new NZlotowka(5, true),
                new NZlotowka(5, true),
                new NZlotowka(10, false)
        };

        NZlotowka[] case2 = {
                new NZlotowka(10, true),
                new NZlotowka(5, true),
                new NZlotowka(20, false)
        };

        NZlotowka[] case3 = {
                new NZlotowka(50, true),
                new NZlotowka(10, false)
        };


        Kasjer kasjer = new Kasjer();

        NZlotowka[] reszta = kasjer.kup(case3, 25);

        Arrays.asList(reszta).forEach(r -> System.out.println("Zlotowkao wartosci: " + r.wartosc + ", rozmienialna: " + r.zlotowkaNierozmienialna));
    }
}
