package symulacja;

import java.util.Arrays;
import java.util.Scanner;

public class Symulacja {
    private final Dzień[] dni;
    private final Przystanek[] przystanki;
    private final Pasażer[] pasażerowie;
    private final Tramwaj[] tramwaje;
    private final Linia[] linie;
    private KolejkaZdarzeń kolejkaZdarzeń;
    private int łączniePrzejazdów;
    private int średniCzasOczekiwania;

    public static Symulacja nowa() {
        Scanner parametry = new Scanner(System.in);

        int ileDni = parametry.nextInt();
        Dzień[] dni = new Dzień[ileDni];
        for (int nrDnia = 0; nrDnia < ileDni; nrDnia++) {
            dni[nrDnia] = new Dzień();
        }

        int pojemnośćPrzystanku = parametry.nextInt();

        int ilePrzystanków = parametry.nextInt();
        Przystanek[] przystanki = new Przystanek[ilePrzystanków];
        for (int nrPrzystanku = 0; nrPrzystanku < ilePrzystanków; nrPrzystanku++) {
            String nazwa = parametry.next();
            przystanki[nrPrzystanku] = new Przystanek(nazwa, pojemnośćPrzystanku);
        }

        int ilePasażerów = parametry.nextInt();
        Pasażer[] pasażerowie = new Pasażer[ilePasażerów];
        for (int idPasażera = 0; idPasażera < ilePasażerów; idPasażera++) {
            Przystanek losowy = przystanki[Losowanie.losuj(0, ilePrzystanków - 1)];
            pasażerowie[idPasażera] = new Pasażer(idPasażera, losowy);
        }

        int pojemnośćTramwaju = parametry.nextInt();

        int ileLinii = parametry.nextInt();
        Linia[] linie = new Linia[ileLinii];

        int nrBoczny = 0;
        Tramwaj[] tramwaje = new Tramwaj[0];
        for (int linia = 0; linia < ileLinii; linia++) {
            int ileTramwajów = parametry.nextInt();

            int długośćTrasy = parametry.nextInt();
            Przystanek[] naTrasie = new Przystanek[długośćTrasy];
            int[] czasyPrzejazdów = new int[długośćTrasy];

            for (int nrNaTrasie = 0; nrNaTrasie < długośćTrasy - 1; nrNaTrasie++) {
                String nazwa = parametry.next();
                int dojazdDoNastępnego = parametry.nextInt();

                naTrasie[nrNaTrasie] = przystanek(nazwa, przystanki);
                czasyPrzejazdów[nrNaTrasie] = dojazdDoNastępnego;
            }
            naTrasie[długośćTrasy - 1] = przystanek(parametry.next(), przystanki);
            czasyPrzejazdów[długośćTrasy - 1] = 0;

            int postójNaPętli = parametry.nextInt();

            Trasa trasa = new Trasa(naTrasie, czasyPrzejazdów, postójNaPętli);

            linie[linia] = new Linia(new Tramwaj[ileTramwajów], trasa);

            tramwaje = Arrays.copyOf(tramwaje, tramwaje.length + ileTramwajów);
            for (int nowych = 0; nowych < ileTramwajów; nowych++) {
                tramwaje[nrBoczny] = new Tramwaj(linia, trasa, nrBoczny, pojemnośćTramwaju);
                linie[linia].dodaj(tramwaje[nrBoczny], nowych);
                nrBoczny++;
            }
        }

        parametry.close();
        return new Symulacja(dni, przystanki, pasażerowie, tramwaje, linie);
    }

    private static Przystanek przystanek(String nazwa, Przystanek[] przystanki) {
        for (Przystanek przystanek : przystanki) {
            if (przystanek.czyTo(nazwa)) {
                return przystanek;
            }
        }
        return null;
    }

    private Symulacja(Dzień[] dni, Przystanek[] przystanki, Pasażer[] pasażerowie, Tramwaj[] tramwaje, Linia[] linie) {
        this.dni = dni;
        this.przystanki = przystanki;
        this.pasażerowie = pasażerowie;
        this.tramwaje = tramwaje;
        kolejkaZdarzeń = new TablicowaKolejka();
        łączniePrzejazdów = 0;
        średniCzasOczekiwania = 0;
        this.linie = linie;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("Symulacja").append(System.lineSeparator());
        string.append("dni: ").append(dni.length).append(System.lineSeparator());
        string.append("pasażerów: ").append(pasażerowie.length).append(System.lineSeparator());
        string.append("przystanki: ").append(System.lineSeparator());
        for (Przystanek przystanek : przystanki) {
            string.append("\t").append(przystanek).append(System.lineSeparator());
        }
        string.append("linii: ").append(linie.length).append(System.lineSeparator());
        string.append("tramwajów: ").append(tramwaje.length);

        return string.toString();
    }

    public void uruchom() {
        StringBuilder przebieg = new StringBuilder();

        for (int i = 0; i < dni.length; i++) {
            symulujDzień(i, przebieg);
            reset();
        }

        String przebiegTekst = przebieg.toString();

        System.out.println("Przebieg symulacji");
        System.out.print(przebiegTekst);
        obliczStatystyki(przebiegTekst);

        System.out.println("-- Statystyki --");
        System.out.println("Łączna liczba przejazdów: " + łączniePrzejazdów);
        System.out.println("Średni czas oczekiwania: " + średniCzasOczekiwania);
        System.out.println("Poszczególne dni:");
        for (int i = 0; i < dni.length; i++) {
            System.out.println("\tDzień " + i + ": " + dni[i]);
        }
    }

    private void symulujDzień(int dzień, StringBuilder przebieg) {
        kolejkaZdarzeń = new TablicowaKolejka();
        pasażerowieNaPrzystanki(dzień);
        wyjazdTramwajów(dzień);
        while (!kolejkaZdarzeń.pusta()) {
            Zdarzenie zdarzenie = kolejkaZdarzeń.pobierz();
            String zapis = zdarzenie.wykonaj(kolejkaZdarzeń);
            if (zapis != null) {
                przebieg.append(zapis);
            }
        }
    }

    private void pasażerowieNaPrzystanki(int dzień) {
        for (Pasażer pasażer : pasażerowie) {
            Czas czas = new Czas(Losowanie.losuj(6, 12), Losowanie.losuj(0, 59));
            Zdarzenie zdarzenie = new PrzyszedłNaPrzystanek(pasażer, dzień, czas);
            kolejkaZdarzeń.dodaj(zdarzenie);
        }
    }

    private void wyjazdTramwajów(int dzień) {
        for (Linia linia : linie) {
            linia.zaplanujWyjazdy(dzień, kolejkaZdarzeń);
        }
    }

    private void reset() {
        for (Przystanek przystanek : przystanki) {
            przystanek.reset();
        }

        for (Pasażer pasażer : pasażerowie) {
            pasażer.reset();
        }

        for (Tramwaj tramwaj : tramwaje) {
            tramwaj.reset();
        }
    }

    private void obliczStatystyki(String przebieg) {
        obliczStatystykiDzienne(przebieg);

        int ilePrzejazdów = 0;
        int ileŁącznieCzekali = 0;
        int ileOczekiwań = 0;

        for (Dzień dzień : dni) {
            ilePrzejazdów += dzień.ilePrzejazdów();
            ileŁącznieCzekali += dzień.ileŁącznieCzekali();
            ileOczekiwań += dzień.ileOczekiwań();
        }

        łączniePrzejazdów = ilePrzejazdów;
        if (ileOczekiwań != 0) {
            średniCzasOczekiwania = ileŁącznieCzekali / ileOczekiwań;
        }
    }

    private void obliczStatystykiDzienne(String przebieg) {
        Scanner zapis = new Scanner(przebieg);

        Czas[] odKtórejCzeka = new Czas[pasażerowie.length];

        int aktualnyDzień = 0;

        while (zapis.hasNextLine()) {
            String zdarzenie = zapis.nextLine();

            if (!zdarzenie.contains("Pasażer")) {
                continue;
            }

            Scanner wZdarzeniu = new Scanner(zdarzenie);

            String dzieńString = wZdarzeniu.next();
            dzieńString = dzieńString.substring(0, dzieńString.length() - 1);
            int dzień = Integer.parseInt(dzieńString);

            if (dzień != aktualnyDzień) {
                for (Czas czas : odKtórejCzeka) {
                    if (czas != null) {
                        dni[aktualnyDzień].dodajCzasOczekiwania(czas.różnicaGodzina(24, 0));
                    }
                }

                Arrays.fill(odKtórejCzeka, null);
                aktualnyDzień = dzień;
            }

            String czasString = wZdarzeniu.next();
            czasString = czasString.substring(0, czasString.length() - 1);
            Czas czas = Czas.zTekstu(czasString);

            wZdarzeniu.next();

            int idPasażera = wZdarzeniu.nextInt();

            if (zdarzenie.contains("przyszedł") || zdarzenie.contains("wysiadł")) {
                odKtórejCzeka[idPasażera] = czas;
                dni[dzień].dodajOczekiwanie();
            }

            else if (zdarzenie.contains("wsiadł")) {
                dni[dzień].dodajPrzejazd();

                int ileCzekał = odKtórejCzeka[idPasażera].różnica(czas);
                dni[dzień].dodajCzasOczekiwania(ileCzekał);

                odKtórejCzeka[idPasażera] = null;
            }

            wZdarzeniu.close();
        }

        zapis.close();
    }

}