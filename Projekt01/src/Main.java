import Dept.DzialPracownikow;
import Exceptions.NotUniqeNameException;
import Logs.Logger;
import Persons.Manager;
import Persons.Pracownik;
import Persons.Recepcjonista;
import Team.Praca;
import Team.Zadanie;
import Team.Zespol;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Logger log = Logger.getLogger();

        // Stworzenie działów pracowników
        try{
            DzialPracownikow.createDzial("Recepcja");
            DzialPracownikow.createDzial("Obliczenia");
            DzialPracownikow.createDzial("Architekci");
            DzialPracownikow.createDzial("Trenerzy");
        }catch (NotUniqeNameException e){
            System.out.println(e.getMessage());
        }

        // Stworzenie pracowników
        // menagerowie
        Manager mRecepcji = new Manager("Zbigniew", "Drzewski", "12.10.1986", "Recepcja", "Zbig123","Drzwo321");
        Manager mObliczen = new Manager("Karol","Wiśniewski","02.12.2000","Obliczenia","Wiśnia", "Karol321");
        Manager mArchitektow = new Manager("Katarzyna","Łęcka","03.02.1970","Architekci","Katarzyna70","łęcka321");
        Manager mTrener = new Manager("Antoni","Silny","01.01.2000","Trenerzy","Sliniak","silneHaslo");
        // recepcjoniści
        Pracownik rec01 = new Recepcjonista("Michał","Recepcyjny", "10.10.2005","Recepcja","Mich323","Narecepcji55");
        Pracownik rec02 = new Recepcjonista("Michalina","Recepcyjna","10.10.2005","Recepcja","Michalina","55wczesniejodbrata");
        // pracownicy
        Pracownik pra01 = new Pracownik("Karol","Sztrasburger","02.03.2001","Obliczenia") {};
        Pracownik pra02 = new Pracownik("Mirek","Nadzwyczajny","05.08.1996","Architekci") {};
        Pracownik pra03 = new Pracownik("Julia","Kontrabas","15.12.1992","Obliczenia") {};
        Pracownik pra04 = new Pracownik("Róża","Kolecka","22.02.2003","Architekci") {};
        Pracownik pra05 = new Pracownik("Mateusz","Szklarka","21.04.1990","Trenerzy") {};

        // wyświetlenie pracowników poszczególnych działów
        System.out.println("------------------");
        DzialPracownikow.getDzial("Recepcja").showPracownicy();
        DzialPracownikow.getDzial("Obliczenia").showPracownicy();
        DzialPracownikow.getDzial("Architekci").showPracownicy();
        DzialPracownikow.getDzial("Trenerzy").showPracownicy();
        System.out.println("------------------");

        // "Błędne wpisanie imienia i zmiana i wypisanie inicjałów przed i po
        System.out.println(rec01);
        System.out.println(rec01.getInicjaly());
        rec01.setNazwisko("Winogrono");
        System.out.println(rec01);
        System.out.println(rec01.getInicjaly());
        System.out.println("------------------");

        // Stworzenie zespołów
        Zespol zOb = new Zespol("Liczenie macierzy",mObliczen);
        // lista pracowników z0b
        List<Pracownik> marcierzeP = new ArrayList<>();
        marcierzeP.add(pra01);
        marcierzeP.add(pra03);
        //marcierzeP.add(mObliczen);
        zOb.addPracownik(marcierzeP);
        zOb.getInfo();

        System.out.println("------------------");

        Zespol zRe = new Zespol("Rejestrowanie pracowników",mRecepcji);
        // lista pracowników z0b
        List<Pracownik> rejestracjaR = new ArrayList<>();
        rejestracjaR.add(rec02);
        rejestracjaR.add(rec01);
        zRe.addPracownik(rejestracjaR);
        zRe.getInfo();

        System.out.println("------------------");

        Zespol zAr = new Zespol("Wykonanie rysunków",mArchitektow);
        // lista pracowników z0b
        List<Pracownik> rysunkowiA = new ArrayList<>();
        rysunkowiA.add(pra02);
        rysunkowiA.add(pra04);
        zAr.addPracownik(rysunkowiA);
        zAr.getInfo();

        System.out.println("------------------");

        Zespol zTr = new Zespol("Trening umysłu",mTrener);
        // lista pracowników z0b
        List<Pracownik> umyslowiTre = new ArrayList<>();
        umyslowiTre.add(pra05);
        zTr.addPracownik(umyslowiTre);
        zTr.getInfo();

        System.out.println("------------------");

        // Stworzenie zdań dla zespołów

        // obliczenia
        Zadanie przygtowowanieStanowisk = new Zadanie("Przygotowanie Stanowisk");
        Zadanie wykonanieObliczen = new Zadanie("Wykonanie obliczenie");
        wykonanieObliczen.setZespol(zOb);
        List<Zadanie> obliczeniaPodst = new ArrayList<>();
        obliczeniaPodst.add(przygtowowanieStanowisk);

        // stworzenie prac
        Praca pOb = new Praca(zOb, "Obliczanie zależności", obliczeniaPodst);

        //architekci
        Zadanie przygotwaniePapieru = new Zadanie("Przygotowanie Papieru");
        Zadanie naostrzenieOlowkow = new Zadanie("Naostrzenie Olowkow");
        Zadanie rysowanieProjekut = new Zadanie("Rysowanie projekut");
        rysowanieProjekut.setZespol(zAr);
        Zadanie sprzatanie = new Zadanie("Sprzatanie");
        sprzatanie.setZespol(zAr);
        List<Zadanie> przygotowaniaArch = new ArrayList<>();
        przygotowaniaArch.add(przygotwaniePapieru);
        przygotowaniaArch.add(naostrzenieOlowkow);

        // stworzenie prac
        Praca pAr = new Praca(zAr, "Prace architeków", przygotowaniaArch);

        //trenerzy
        Zadanie treningUmyslu = new Zadanie("Trening umyslu","Wykonanie zadań pobudzających kreatywność i trzeźwość umysłu dla pracowników", true);
        treningUmyslu.setZespol(zTr);
        // stworzenie prac
        Praca pTr = new Praca(zTr, "Trening", null);

        // recepcja
        Zadanie rejestracjaPracownikow = new Zadanie("Rejestracja pracownikow");
        rejestracjaPracownikow.setZespol(zRe);
        // zadanie nie zatwierdzone
        Zadanie wylaczeniePradu = new Zadanie("Wylaczenie pradu", "Wyłączyć prąd w całym budynku", false);
        wylaczeniePradu.setZespol(zRe);

        List<Zadanie> recepcja = new ArrayList<>();
        recepcja.add(sprzatanie);

        // stworzenie prac
        Praca pRe = new Praca(zRe, "Rejestracja", recepcja);

        // lista prac
        List<Praca> prace = new ArrayList<>();
        prace.add(pOb);
        prace.add(pTr);
        prace.add(pRe);
        prace.add(pAr);

        // wykonanie prac - w odzielnych wątkach - przypisanych do prac - zaczekanie aż prace się wykonają
        List<Thread> watki = new ArrayList<>();
        prace.forEach(p -> {
            watki.add(new Thread(p));
        });

        watki.forEach(w -> w.start());

        watki.forEach(w -> {
            try {
                w.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // wyświetlenie zadań ukończonych przez dane zespoły
        System.out.println("------------------");

        zOb.showWykonaneZadania();
        System.out.println("------------------");
        zAr.showWykonaneZadania();
        System.out.println("------------------");
        zRe.showWykonaneZadania();
        System.out.println("------------------");
        zTr.showWykonaneZadania();

        System.out.println("------------------");


        // wykonanie pozostałych prac
        Praca.wykonajWszystkieZadania();

        wylaczeniePradu.stanZadania();

        System.out.println("------------------");

        zOb.showWykonaneZadania();
        System.out.println("------------------");
        zAr.showWykonaneZadania();
        System.out.println("------------------");
        zRe.showWykonaneZadania();
        System.out.println("------------------");
        zTr.showWykonaneZadania();

        System.out.println("------------------");

        // wyświetlenie posrotowanej listy pracowników działu - interfejs comparable i metoda compareTo
        DzialPracownikow.getDzial("Obliczenia").showPracownicy();

        // znalezienie zadania po jego ID
        Praca.findZadanie("ZAD10001");

        // wyświetlenie metod interfejsu IDobryPracownik
        System.out.println("Wiek pracownika " + mRecepcji + " " + mRecepcji.wiekPracownika());
        System.out.println("Informacje na temat " + rec01 + " -> " + ((Recepcjonista) rec01).informacje());
    }
}