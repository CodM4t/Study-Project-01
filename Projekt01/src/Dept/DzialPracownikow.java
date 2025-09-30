package Dept;

import Exceptions.NotUniqeNameException;
import Persons.Pracownik;
import Logs.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DzialPracownikow {
    // prywatne finalne pole nazwa - nie zmienialne więc jest final - jest za razem identyfikatorem obiektu "ID"
    private final String nazwa;

    // HashMapa - nie przyjmuje dwukrotnie takiego samego klucza - czyli nazwy w tym przypadku która ma być unikalna
    // szybki dostęp do samej nazwy jak i dostęp do dalszej zawartosci działu
    // nie interesuje nas kolejność działów
    private static final Map<String, DzialPracownikow> dzialy = new HashMap<>();

    // lista pracowników - użycie dzikiej karty
    private List<Pracownik> pracownicy;

    // konstruktor prywatny - chcemy mieć gwarancje, że obiekt zostanie utworzony jedynie przez metodę tej klasy
    private DzialPracownikow(String nazwa) {
        this.nazwa = nazwa;
        pracownicy = new ArrayList<>();
        System.out.println("Dział pracownika " + nazwa + " utworzony");
        Logger.getLogger().log("Dział pracownika " + nazwa + " utworzony");
    }

    // metoda tworząca obiekt klasy
    public static void createDzial(String nazwa) {
        // sprawdzenie czy istnieje już obiekt o podanej nazwie - jak tak to wyrzucenie błędu
        if(dzialy.containsKey(nazwa))
            throw new NotUniqeNameException("Nazwa działu nie jest unikalna");
        // jeżeli nie to utworzenie nowego obiektu i dodanie go do mapy
        dzialy.put(nazwa, new DzialPracownikow(nazwa));
    }

    // metoda dodająca pracownika do dzialu
    public void addPracownik(Pracownik pracownik) {
        // sprawdzenie czy pracownik nie jest nullem
        if(pracownik != null) {
            if (pracownicy.contains(pracownik)) {
                System.out.println("Pracownik już był zarejestrowany w dziale");
                Logger.getLogger().log("Próba dodania pracownika " + pracownik + " do działu " +
                        this.nazwa + " - NIEPOWODZENIE - pracownik zarejestrowany w dziale");
            }
            else {
                pracownicy.add(pracownik);
                System.out.println(pracownik.getId() + " dodany do działu - " + this.nazwa);
                Logger.getLogger().log("Pracownik " + pracownik + " dodany do działu " + this.nazwa);
            }
        }else{
            Logger.getLogger().log("Próba dodania pracownika jako NULL");
            throw new IllegalArgumentException("Pracownik nie może być null");
        }
    }

    // usuwanie pracownika z dzialu
    public void removePracownik(Pracownik pracownik) {
        if(!pracownicy.contains(pracownik)) {
            System.out.println("Pracownik " + pracownik + " nie jest zarejestrowany w dziale");
            Logger.getLogger().log("Pracownik " + pracownik + " nie jest zarejestrowany w dziale");
        }
        else {
            pracownicy.remove(pracownik);
            System.out.println(pracownik + " usunięty z działu - " + this.nazwa);
            Logger.getLogger().log(pracownik + " usunięty z działu - " + this.nazwa);
        }
    }

    // metoda pokazująca wszystkich pracowników działu - posortowanych zgodnie z metodą comapreTo z klasy pracownicy
    public void showPracownicy(){
        if(!pracownicy.isEmpty()) {
            System.out.println("PRACOWNICY " + this.nazwa);
            // wyświetla posortowanych pracowników (ID danego pracownika)
            pracownicy.stream().sorted().forEach(pracownik -> System.out.print(pracownik + ", "));
            Logger.getLogger().log("Wyświetlenie pracowników działu " + this.nazwa);
            System.out.println();
        }
        else {
            System.out.println("Brak pracowników w dziale");
            Logger.getLogger().log("Próba wyświetlenia pracowników pustego działu");
        }
    }

    // getery - dostęp do odczytu pól klasowych
    public String getNazwa() {
        return nazwa;
    }

    public static Map<String, DzialPracownikow> getDzialy() {
        // zwraca kopie mapy działów - aby z zewnątrz nie dało się ingerować w mapę bazową
        return Map.copyOf(dzialy);
    }

    public List<Pracownik> getPracownicy() {
        return pracownicy;
    }

    // metoda do wyszukiwania dzialu po nazwie - skraca kod w main - kod jest czytelniszjy
    public static DzialPracownikow getDzial(String nazwa) {
        if(dzialy.containsKey(nazwa)) {
            return dzialy.get(nazwa);
        }
        System.out.println(nazwa + " :: Dział nie istnieje \nDostępne działy: ");
        Logger.getLogger().log("Szukany dział nie dostępny :: " + nazwa);
        for(DzialPracownikow dzial : dzialy.values())
            System.out.println(dzial.nazwa);
        throw new IllegalArgumentException("Podany dział nie istnieje");
    }

    // nadpisanie metody toString
    @Override
    public String toString() {
        return "Dział: " + nazwa + ", pracownicy w dziale: " + pracownicy;
    }
}
