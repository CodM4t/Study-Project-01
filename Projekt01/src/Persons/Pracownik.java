package Persons;

import Dept.DzialPracownikow;
import Logs.Logger;
import Team.Zadanie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public abstract
    class Pracownik implements Comparable<Pracownik> {

    // statyczna lista wszystkich utworzonych pracowników
    private static List<Pracownik> pracownicy = new ArrayList<>();
    // pola pracownika
    private String imie;
    private String nazwisko;
    //tworzenie ID - p ( oznaczenie pracownika ) - 0000 numer pracownika
    private static int counter = 1000;
    protected String id;
    // data - za pomocą klasy LocalDate
    private LocalDate dataUrodzenia;
    private DzialPracownikow dzialPracownika;
    private boolean czyZdrowy;
    // lista zadań w których brał udział pracownik - final bo nie zmienia się listy zadań które dany pracownik wykonał
    // HashSet nie pozwala na dodanie duplikatu - nie można wykonać dwa razy zadania które już raz się wykonało
    private final List<Zadanie> wykonaneZadania = new ArrayList<>();

    // konstruktor z domyślnym oznaczeniem pracownika jako P dla podstawowej klasy pracownik
    public Pracownik(String imie, String nazwisko, String dataUrodzenia, String dzial) {
        this(imie, nazwisko, dataUrodzenia, dzial, "P");
    }
    // konstruktor klasy - główny
    protected Pracownik(String imie, String nazwisko, String dataUrodzenia, String dzial, String oznaczenie) {
        // formatowanie daty urodzenia
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        // przypisanie wartości do daty urodzenia - w momencie podania daty w złym formacie wyrzuca błąd
        try{
            this.dataUrodzenia = LocalDate.parse(dataUrodzenia, format);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("Nieprawidłowy zapis daty (dd.MM.yyyy)");
        }

        // przypisanie podstawowych wartości do pól klasowych
        this.imie = imie;
        this.nazwisko = nazwisko;
        // przypisanie podstawowej wartosci pola
        this.czyZdrowy = true;
        // przypisanie ID do pracownika - używane jako klucz w mapie
        this.id = this.setID(oznaczenie);
        // przypisanie działu do pracownika oraz pracownika do działu
        this.dzialPracownika = DzialPracownikow.getDzial(dzial);
        Logger.getLogger().log("Pracownik " + this.id + " " + this.imie + " " + this.nazwisko + " został utworzony");
        DzialPracownikow.getDzial(dzial).addPracownik(this);
        //dodanie do mapy pracowników
        pracownicy.add(this);
        System.out.println("Pracownik " + this.id + " " + this.imie + " stworzony");
    }


    // zmiana zdrowia
    public void setZdrowie(boolean zdrowie) {
        this.czyZdrowy = zdrowie;
        System.out.println("Stan zdrowia pracownika " + this.id + " uległ zmianie na " +
                (zdrowie ? " Zdrowy" : " Chory"));
        Logger.getLogger().log("Stan zdrowia pracownika " + this.id + " uległ zmianie na " +
                (zdrowie ? " Zdrowy" : " Chory"));
    }

    // gettery do pól
    public String getImie() {
        return imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public LocalDate getDataUrodzenia() {
        return dataUrodzenia;
    }
    public DzialPracownikow getDzialPracownika() {
        return dzialPracownika;
    }
    public String getId() {
        return id;
    }
    public boolean czyZdrowy() {
        return czyZdrowy;
    }
    public static List<Pracownik> getPracownicy() {
        // kopia mapy aby chronić dane tak jak w przypadku działu pracowników
        return List.copyOf(pracownicy);
    }

    // implementacja metody compareTo z interfejsu Comparable
    @Override
    public int compareTo(Pracownik o) {
        // porównuje stan zdrowia - jeżeli zdrowi to porównuje który jest starszy
        if (this.czyZdrowy() && !o.czyZdrowy()) {
            return 1;
        }
        if (!this.czyZdrowy() && o.czyZdrowy()) {
            return -1;
        }
        // to samo zdrowie - porównujemy wiek
        return this.getDataUrodzenia().compareTo(o.getDataUrodzenia());
    }

    // metoda pozwaljąca zmieniać oznaczenia w id
    private String setID(String id) {
        return id + counter++;
    }


    // settery do pól konstruktora
    public void setImie(String imie) {
        System.out.println(this + " zmienił imie na " + imie);
        Logger.getLogger().log("Zmiana imienia pracownika " + this.id + " z " + this.imie + " na " + imie);
        this.imie = imie;
    }
    public void setNazwisko(String nazwisko) {
        System.out.println(this + " zmienił nazwisko na " + imie);
        Logger.getLogger().log("Zmiana nazwiska pracownika " + this.id + " z " + this.nazwisko + " na " + nazwisko);
        this.nazwisko = nazwisko;
    }
    public void setDzialPracownika(String dzial) {
        if (dzial != null || !(dzialPracownika.getNazwa().equals("dzial"))){
            Logger.getLogger().log("Zmieniono dział pracownika " + this.id + " na " + dzial);
            this.dzialPracownika.removePracownik(this);
            DzialPracownikow.getDzial(dzial).addPracownik(this);
        }else{
            Logger.getLogger().log("Nie poprawny dział zmiany :: " + this.id + " /= " + dzial);
            throw new IllegalArgumentException("Nie poprawny dzial zmiany");
        }
    }
    // dodawanie wykonanego zadania do listy pracownika
    public void addWykonaneZadanie(Zadanie zadanie){
        if(!wykonaneZadania.contains(zadanie)) {
            wykonaneZadania.add(zadanie);
            System.out.println(zadanie + " dodane poprawnie do pracownika " + this.id);
        }
    }

    // zwrócenie wykonanych zadań przez pracownika - nie do modyfikowania dlatego stream
    public List<Zadanie> getWykonaneZadaniaPracownika(){
        return wykonaneZadania.stream().toList();
    }


    // nadpisanie metody toString - wyświetla samo ID pracownika
    @Override
    public String toString() {
        return this.id + " " + this.imie + " " + this.nazwisko;
    }

    // metoda getInitials - zwraca inicjały osoby
    public String getInicjaly() {
            return this.getImie().charAt(0) + "" + this.getNazwisko().charAt(0);
    }

}
