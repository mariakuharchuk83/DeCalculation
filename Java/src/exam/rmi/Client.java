package exam.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static final String UNIC_BINDING_NAME = "books.service";

    public void start() throws RemoteException, NotBoundException {
        final Registry registry = LocateRegistry.getRegistry(2099);
        BookService service = (BookService) registry.lookup(UNIC_BINDING_NAME);

        try(Scanner scanner = new Scanner(System.in)) {
            printCmgInfo();
            String cmd = scanner.next();
            while (!cmd.equalsIgnoreCase("stop")) {
                if (cmd.equalsIgnoreCase("gba")) {
                    System.out.println("Enter author whose books you want to get:");
                    String input = scanner.next();
                    System.out.println(service.getByAuthor(input));
                } else if (cmd.equalsIgnoreCase("gbp")) {
                    System.out.println("Enter publishing whose books you want to get:");
                    String input = scanner.next();
                    System.out.println(service.getByPublishing(input));
                } else if (cmd.equalsIgnoreCase("gby")) {
                    System.out.println("Enter target year:");
                    Long input = Long.parseLong(scanner.next());
                    System.out.println(service.getByYear(input));
                } else {
                    System.out.println("Unknown command.");
                    printCmgInfo();
                }
                cmd = scanner.next();
            }
            System.out.println("Client stopped");
        }
    }

    public void printCmgInfo() {
        System.out.println("""
                allowed commands:
                gba(get by author)
                gbp(get by publishing)
                gby(get by year)
                """);
    }

    public static void main(String[] args) throws Exception {
        new Client().start();
    }
}
