package symulacja;

import java.util.Arrays;

public class Kolejka <T> {
    private T[] elementy;
    private int wypełnionaDo;
    private int aktualnyElement;

    public Kolejka() {
        elementy = (T[]) new Object[0];
        wypełnionaDo = -1;
        aktualnyElement = 0;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i <= wypełnionaDo; i++) {
            string.append(elementy[i]);
            string.append(System.lineSeparator());
        }
        return string.toString();
    }

    public void dodaj(T element) {
        if (++wypełnionaDo == elementy.length) {
            elementy = Arrays.copyOf(elementy, elementy.length * 2 + 1);
        }

        elementy[wypełnionaDo] = element;
    }

    public T pobierz() {
        return elementy[aktualnyElement++];
    }

    public boolean pusta() {
        return aktualnyElement > wypełnionaDo;
    }

    public int długość() {
        return wypełnionaDo - aktualnyElement + 1;
    }
}
