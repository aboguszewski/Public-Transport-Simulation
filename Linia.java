package symulacja;

public class Linia {
    private final Pojazd[] pojazdy;
    private final Trasa trasa;

    public Linia(Pojazd[] pojazd, Trasa trasa) {
        this.pojazdy = pojazd;
        this.trasa = trasa;
    }

    public void dodaj(Pojazd pojazd, int który) {
        pojazdy[który] = pojazd;
    }

    public void zaplanujWyjazdy(int dzień, KolejkaZdarzeń kolejka) {
        int odstępCzasu = trasa.czasPrzejazdu() / pojazdy.length;

        Czas czas = new Czas(6, 0);
        for (Pojazd pojazd : pojazdy) {
            Zdarzenie zdarzenie = new WyjeżdżaNaTrasę(dzień, czas, pojazd);
            kolejka.dodaj(zdarzenie);

            czas = czas.późniejszyO(odstępCzasu);
            if (czas.późniejNiżGodzina(23, 0)) {
                break;
            }
        }

    }
}
