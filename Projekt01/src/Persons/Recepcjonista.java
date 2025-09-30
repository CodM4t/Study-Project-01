package Persons;

import Logs.Logger;

import java.time.LocalDate;
import java.time.Period;

public class Recepcjonista
        // dziedziczy po klasie recepcjonista i implementuje interfejs IDobryPracownik
        extends Pracownik
        implements IDobryPracownik{
    // dodatkowe pola klasowe
    private String login;
    private String haslo;
    private String inicjaly;

    // konstruktor inicjalizuj≥ący dodatkowe pola klasowe oraz odwołujący się do konstruktora klasy Pracownik
    public Recepcjonista(String imie, String nazwisko, String dataUrodzenia, String dzial, String login, String haslo) {
        super(imie, nazwisko, dataUrodzenia, dzial, "R");
        this.login = login;
        this.haslo = haslo;
        // przypisanie inicjałów recepcjoniście za pomocą klasy getInitials
        this.inicjaly = getInicjaly();
    }
    // drugi konstruktor do nadania oznaczenia ID
    protected Recepcjonista(String imie, String nazwisko, String dataUrodzenia, String dzial, String login, String haslo, String oznaczenie) {
        super(imie, nazwisko, dataUrodzenia, dzial, oznaczenie);
        this.login = login;
        this.haslo = haslo;
        // przypisanie inicjałów recepcjoniście za pomocą klasy getInitials
        this.inicjaly = getInicjaly();
    }

    // metoda prywatna - pomocnicza wywoływana przy zmianie imienia przypisująca nowe inicjały do recepcjonisty
    private void setInicjaly(String initial) {
        this.inicjaly = initial;
    }

    // getery do pól - login i haslo
    public String getLogin() {
        return login;
    }
    public String getHaslo() {
        return haslo;
    }

    // setery do pól - login i haslo
    public void setHaslo(String haslo) {
        System.out.println("Dokonano zmiany hasła");
        Logger.getLogger().log("Dokonano zmiany hasla użytkownika " + this.login);
        this.haslo = haslo;
    }
    public void setLogin(String login) {
        System.out.println("Dokonano zmiany loginu");
        Logger.getLogger().log("Dokonano zmiany loginu recepcjonisty " + this.id);
        this.login = login;
    }

    // nadpisana metoda set imie - która dodatkowo poprawia aktualne inicjały
    @Override
    public void setImie(String imie) {
        super.setImie(imie);
        setInicjaly(getInicjaly());
    }
    // nadpisana metoda set nazwisko - która dodatkowo poprawia aktualne inicjały
    @Override
    public void setNazwisko(String nazwisko) {
        super.setNazwisko(nazwisko);
        setInicjaly(getInicjaly());
    }

    // metody do nadpisania po implementacji interfejsu dobrypracownik
    @Override
    public boolean zdolnyDoPracy() {
        return this.czyZdrowy();
    }

    @Override
    public String informacje() {
        return "Recepcjonista [" + this.id + "] " + this.getImie() + " " +
                this.getNazwisko() + " " +this.wiekPracownika() + " lat, o loginie - " + this.getLogin();
    }

    @Override
    public int wiekPracownika() {
        return Period.between(getDataUrodzenia(), LocalDate.now()).getYears();
    }
}
