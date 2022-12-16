package exam.sockets;
import exam.domain.Book;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {

    public void start(String ip, int port) throws IOException, ClassNotFoundException {
        try(Socket clientSocket = new Socket(ip, port);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            Scanner scanner = new Scanner(System.in)) {
            printCmgInfo();
            String cmd = scanner.next();
            while(!cmd.equalsIgnoreCase("stop")) {
                if(cmd.equalsIgnoreCase("gba")) {
                    System.out.println("Enter author whose books you want to get:");
                    String input = scanner.next();
                    out.println("gba:" + input);
                    System.out.println(Arrays.toString(((List<Book>) in.readObject()).toArray()));
                } else if(cmd.equalsIgnoreCase("gbp")) {
                    System.out.println("Enter publishing whose books you want to get:");
                    String input = scanner.next();
                    out.println("gbp:" + input);
                    System.out.println(Arrays.toString(((List<Book>) in.readObject()).toArray()));
                } else if(cmd.equalsIgnoreCase("gby")) {
                    System.out.println("Enter target year:");
                    String input = scanner.next();
                    out.println("gby:" + input);
                    System.out.println(Arrays.toString(((List<Book>) in.readObject()).toArray()));
                } else {
                    System.out.println("Unknown command.");
                    printCmgInfo();
                }
                cmd = scanner.next();
            }
            System.out.println("Stopped client");
            out.write(cmd);
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
        //localhost
        new Client().start("127.0.0.1", 1111);
    }
}
