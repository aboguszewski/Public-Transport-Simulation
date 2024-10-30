package symulacja;

import java.util.Arrays;

public class TablicowaKolejka extends KolejkaZdarzeń {
    private Zdarzenie[] zdarzenia;
    private int wypełnionaDo;
    private int aktualneZdarzenie;

    public TablicowaKolejka() {
        zdarzenia = new Zdarzenie[0];
        wypełnionaDo = -1;
        aktualneZdarzenie = 0;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i <= wypełnionaDo; i++) {
            string.append(zdarzenia[i]);
            string.append(System.lineSeparator());
        }
        return string.toString();
    }

    @Override
    public void dodaj(Zdarzenie zdarzenie) {
        if (wypełnionaDo + 1 >= zdarzenia.length) {
            zdarzenia = Arrays.copyOf(zdarzenia, zdarzenia.length * 2 + 1);
        }

        Zdarzenie[] noweZdarzenia = new Zdarzenie[zdarzenia.length];

        int idx = 0;
        while (idx <= wypełnionaDo && zdarzenie.po(zdarzenia[idx])) {
            noweZdarzenia[idx] = zdarzenia[idx];
            idx++;
        }

        noweZdarzenia[idx++] = zdarzenie;
        wypełnionaDo++;
        while (idx - 1 < wypełnionaDo) {
            noweZdarzenia[idx] = zdarzenia[idx - 1];
            idx++;
        }

        zdarzenia = noweZdarzenia;
    }

    @Override
    public Zdarzenie pobierz() {
        return zdarzenia[aktualneZdarzenie++];
    }

    @Override
    public boolean pusta() {
        return aktualneZdarzenie > wypełnionaDo;
    }

}

