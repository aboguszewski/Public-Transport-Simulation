package symulacja;

import java.util.Arrays;

public abstract class Pojazd {
    private final int linia;
    private final Trasa trasa;
    private final int nrBoczny;
    private Przystanek aktualny;
    private int kierunek;
    private final Pasażer[] pasażerowie;
    private int ilePasażerów;

    protected Pojazd(int linia, Trasa trasa, int nrBoczny, int pojemność) {
        this.linia = linia;
        this.trasa = trasa;
        this.nrBoczny = nrBoczny;
        kierunek = trasa.długość() - 1;
        aktualny = null;
        pasażerowie = new Pasażer[pojemność];
        ilePasażerów = 0;
    }

    @Override
    public String toString() {
        return typ() + " linii " + linia + " (numer boczny: " + nrBoczny + ")" ;
    }

    public abstract String typ();

    public Przystanek następny() {
        if (aktualny == null) {
            if (kierunek == 0) {
                kierunek = trasa.długość() - 1;
                return trasa.początek();
            } else {
                kierunek = 0;
                return trasa.koniec();
            }
        }

        return trasa.następny(aktualny, kierunek);
    }

    public String jedźDoNastępnego(int dzień, Czas czas, KolejkaZdarzeń kolejka) {
        if (aktualny == null && kierunek == 0 && czas.późniejNiżGodzina(23,1)) {
            return null;
        }

        aktualny = następny();

        if (aktualny != null) {
            wysadźPasażerów(kolejka, dzień, czas, aktualny);
            wpuśćPasażerów(kolejka, dzień, czas, aktualny);
        }

        Czas następnyDojazdCzas = czas.późniejszyO(trasa.następnyCzas(aktualny, kierunek));
        Zdarzenie następnyDojazd = new DojeżdżaNaNastępny(dzień, następnyDojazdCzas, this);

        kolejka.dodaj(następnyDojazd);

        if (aktualny == null) {
            return null;
        } else {
            return this + " przyjechał na przystanek " + aktualny + System.lineSeparator();
        }
    }

    private void wysadźPasażerów(KolejkaZdarzeń kolejka, int dzień, Czas czas, Przystanek przystanek) {
        while (ilePasażerów > 0 && przystanek.jestMiejsce()) {
            int wysiadający = znajdźWysiadającego(przystanek);
            if (wysiadający == -1) {
                break;
            }

            Zdarzenie wysiada = new WysiadaNa(dzień, czas, pasażerowie[wysiadający], przystanek);
            kolejka.dodaj(wysiada);

            pasażerowie[wysiadający] = null;
            ilePasażerów--;
        }
    }

    private void wpuśćPasażerów(KolejkaZdarzeń kolejka, int dzień, Czas czas, Przystanek przystanek) {
        while (!przystanek.pusty() && ilePasażerów < pasażerowie.length) {
            Pasażer wsiadający = przystanek.pobierz();

            Zdarzenie wsiada = new WsiadaDo(dzień, czas, wsiadający, this);
            kolejka.dodaj(wsiada);
        }
    }

    public String wyjedźNaTrasę(int dzień, Czas czas, KolejkaZdarzeń kolejka) {
        aktualny = trasa.początek();

        wpuśćPasażerów(kolejka, dzień, czas, aktualny);

        Czas następnyDojazdCzas = czas.późniejszyO(trasa.następnyCzas(aktualny, kierunek));
        Zdarzenie następnyDojazd = new DojeżdżaNaNastępny(dzień, następnyDojazdCzas, this);

        kolejka.dodaj(następnyDojazd);

        return this + " stanął na początku trasy - " + aktualny + System.lineSeparator();
    }

    private int znajdźWysiadającego(Przystanek przystanek) {
        for (int który = 0; który < pasażerowie.length; który++) {
            if (pasażerowie[który] != null && pasażerowie[który].chceWysiąść(przystanek)) {
                return który;
            }
        }

        return -1;
    }

    public void wpuśćPasażera(Pasażer pasażer) {
        for (int miejsce = 0; miejsce < pasażerowie.length; miejsce++) {
            if (pasażerowie[miejsce] == null) {
                pasażerowie[miejsce] = pasażer;
                break;
            }
        }

        ilePasażerów++;
    }

    public Przystanek[] przystankiNaTrasie() {
        return trasa.przystankiDoPętli(aktualny, kierunek);
    }

    public void reset() {
        kierunek = trasa.długość() - 1;
        aktualny = null;
        wyrzućPasażerów();
        ilePasażerów = 0;
    }

    private void wyrzućPasażerów() {
        Arrays.fill(pasażerowie, null);
    }
}
