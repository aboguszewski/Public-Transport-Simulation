package symulacja;

public class Dzień {
    private int ilePrzejazdów;
    private int ileŁącznieCzekali;
    private int ileOczekiwań;

    public Dzień() {
        ilePrzejazdów = 0;
        ileŁącznieCzekali = 0;
        ileOczekiwań = 0;
    }

    @Override
    public String toString() {
        return "Łącznie przejazdów: " + ilePrzejazdów + ", Łączny czas oczekiwania: " + ileŁącznieCzekali;
    }

    public void dodajOczekiwanie() {
        ileOczekiwań++;
    }

    public void dodajPrzejazd() {
        ilePrzejazdów++;
    }

    public void dodajCzasOczekiwania(int minut) {
        ileŁącznieCzekali += minut;
    }

    public int ileŁącznieCzekali() {
        return ileŁącznieCzekali;
    }

    public int ilePrzejazdów() {
        return ilePrzejazdów;
    }

    public int ileOczekiwań() {
        return ileOczekiwań;
    }
}
