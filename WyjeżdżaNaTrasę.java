package symulacja;

public class WyjeżdżaNaTrasę extends Zdarzenie {
    private final Pojazd pojazd;

    public WyjeżdżaNaTrasę(int dzień, Czas czas, Pojazd pojazd) {
        super(dzień, czas);
        this.pojazd = pojazd;
    }

    @Override
    public String wykonaj(KolejkaZdarzeń kolejka) {
        return data() + pojazd.wyjedźNaTrasę(dzień(), czas(), kolejka);
    }

    @Override
    public String opis() {
        return pojazd + "wyjeżdża na początek trasy";
    }
}
