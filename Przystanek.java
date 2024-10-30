package symulacja;

public class Przystanek {
    private final String nazwa;
    private final int pojemność;
    private Kolejka<Pasażer> kolejka;

    public Przystanek(String nazwa, int pojemność) {
        this.nazwa = nazwa;
        this.pojemność = pojemność;
        kolejka = new Kolejka<Pasażer>();
    }

    @Override
    public String toString() {
        return nazwa;
    }

    public boolean czyTo(String nazwa) {
        return this.nazwa.equals(nazwa);
    }

    public boolean jestMiejsce() {
        return kolejka.długość() < pojemność;
    }

    public void dodaj(Pasażer pasażer) {
        kolejka.dodaj(pasażer);
    }

    public boolean pusty() {
        return kolejka.pusta();
    }

    public Pasażer pobierz() {
        return kolejka.pobierz();
    }

    public void reset() {
        kolejka = new Kolejka<Pasażer>();
    }
}
