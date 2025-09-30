package Persons;

import Logs.Logger;
import Team.Zadanie;
import Team.Zespol;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Manager
        // dziedziczy po klasie recepcjonista i implementuje interfejs IDobryPracownik
        extends Recepcjonista
        implements IDobryPracownik{
    // prywatne listy zadan i zespołów
    private final List<Zadanie> zadanie;
    private final List<Zespol> zespol;

    // konstruktor menadżera - wywołujący konstruktor recepcjonisty
    public Manager(String imie, String nazwisko, String dataUrodzenia, String dzial, String login, String haslo) {
        super(imie, nazwisko, dataUrodzenia, dzial, login, haslo, "M");
        zadanie = new ArrayList<>();
        zespol = new ArrayList<>();
    }

    // dodawanie zadania do listy
    public void addZadanie(Zadanie zadanie) {
        if(zadanie != null){
            System.out.println("Zadanie " + zadanie.getID() + " dodane do menagera " + this.id);
            Logger.getLogger().log("Zadanie " + zadanie.getID() + " dodane do menagera " + this.id);
            this.zadanie.add(zadanie);
        }else{
            Logger.getLogger().log("Błędne zadanie - nie dodano menagera " + this.id);
            throw new IllegalArgumentException("Błędne zadanie - nie dodano");
        }
    }
    // dodawanie zespołu do listy
    public void addZespol(Zespol zespol) {
        if(zespol != null){
            System.out.println("Dodano zespół " + this.id + " do menagera " + this.id);
            Logger.getLogger().log("Dodano zespół " + this.id + " do menagera " + this.id);
            this.zespol.add(zespol);
        }else{
            Logger.getLogger().log("Błędny zespół");
            throw new IllegalArgumentException("Błędne zespół");
        }
    }

    // getery list - przy użyciu strama aby uzyskać niemodowalne listy
    public List<Zadanie> getZadanie() {
        System.out.println("Zadania " + this.id);
        return zadanie.stream().toList();
    }
    public List<Zespol> getZespol() {
        return zespol.stream().toList();
    }

    // jeżeli przez przypadek zostanie dodane zadanie lub zespół można go usunąć
    public void removeZadanie(Zadanie zadanie) {
        if(!this.zadanie.remove(zadanie)) {
            throw new IllegalArgumentException("Błędne zadanie do usunięcia");
        }
    }
    public void removeZespol(Zespol zespol) {
        if(!this.zespol.remove(zespol)) {
            throw new IllegalArgumentException("Błędny zespół do usunięcia");
        }
    }

    // metody do nadpisania po implementacji interfejsu dobrypracownik
    @Override
    public boolean zdolnyDoPracy() {
        return this.czyZdrowy() && !this.zespol.isEmpty();
    }

    @Override
    public String informacje() {
        return "Wyświetlono informacje :: " + "Menager [" + this.id + "] " + this.getImie() + " " +
                this.getNazwisko() + " " +this.wiekPracownika() + " lat, będący w zespołach - " + this.zespol;
    }

    @Override
    public int wiekPracownika() {
        return Period.between(getDataUrodzenia(), LocalDate.now()).getYears();
    }

}
