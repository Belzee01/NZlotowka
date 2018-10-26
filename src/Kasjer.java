import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Kasjer {

    private static final List<Integer> DENOMINATIONS = Arrays.asList(500, 200, 100, 50, 20, 10, 5, 2, 1);

    public NZlotowka[] kup(NZlotowka[] pieniadze, int kwotaDoZaplaty) {
        List<NZlotowka> wynikPracy = new ArrayList<>();

        NZlotowka[] money = Arrays.copyOf(pieniadze, pieniadze.length);

        List<NZlotowka> unchangeable = Stream.of(money).filter(m -> m.zlotowkaNierozmienialna).collect(Collectors.toList());
        List<NZlotowka> normal = Stream.of(money).filter(m -> !m.zlotowkaNierozmienialna).collect(Collectors.toList());

        sort(unchangeable);
        sort(normal);

        for (int i = unchangeable.size() - 1; i >= 0; i--) {
            if (kwotaDoZaplaty >= unchangeable.get(i).wartosc) {
                kwotaDoZaplaty = kwotaDoZaplaty - unchangeable.get(i).wartosc;
            }
        }

        for (int i = normal.size() - 1; i >= 0; i--) {
            kwotaDoZaplaty = kwotaDoZaplaty - normal.get(i).wartosc;
            if (kwotaDoZaplaty <= 0)
                break;
        }

        if (kwotaDoZaplaty > 0) {
            for (NZlotowka anUnchangeable : unchangeable) {
                kwotaDoZaplaty = kwotaDoZaplaty - anUnchangeable.wartosc;

                if (kwotaDoZaplaty <= 0) {
                    wynikPracy.add(anUnchangeable);
                    break;
                }
            }
        }

        wynikPracy.addAll(evaluateDenomination(kwotaDoZaplaty * (-1)));

        return wynikPracy.toArray(new NZlotowka[0]);
    }

    private static void sort(List<NZlotowka> nZlotowkas) {
        NZlotowka temp;
        int change = 1;
        while (change > 0) {
            change = 0;
            for (int i = 0; i < nZlotowkas.size() - 1; i++) {
                if (nZlotowkas.get(i).wartosc > nZlotowkas.get(i + 1).wartosc) {
                    temp = nZlotowkas.get(i + 1);
                    nZlotowkas.set(i + 1, nZlotowkas.get(i));
                    nZlotowkas.set(i, temp);
                    change++;
                }
            }
        }
    }

    private static List<NZlotowka> evaluateDenomination(int cost) {
        List<NZlotowka> money = new ArrayList<>();
        int rest = cost;
        for (Integer d : DENOMINATIONS) {
            int size = (rest / d);
            rest = rest % d;
            if (size > 0)
                money.addAll(createMoneyWithDenomination(size, d));
            if (rest == 0)
                break;
        }
        return money;
    }

    private static List<NZlotowka> createMoneyWithDenomination(int size, int denomination) {
        List<NZlotowka> zlotowkas = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            NZlotowka zlotowka = new NZlotowka();
            zlotowka.wartosc = denomination;
            zlotowka.zlotowkaNierozmienialna = false;
            zlotowkas.add(zlotowka);
        }
        return zlotowkas;
    }
}
