package symulacja;

public class PrzyszedłNaPrzystanek extends Zdarzenie {
    private final Pasażer pasażer;

    public PrzyszedłNaPrzystanek(Pasażer pasażer, int dzień, Czas czas) {
        super(dzień, czas);
        this.pasażer = pasażer;
    }

    @Override
    public String wykonaj(KolejkaZdarzeń kolejka) {
        String zapis = pasażer.idźNaPrzystanek();
        if (zapis != null) {
            return data() + zapis;
        }
        return null;
    }

    @Override
    public String opis() {
        return "Pasażer " + pasażer + " idzie na swój przystanek";
    }



}
