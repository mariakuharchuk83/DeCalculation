package exam.rmi;

import exam.domain.Book;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final String UNIC_BINDING_NAME = "books.service";

    public static void main(String[] args) throws Exception {
        final List<Book> books = new ArrayList<>();
        books.add(new Book("book1", List.of("author1", "author2"), "publishing1", 2000L, 100L, 100L, "type1"));
        books.add(new Book("book2", List.of("author3", "author1"), "publishing2", 2001L, 100L, 100L, "type2"));
        books.add(new Book("book3", List.of("author5"), "publishing3", 2002L, 100L, 100L, "type3"));
        books.add(new Book("book4", List.of("author6"), "publishing4", 2000L, 100L, 100L, "type1"));
        books.add(new Book("book5", List.of("author7"), "publishing5", 2000L, 100L, 100L, "type1"));

        BooksServiceImpl booksService = new BooksServiceImpl(books);
        final Registry registry = LocateRegistry.createRegistry(2099);
        Remote stub = UnicastRemoteObject.exportObject(booksService, 0);
        registry.bind(UNIC_BINDING_NAME, stub);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
