package symulacja;

public class Pasażer {
    private final int id;
    private final Przystanek domowy;
    private Przystanek stoiNa;
    private Przystanek chceDojechać;

    public Pasażer(int id, Przystanek przystanek) {
        this.id = id;
        domowy = przystanek;
        stoiNa = null;
        chceDojechać = null;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public String idźNaPrzystanek() {
        if (domowy.jestMiejsce()) {
            domowy.dodaj(this);
            stoiNa = domowy;
            return "Pasażer " + id + " przyszedł na przystanek " + domowy + System.lineSeparator();
        }

        return null;
    }

    public String wysiądź(Przystanek przystanek) {
        przystanek.dodaj(this);
        stoiNa = przystanek;
        return "Pasażer " + id + " wysiadł na przystanku " + stoiNa + System.lineSeparator();
    }

    public String wsiądźDo(Pojazd pojazd) {
        pojazd.wpuśćPasażera(this);

        Przystanek[] doWyboru = pojazd.przystankiNaTrasie();
        chceDojechać = doWyboru[Losowanie.losuj(0, doWyboru.length - 1)];

        return "Pasażer " + id + " wsiadł do " + pojazd + System.lineSeparator();
    }

    public boolean chceWysiąść(Przystanek przystanek) {
        return chceDojechać == przystanek;
    }

    public void reset() {
        stoiNa = null;
        chceDojechać = null;
    }
}
