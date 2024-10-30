package symulacja;

public class Trasa {
    private final Przystanek[] przystanki;
    private final int[] czasyPrzejazdów;
    private final int postójNaPętli;

    public Trasa(Przystanek[] przystanki, int[] czasyPrzejazdów, int postójNaPętli) {
        this.przystanki = przystanki;
        this.czasyPrzejazdów = czasyPrzejazdów;
        this.postójNaPętli = postójNaPętli;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Przystanek przystanek : przystanki) {
            string.append(przystanek).append(" ");
        }
        return string.toString();
    }

    public final int długość() {
        return przystanki.length;
    }

    public Przystanek początek() {
        return przystanki[0];
    }

    public Przystanek koniec() {
        return przystanki[przystanki.length - 1];
    }

    public int czasPrzejazdu() {
        int czas = 0;
        for (int czasPrzejazdu : czasyPrzejazdów) {
            czas += czasPrzejazdu;
        }
        czas += postójNaPętli * 2;
        return czas;
    }

    public Przystanek następny(Przystanek aktualny, int kierunek) {
        if (zostajeNaPętli(aktualny, kierunek)) {
            return null;
        }

        int idx = któryNaTrasie(aktualny);

        if (kierunek == 0) {
            return przystanki[--idx];
        } else {
            return przystanki[++idx];
        }
    }

    public int następnyCzas(Przystanek aktualny, int kierunek) {
        if (aktualny == null) {
            return postójNaPętli;
        }

        if (zostajeNaPętli(aktualny, kierunek)) {
            return 0;
        }

        int idx = któryNaTrasie(aktualny);
        if (kierunek == długość() - 1) {
            return czasyPrzejazdów[idx];
        } else {
            return czasyPrzejazdów[idx - 1];
        }


    }

    private int któryNaTrasie(Przystanek przystanek) {
        int idx = 0;
        while (przystanki[idx] != przystanek) {
            idx++;
        }
        return idx;
    }

    private boolean zostajeNaPętli(Przystanek przystanek, int kierunek) {
        return (przystanek == koniec() && kierunek == długość() - 1) || (przystanek == początek() && kierunek == 0);
    }

    public Przystanek[] przystankiDoPętli(Przystanek przystanek, int kierunek) {
        if (zostajeNaPętli(przystanek, kierunek)) {
            Przystanek[] pętla = new Przystanek[1];
            pętla[0] = przystanek;
            return pętla;
        }

        int aktualny = któryNaTrasie(przystanek);

        Przystanek[] doPętli;
        if (kierunek == długość() - 1) {
            doPętli = new Przystanek[długość() - 1 - aktualny];
            for (int idx = 0; idx < doPętli.length; idx++) {
                doPętli[idx] = przystanki[++aktualny];
            }
        } else {
            doPętli = new Przystanek[aktualny];
            for (int idx = 0; idx < doPętli.length; idx++) {
                doPętli[idx] = przystanki[--aktualny];
            }
        }

        return doPętli;
    }

}
