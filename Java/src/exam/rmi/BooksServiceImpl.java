package exam.rmi;
import exam.domain.Book;
import lombok.AllArgsConstructor;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BooksServiceImpl implements BookService {
    private List<Book> books;

    public BooksServiceImpl() {
        this.books = new ArrayList<>();
    }

    @Override
    public void addBooks(List<Book> books) throws RemoteException {
        this.books.addAll(books);
    }

    @Override
    public List<Book> getByAuthor(String author) throws RemoteException {
        return books.stream()
                .filter(book -> book.getAuthors().stream().anyMatch(author::equalsIgnoreCase))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getByPublishing(String publishing) throws RemoteException {
        return books.stream()
                .filter(book -> publishing.equalsIgnoreCase(book.getPublishing())).collect(Collectors.toList());
    }

    @Override
    public List<Book> getByYear(Long year) throws RemoteException {
        return books.stream()
                .filter(book -> book.getYear() >= year).collect(Collectors.toList());
    }
}
