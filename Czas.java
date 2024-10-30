package symulacja;

public class Czas {
    private final int godzina;
    private final int minuta;

    public Czas(int godzina, int minuta) {
        this.godzina = godzina;
        this.minuta = minuta;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        if (godzina < 10) {
            string.append("0");
        }
        string.append(godzina);
        string.append(":");
        if (minuta < 10) {
            string.append("0");
        }
        string.append(minuta);
        return string.toString();
    }

    public static Czas zTekstu(String tekst) {
        String godzina = tekst.substring(0,2);
        String minuta = tekst.substring(3);

        return new Czas(Integer.parseInt(godzina), Integer.parseInt(minuta));
    }

    public boolean późniejNiż(Czas czas) {
        return !czas.późniejNiżGodzina(godzina, minuta);
    }

    public boolean późniejNiżGodzina(int godzina, int minuta) {
        int taGodzina = this.godzina * 100 + this.minuta;
        int drugaGodzina = godzina * 100 + minuta;

        return taGodzina >= drugaGodzina;
    }

    public Czas późniejszyO(int minut) {
        int nowaGodzina = godzina + ((minuta + minut) / 60);
        int nowaMinuta = (minuta + minut) % 60;

        return new Czas(nowaGodzina, nowaMinuta);
    }

    public int różnica(Czas drugi) {
        return drugi.różnicaGodzina(godzina, minuta);
    }

    public int różnicaGodzina(int godzina, int minuta) {
        int wMinutach1 = this.godzina * 60 + this.minuta;
        int wMinutach2 = godzina * 60 + minuta;

        return Math.abs(wMinutach1 - wMinutach2);
    }
}

