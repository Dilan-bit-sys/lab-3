import java.util.ArrayList;
import java.util.List;

public class Author {
    private String name;
    private List<Book> books;

    public Author(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void addBook(String title, double price) {
        Book newBook = new Book(title, this, price);
        this.books.add(newBook);
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public String getName() {
        return this.name;
    }
}
public class Book {
    private String title;
    private Author author;
    private double price;

    public Book(String title, Author author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getInfo() {
        return "Title: " + this.title + ", Author: " + this.author.getName() + ", Price: $" + this.price;
    }
}
public class author {
    public static void main(String[] args) {

        Author author = new Author("J.K. Rowling");

        Book book1 = new Book("Harry Potter and the Sorcerer's Stone", author, 20.99);
        Book book2 = new Book("Harry Potter and the Chamber of Secrets", author, 22.99);

        author.addBook(book1);
        author.addBook(book2);

        author.addBook("Harry Potter and the Prisoner of Azkaban", 24.99);

        System.out.println("Books by " + author.getName() + ":");
        List<Book> books = author.getBooks();
        for (Book book : books) {
            System.out.println(book.getInfo());
        }
    }
}
