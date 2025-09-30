package Team;

import Logs.Logger;
import java.time.LocalDateTime;

public class Zadanie
        extends Thread{
    // pola klasowe
    private String id;
    private static int count = 10000;
    private String nazwa;
    private String opis;
    private Stan stan;
    private LocalDateTime dataUtworzenia;
    private LocalDateTime dataZakonczenia;
    private int czasWykonania;
    // przypisanie zespołu do zadania
    private Zespol zespol;
    // zmienna sprawdzająca czy zadanie jest zatwierdzone
    private boolean zatwierdzenie;

    // konstruktory klasy
    public Zadanie(String nazwa, String opis, Boolean zatwierdzone) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.stan = Stan.UTWORZONE;
        this.zatwierdzenie = zatwierdzone;
        dataUtworzenia = LocalDateTime.now();
        // przypisanie losowego czasu od 3 do 8 sekund
        czasWykonania = (int)(Math.random() * 5) + 3;
        // przypisanie unikalnego ID
        this.id = "ZAD" + count++;
        System.out.println("Zadanie zostało utworzone :: " + nazwa);
        Logger.getLogger().log("Utworzono zadanie " + this.id + " " + nazwa);
        // utowrzenie zadania dodaje go do statycznej mapy wszystkich zadań
        Praca.addZadanieToMap(this);
    }
    public Zadanie(String nazwa){
        this.nazwa = nazwa;
        this.stan = Stan.UTWORZONE;
        dataUtworzenia = LocalDateTime.now();
        czasWykonania = (int)(Math.random() * 5) + 3;
        this.zatwierdzenie = true;
        this.id = "ZAD" + count++;
        System.out.println("Zadanie zostało utworzone :: " + nazwa);
        Logger.getLogger().log("Utworzono zadanie " + this.id + " " + nazwa);
        Praca.addZadanieToMap(this);
    }

    //dodanie zadania do zespołu
    public void setZespol(Zespol zespol){
        // jeżeli zespół nie jest przypisany w innym pezypadku wyrzuć błąd
        if(this.zespol == null){
            System.out.println("Zespół " + zespol + " został przypisany do zadania " + this.id + " " + nazwa);
            Logger.getLogger().log("Przypisano zespół " + zespol + " do zadania " + this.id);
            this.zespol = zespol;
            // przypisanie zadania do menagera zespolu
            zespol.getManager().addZadanie(this);
        }else{
            // warunek zmiany zespołu zadania - jeżeli zadanie zostało już wykonane przez dany zespół to zamiana jest nie możliwa
            if(this.stan.equals(Stan.ZAKONCZONE))
                System.out.println("Zadanie wykonane - zmiana zespołu nie możliwa");
            else {
                Logger.getLogger().log("Zadanie " + this.id + " posiada już zespół wykonujący - zmiana na " + zespol.getId());
                this.zespol = zespol;
            }
        }
    }

    // sprawdzenie czy każdy pracownik zespołu oraz menager jest zdrowy
    private boolean czyWszyscyZdrowi() {
        if(zespol == null)
            throw new IllegalArgumentException("Brak przypisanego zespołu do zadania");
        if(!zespol.getManager().czyZdrowy())
            return false;
        // jeżeli menager jest zdrowy to sprawdzamy czy każ∂y pracownik jest zdrowy
        // wykorzystane streamy do tego i metoda allMatch sprawdzająca czy każdy element zwraca true
        boolean zdrowi = zespol.getPracownicy().stream().
                allMatch(p -> p.czyZdrowy());
        Logger.getLogger().log(zdrowi ? "Wszyscy pracownicy zespołu " + zespol.getId() + " są zdrowi" :
                "Przynajmniej jeden pracownik zespołu " + zespol.getId() + " jest chory");
        return zdrowi;
    }

    // zwraca stan zadania
    public Stan stanZadania(){
        return stan;
    }

    // metoda uruchamiająca zadanie
    @Override
    public void run() {
        if(this.stan.equals(Stan.ZAKONCZONE)) {
            System.out.println(this.id + " zadanie było zakończone - wykonanie niemożliwe");
        }

        Logger.getLogger().log("Próba rozpoczęcia zadania " + this.id);
        // sprawdzenie czy zadanie zostało zatwierdzone
        if(!this.zatwierdzenie) {
            Logger.getLogger().log("Zadanie " + this.id  + " nie zatwierdzone");
            return;
        }
        // sprawdzenie czy zespół jest zdrowy
        if(!czyWszyscyZdrowi()) {
            System.out.println("Nie wszyscy w zespole są zdrowi - zadanie nie możliwe do wykonania");
            return;
        }

        // jeżeli warunki są spełnione to przechodzi do wykonania zadania
        stan = Stan.ROZPOCZETE;
        Logger.getLogger().log("Zadanie " + this.id + " zmieniło status na rozpoczęte :: czas na wykonanie " + this.czasWykonania + "s");

        // wykonywanie zadania
        try{
            // zmienna pomocnicza do odliczania
            int tmp = 0;
            // pętla while
            while(tmp < czasWykonania) {
                System.out.println("Zadanie " + this.nazwa +
                        " " + this.stan + " do ukończenia pozsotało: " +
                        (czasWykonania - tmp));
                // sleep 1000 ponieważ czas jest liczony w milisekundach
                Thread.sleep(1000);
                // zwiększenie zmiennej pomocniczej do odliczania
                tmp++;
            }
            // w razie gdyby wystąpił jakiś błąd wykonywanie zadania zostanie przerwane
        }catch (Exception e){
            Logger.getLogger().log("Wykonywanie zadania " + this.id + " zostało przerwane");
        }

        // zadanie wykonało się prawidłowo - przypisanie daty zakonczenia oraz zmiana stanu
        this.dataZakonczenia = LocalDateTime.now();
        stan = Stan.ZAKONCZONE;
        //Logger.getLogger().log("Zadanie " + this.id + " zostało wykonane");
        this.zespol.addWykonaneZadanie(this);
        // dodanie wykonanego zadania do pracowników zespołu którzy uczestniczyli w tym zadaniu
        zespol.getPracownicy().forEach(p -> p.addWykonaneZadanie(this));
    }


    // gettery do pól klasowych
    public String getNazwa() {
        return nazwa;
    }
    public String getOpis() {
        return opis;
    }
    public LocalDateTime getDataUtworzenia() {
        return dataUtworzenia;
    }
    public LocalDateTime getDataZakonczenia() {
        return dataZakonczenia;
    }
    public int getCzasWykonania() {
        return czasWykonania;
    }
    public String getID(){
        return id;
    }
    public Zespol getZespol() {
        return zespol;
    }

    // nadpisanie metody toString
    @Override
    public String toString() {
        return nazwa + "" + (opis == null ? "" : " - " + opis) + " - " + stan;
    }
}
