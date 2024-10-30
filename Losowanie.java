package symulacja;

import java.util.Random;

public abstract class Losowanie {

    public static int losuj(int dolna, int gorna) {
        Random maszynaLosująca = new Random();
        return maszynaLosująca.nextInt(dolna, gorna + 1);
    }

}
