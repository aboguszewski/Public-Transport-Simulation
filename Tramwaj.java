package symulacja;

public class Tramwaj extends Pojazd {

    public Tramwaj(int linia, Trasa trasa, int nrBoczny, int pojemność) {
        super(linia, trasa, nrBoczny, pojemność);
    }

    @Override
    public String typ() {
        return "Tramwaj";
    }
}
