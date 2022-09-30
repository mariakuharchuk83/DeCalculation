package Lab4.a.File;

import Lab4.a.File.Data.Data;
import Lab4.a.Lock.RWLock;
import Lab4.a.main.Main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class Finder implements Runnable{
    private final File file;
    private final RWLock lock;
    private final AtomicInteger recordsNumber;

    public Finder(File file, RWLock rwLock, AtomicInteger recordsNumber) {
        this.file = file;
        this.lock = rwLock;
        this.recordsNumber = recordsNumber;
    }

    @Override
    public void run() {
        while (Main.running) {
            try {
                Thread.sleep(Data.randomInt(1500, 2500));
                lock.lockRead();
                System.out.printf(Thread.currentThread().getName() + " locked\n");
                int index = Data.randomInt(1, recordsNumber.get());
                System.out.printf(Thread.currentThread().getName() + " looking for record with index " + index + "\n");
                var record = getRecord(index);
                Thread.sleep(500);
                if (record != null) {
                    System.out.printf(Thread.currentThread().getName() + " found record: "+  record.name + record.phone + "\n");
                    System.out.println("record by name " + record.name.toUpperCase() + " has number: " + getPhone(record.name) +"\n");
                    System.out.println("record by phone " + record.phone + " has name: " + getName(record.phone) +"\n");
                }
                System.out.printf(Thread.currentThread().getName() + " unlocked\n");
                lock.unlockRead();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Data.Record getRecord(int index) {
        try {
            var scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNext()) {
                var record = new Data.Record(scanner.next(), scanner.next());
                i++;
                if (i == index) {
                    return record;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getName(String phone) {
        try {
            var scanner = new Scanner(file);
            while (scanner.hasNext()) {
                var record = new Data.Record(scanner.next(), scanner.next());
                if (phone.equals(record.phone)) {
                    return record.name;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPhone(String name) {
        try {
            var scanner = new Scanner(file);
            while (scanner.hasNext()) {
                var record = new Data.Record(scanner.next(), scanner.next());
                if (name.equals(record.name)) {
                    return record.phone;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
