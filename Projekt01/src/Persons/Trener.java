package Persons;

import java.time.LocalDate;
import java.time.Period;

public class Trener
        // dziedziczy po klasie recepcjonista i implementuje interfejs IDobryPracownik
        extends Pracownik
        implements IDobryPracownik {
    // pole dodatkowe specjalizacja
    private String specjalizacja;

    // konstruktor obiektu trener
    public Trener(String imie, String nazwisko, String dataUrodzenia, String dzial, String spcjalizacja) {
        // kontruktor klasy bazowej
        super(imie, nazwisko, dataUrodzenia, dzial, "T");
        this.specjalizacja = spcjalizacja;
    }
    // getter do specjalizacji
    public String getSpcjalizacja() {
        return specjalizacja;
    }

    // metody do nadpisania po implementacji interfejsu dobrypracownik
    @Override
    public boolean zdolnyDoPracy() {
        return this.czyZdrowy();
    }

    @Override
    public String informacje() {
        return "Wyświetlono informacje :: " + "Trener [" + this.id + "] " + this.getImie() + " " +
                this.getNazwisko() + " " +this.wiekPracownika() + " lat, będący - " + this.specjalizacja;
    }

    @Override
    public int wiekPracownika() {
        return Period.between(getDataUrodzenia(), LocalDate.now()).getYears();
    }
}
