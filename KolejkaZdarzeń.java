package symulacja;

public abstract class KolejkaZdarzeń {
    public abstract void dodaj(Zdarzenie zdarzenie);
    public abstract Zdarzenie pobierz();
    public abstract boolean pusta();
}
