package Team;

import Logs.Logger;
import Persons.Manager;
import Persons.Pracownik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Zespol {
    // tworzenie id
    private String id;
    private static int counter = 100;
    // pola klasowe
    private String nazwa;
    private Manager manager;
    // mapa dla lepszego przechowywania i wyszukiwania po kluczu - ID w przypadku pracowników
    private List<Pracownik> pracownicy;
    // przypisanie listy wykonanych zadań przez dany zespół - tylko wykonane
    private List<Zadanie> wykonaneZadania;

    // konstruktor klasy zespol
    public Zespol(String nazwa, Manager manager) {
        this.id = "ZES" + counter++;
        this.nazwa = nazwa;
        this.manager = manager;
        pracownicy = new ArrayList<>();
        System.out.println("Zespol " + nazwa + " utworzony");
        // jeżeli ustawiamy managerem to menager musi mieć ten zespoł w swoich zespołach
        manager.addZespol(this);
        this.wykonaneZadania = new ArrayList<>();
    }

    // metoda przyjmująca jednego pracownika
    public void addPracownik(Pracownik pracownik) {
        if(!pracownicy.contains(pracownik)){
            // jeżeli pracownik którego dodajemy okaże się menagerem to nie ma możliwości jego dodania
            // każdy menager w ID zaczyna się literą M
            if(pracownik.getId().charAt(0) == 'M'){
                throw new IllegalArgumentException("Nie ma możliwości dodania menagera jako pracownika");
            }
            pracownicy.add(pracownik);
            System.out.println("Pracownik " + pracownik  + " dodany do zespołu " + this.nazwa);
            Logger.getLogger().log("Pracownik " + pracownik  + " dodany do zespołu " + this.nazwa);
        }else{
            throw new IllegalArgumentException("Dodanie pracownika do zespółu nie możliwe - jest już w zespole");
        }
    }

    // metoda przyjmująca listę pracowników
    public void addPracownik(List<Pracownik> pracownicy){
        if(!pracownicy.isEmpty()){
            pracownicy.forEach(p -> addPracownik(p));
        }else{
            throw new IllegalArgumentException("Nieprawidłowa lista pracowników");
        }
    }
    // metoda wyświetlająca informacje o całym zespole
    public void getInfo() {
        Logger.getLogger().log("Zespol " + nazwa + ", manager: " + this.manager + " - pracownicy");
        System.out.println("Zespol" + nazwa + ", manager: " + this.manager + " - pracownicy " + this.pracownicy);
    }

    // metoda dodająca wykonane zadanie do zespołu;
    protected void addWykonaneZadanie(Zadanie zadanie) {
        if((!wykonaneZadania.contains(zadanie)) && zadanie.getZespol() == this){
            wykonaneZadania.add(zadanie);
            Logger.getLogger().log("Zadanie " + zadanie.getID() + " zostało wykonane przez zespół " + this.nazwa);
        }else {
            System.out.println("Zadanie nie zostało dodane do wykonanych zadań");
        }
    }

    // metoda wyświetlająca wykonane zadania przez zespół
    public void showWykonaneZadania(){
        System.out.println("Zadania wykonane przez " + this.nazwa);
        this.wykonaneZadania.stream().forEach(z -> System.out.println(z));
    }

    // getter managera
    public Manager getManager() {
        return manager;
    }

    // getter pracowników
    public List<Pracownik> getPracownicy() {
        if(pracownicy.isEmpty()){
            throw new IllegalArgumentException("Lista pracowników jest pusta");
        }
        return List.copyOf(this.pracownicy);
    }

    // getter id
    public String getId() {
        return id;
    }

    // nadpisanie metody toString
    @Override
    public String toString() {
        return "Zespół: " + nazwa;
    }


}
