package Lab4.a.main;

import Lab4.a.File.FileEditor;
import Lab4.a.File.Finder;
import Lab4.a.Lock.RWLock;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static boolean running = true;
    public static void main(String[] args) {
        var rwLock = new RWLock();
        var recordsNumber = new AtomicInteger();

        var file = new File("data.txt");
        try {
            if(file.delete()) {
                System.out.println("File deleted");
            }
            if(file.createNewFile()) {
                System.out.println("File created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new FileEditor(file, rwLock, recordsNumber), "Editor").start();
        new Thread(new Finder(file, rwLock, recordsNumber), "Finder-1").start();
        new Thread(new Finder(file, rwLock, recordsNumber), "Finder-2").start();
    }

    public static void stop() {
        running = false;
    }
}
