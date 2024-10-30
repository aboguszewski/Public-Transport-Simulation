package symulacja;

public class WsiadaDo extends Zdarzenie {
    private final Pasażer pasażer;
    private final Pojazd pojazd;

    public WsiadaDo(int dzień, Czas czas, Pasażer pasażer, Pojazd pojazd) {
        super(dzień, czas);
        this.pasażer = pasażer;
        this.pojazd = pojazd;
    }

    @Override
    public String wykonaj(KolejkaZdarzeń kolejka) {
        return data() + pasażer.wsiądźDo(pojazd);
    }

    @Override
    public String opis() {
        return "Pasażer " + pasażer + " wsiada do " + pojazd;
    }
}
