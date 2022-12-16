package exam.rmi;
import exam.domain.Book;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BookService extends Remote {
    void addBooks(List<Book> books) throws RemoteException;

    List<Book> getByAuthor(String author) throws RemoteException;

    List<Book> getByPublishing(String publishing) throws RemoteException;

    List<Book> getByYear(Long year) throws RemoteException;
}
