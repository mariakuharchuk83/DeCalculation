package Lab4.a.File;

import Lab4.a.File.Data.Data;
import Lab4.a.Lock.RWLock;
import Lab4.a.main.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class FileEditor implements Runnable {
    private final File file;
    private final RWLock lock;
    private final AtomicInteger recordsNumber;
    private int counter = 0;

    public FileEditor(File file, RWLock rwLock, AtomicInteger recordsNumber) {
        this.file = file;
        this.lock = rwLock;
        this.recordsNumber  = recordsNumber;
    }

    @Override
    public void run() {
        while (Main.running) {
            try {
                Thread.sleep(Data.randomInt(1500, 2500));
                lock.lockWrite();
                System.out.printf(Thread.currentThread().getName() + " locked\n");
                if (Math.random() < 0.6) {
                    addRecord(new Data.Record("Name" + (counter + 1), String.valueOf(counter + 1).repeat(8)));
                } else {
                    deleteRecord(Data.randomInt(1, recordsNumber.get()));
                }
                System.out.printf(Thread.currentThread().getName() + " unlocked\n");
                lock.unlockWrite();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    private void addRecord(Data.Record record) {
        try {
            var writer = new FileWriter(file, true);
            counter++;
            recordsNumber.incrementAndGet();
            writer.write(record.name + " " + record.phone + " ");
            System.out.printf(Thread.currentThread().getName() + " added record: " + record.name + " " + record.phone + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteRecord(int index) {
        try {
            var scanner = new Scanner(file);
            var elements = new ArrayList<Data.Record>();
            int i = 0;
            while (scanner.hasNext()) {
                var record = new Data.Record(scanner.next(), scanner.next());
                i++;
                if (i != index) {
                    elements.add(record);
                } else {
                    recordsNumber.decrementAndGet();
                    System.out.printf(Thread.currentThread().getName() + " removed record: " +  record.name + " " + record.phone + "\n");
                }
            }

            var writer = new FileWriter(file, false);
            for (var record : elements) {
                writer.write(record.name + " " + record.phone + " ");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
