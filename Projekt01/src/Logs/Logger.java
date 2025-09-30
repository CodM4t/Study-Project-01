package Logs;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    // pola klasowe loggera
    private static Logger logger;
    private final String plik = "log.txt";
    // ustawienie formatu zapisu daty i godziyn danego loga
    private final String czasLoga = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


    // prywatny konstruktor zapewniający stworzenie obiektu tylko metodą klasową
    private Logger(){
        // stworzenie pliku do którego będą zapisywane logi
        try(FileWriter fw = new FileWriter(plik)){
            fw.write("LOGGER URUCHOMIONY\n");
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // tworzenie tylko jednego obiektu loggera - wzorcem singletone
    public static Logger getLogger(){
        if(Logger.logger == null)
            logger = new Logger();
        return Logger.logger;
    }

    // dopisywanie loga do pliku z logami
    public void log(String message){
        String log = "[" + czasLoga + "] " + message + "\n";
        //System.out.println(message);
        try(FileWriter fw = new FileWriter(plik, true)){
            fw.write(log);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
