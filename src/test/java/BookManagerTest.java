
import org.konstde00.model.Book;
import org.konstde00.model.Genre;
import org.konstde00.service.BookManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class BookManagerTest {

    private BookManager bookManager;
    private Book testBook;

    @BeforeClass
    public void setUpClass() {
        bookManager = new BookManager();
    }

    @BeforeMethod
    public void setUpMethod() {
        testBook = new Book("1", "Test Book", "Author Name", Genre.PHILOSOPHICAL_FICTION);
        bookManager.addBook(testBook);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetBookNotFound() {
        bookManager.getBook("nonexistent");
    }

    @Test
    public void testAddAndRemoveBook() {
        String id = "2";
        Book book = new Book(id, "Another Test Book", "Another Author", Genre.DYSTOPIA);
        bookManager.addBook(book);
        Assert.assertNotNull(bookManager.getBook(id));
        Assert.assertEquals(bookManager.removeBook(id), book);
        Assert.assertNull(bookManager.removeBook(id));
    }

    @Test
    public void testFindBooksByTitle() {
        List<Book> foundBooks = bookManager.findBooksByTitle("Test Book");
        Assert.assertFalse(foundBooks.isEmpty());
        Assert.assertTrue(foundBooks.contains(testBook));
    }

    @DataProvider(name = "genreProvider")
    public Genre[] genreProvider() {
        return Genre.values();
    }

    @Test(dataProvider = "genreProvider")
    public void testFindBooksByGenre(Genre genre) {
        List<Book> foundBooks = bookManager.findBooksByGenre(genre);
        if (genre.equals(Genre.PHILOSOPHICAL_FICTION)) {
            Assert.assertTrue(foundBooks.contains(testBook));
        } else {
            Assert.assertTrue(foundBooks.isEmpty());
        }
    }

    @Test(groups = {"authorGroup"})
    public void testGetBooksByAuthor() {
        List<Book> foundBooks = bookManager.getBooksByAuthor("Author Name");
        Assert.assertFalse(foundBooks.isEmpty());
        Assert.assertTrue(foundBooks.contains(testBook));
    }
}
