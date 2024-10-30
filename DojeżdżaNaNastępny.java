package symulacja;

public class DojeżdżaNaNastępny extends Zdarzenie {
    private final Pojazd pojazd;

    public DojeżdżaNaNastępny(int dzień, Czas czas, Pojazd pojazd) {
        super(dzień, czas);
        this.pojazd = pojazd;
    }

    @Override
    public String wykonaj(KolejkaZdarzeń kolejka) {
        String zapis = pojazd.jedźDoNastępnego(dzień(), czas(), kolejka);
        if (zapis != null) {
            return data() + zapis;
        }
        return null;
    }

    @Override
    public String opis() {
        return pojazd + " dojeżdża na następny przystanek na jego trasie";
    }


}
