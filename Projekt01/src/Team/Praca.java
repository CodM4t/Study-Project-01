package Team;

import Logs.Logger;
import java.util.*;

public class Praca
        // impelementacja interfejsu runnable
        implements Runnable {
    // ID obiektu
    private String id;
    private static int count = 100;
    // pola klasowe
    private List<Zadanie> zadania;
    private String opis;
    private Zespol zespol;
    // statyczna mapa przechowująca zadania
    private final static Map<String, Zadanie> wszystkieZadania = new HashMap<>();

    // konstruktor klasy
    public Praca(Zespol zespol, String opis, List<Zadanie> zadaniaPracy) {
        this.zespol = zespol;
        this.opis = opis;
        this.zadania = zadaniaPracy == null ? new ArrayList<>() : zadaniaPracy;
        this.id = "PRA" + count++;
        System.out.println("Utworzono prace :: " + this.id +  " " + this.opis + " " + this.zespol);
        Logger.getLogger().log("Obiekt Praca utworzony pomyślnie");

    }

    // pozyskanie obiektu zadania po podaniu ID
    public static Zadanie findZadanie(String id){
        if(wszystkieZadania.containsKey(id)) {
            System.out.println("Zadanie " + id + " znalezione :: " + wszystkieZadania.get(id));
            Logger.getLogger().log("Wyszukiwanie zadania ukończone pozytywnie :: zadanie -> " + id);
            return wszystkieZadania.get(id);
        }
        Logger.getLogger().log("Nie znaleziono zadania o podanym ID " + id);
        System.out.println("Nie ma zadania o podanym ID :: " + id );
        return null;
    }

    // dodawanie zadań do mapy
    protected static void addZadanieToMap(Zadanie zadanie){
        Logger.getLogger().log("Zadanie " + zadanie + " dodane do mapy zadań");
        Praca.getWszystkieZadania().put(zadanie.getID(), zadanie);
    }

    // wyciągnięcie mapy
    private static Map<String, Zadanie> getWszystkieZadania(){
        Logger.getLogger().log("Zwrócono mapę wszystkich zadań");
        return wszystkieZadania;
    }

    // wykonanie wszystkich zadań z mapy;
    public static void wykonajWszystkieZadania(){
        System.out.println("Rozpoczęto wykonywanie wszystkich zadań ...");
        Logger.getLogger().log("Rozpoczęto wykonywanie wszystkich niewykonanych zadań");
        // pętla for each przechodzi pokolei po wartościach z mapy wyciągając obiekty zadań
        for(Zadanie zadanie: Praca.getWszystkieZadania().values()){
            // jeżeli zadanie już zostało wykonane to je pomijamy
            if(!zadanie.stanZadania().equals(Stan.ZAKONCZONE)) {
                // uruchomienie wątku z zadaniem
                zadanie.start();
            }
        }
        Praca.getWszystkieZadania().values().forEach(z -> {
            try {
                z.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void run() {
        if(this.zadania.isEmpty()) {
            Logger.getLogger().log("Brak zadań do wykonania w pracy :: " + this.id + " - PRACA WYKONANA");
            return;
        }
        Logger.getLogger().log("Rozpoczęto wykonywanie zadań z pracy " + this.id);
        // dodanie zespołu do zadań przydzielonych w pracy - jeżeli dany zespół wykonuje zadanie
        // to nie powinno to zadanie trafić jako wykonane do zespołu który pierwotnie miał przypisane to zadanie
        // uruchomienie zadań na odzielnych wątkach
        zadania.forEach(z -> {
                    // potrzebne wykonanie tego procesu na wątku i poczekanie na jego ukończenie aby
                    // przypisanie zdążylo się wykonać przez uruchomienienm zadań bo wtedy może się wysypać
                    // przypisanie zespołów
                    Thread przypisanie = new Thread(() -> z.setZespol(this.zespol));
                    przypisanie.start();
                    try {
                        przypisanie.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // uruchomienie zadań z przygotowanymi wszystkimi danymi - prawidłowy zespół
                     z.start();
                });

        // oczekiwanie na wykonanie się wątków i zsynchronizowanie z wątkiem głównym i odapli dalszą część programu dopiero po
        // zakończeniu wszystkich wątków zadań
        zadania.forEach(z -> {
            try {
                z.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        // pętla usuwająca wykonane zadania z listy zadań - nie można tego zrobić w trakcie trwania pętli
        zadania.clear();
        Logger.getLogger().log("PPRACA " + this.id + " WYKONANA");
    }

    public List<Zadanie> getZadania(){
        return this.zadania;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.opis;
    }
}
