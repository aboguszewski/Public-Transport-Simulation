package symulacja;

public class WysiadaNa extends Zdarzenie {
    private final Pasażer pasażer;
    private final Przystanek przystanek;


    public WysiadaNa(int dzień, Czas czas, Pasażer pasażer, Przystanek przystanek) {
        super(dzień, czas);
        this.pasażer = pasażer;
        this.przystanek = przystanek;
    }

    @Override
    public String wykonaj(KolejkaZdarzeń kolejka) {
        return data() + pasażer.wysiądź(przystanek);
    }

    @Override
    public String opis() {
        return "Pasażer " + pasażer + " wysiada na przystanku " + przystanek;
    }
}
