package symulacja;

public abstract class Zdarzenie {
    private final int dzień;
    private final Czas czas;

    protected Zdarzenie(int dzień, Czas czas) {
        this.dzień = dzień;
        this.czas = czas;
    }

    @Override
    public String toString() {
        return czas + ": " + opis();
    }

    public abstract String wykonaj(KolejkaZdarzeń kolejka);
    public abstract String opis();

    public boolean po(Zdarzenie zdarzenie) {
        if (zdarzenie == null) {
            return false;
        }

        return !zdarzenie.poGodzinie(czas);
    }

    public boolean poGodzinie(Czas czas) {
        return this.czas.późniejNiż(czas);
    }

    protected String data() {
        return dzień + ", " + czas + ": ";
    }

    protected int dzień() {
        return dzień;
    }

    protected Czas czas() {
        return czas;
    }
}
