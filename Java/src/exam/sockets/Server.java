package exam.sockets;

import exam.domain.Book;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Server {
    public void start(List<Book> data, int port) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket clientSocket = serverSocket.accept();
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String input;
            while (!(input = in.readLine()).equalsIgnoreCase("stop")) {
                if (input.matches("gba:\\w+")) {
                    String author = input.split(":")[1];
                    final List<Book> filtered = data.stream()
                            .filter(book -> book.getAuthors().stream().anyMatch(author::equalsIgnoreCase))
                            .collect(Collectors.toList());
                    out.writeObject(filtered);
                } else if (input.matches("gbp:\\w+")) {
                    String publishing = input.split(":")[1];
                    final List<Book> filtered = data.stream()
                            .filter(book -> publishing.equalsIgnoreCase(book.getPublishing())).collect(Collectors.toList());
                    out.writeObject(filtered);
                } else if (input.matches("gby:\\w+")) {
                    String year = input.split(":")[1];
                    final List<Book> filtered = data.stream()
                            .filter(book -> book.getYear() >= Long.parseLong(year)).collect(Collectors.toList());
                    out.writeObject(filtered);
                } else {
                    System.out.println("unknown command");
                }
            }
            System.out.println("Stopped server");
        }
    }

    public static void main(String[] args) throws Exception {
        final List<Book> books = new ArrayList<>();
        books.add(new Book("book1", List.of("author1", "author2"), "publishing1", 2000L, 100L, 100L, "type1"));
        books.add(new Book("book2", List.of("author3", "author1"), "publishing2", 2001L, 100L, 100L, "type2"));
        books.add(new Book("book3", List.of("author5"), "publishing3", 2002L, 100L, 100L, "type3"));
        books.add(new Book("book4", List.of("author6"), "publishing4", 2000L, 100L, 100L, "type1"));
        books.add(new Book("book5", List.of("author7"), "publishing5", 2000L, 100L, 100L, "type1"));

        Server server = new Server();
        server.start(books, 1111);
    }
}
