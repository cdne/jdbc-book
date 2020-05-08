package com.codecool.books;

import com.codecool.books.model.*;
import com.codecool.books.view.UserInterface;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookManager extends Manager {
    Dao<Book, Book> bookDao;
    Dao<Author, Author> authorDao;


    public BookManager(UserInterface ui, Dao<Book, Book> bookDao, Dao<Author, Author> authorDao){
        super(ui);
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @Override
    protected void add() throws SQLException {
        String title = ui.readString("Title", "X");
        String authorID = ui.readString("Author id ", "1");
        bookDao.add(new Book(authorDao.get(Integer.parseInt(authorID)), title));
    }

    @Override
    protected String getName() {
        return "Book Manager";
    }

    @Override
    protected void list() throws SQLException {
        for (Book book: bookDao.getAll()) {
            ui.println(book);
        }
    }

    @Override
    protected void edit() throws SQLException {
        int id = ui.readInt("Book ID", 0);
        Book book = bookDao.get(id);
        if (book == null) {
            ui.println("Book not found!");
            return;
        }
        ui.println(book);
        String title = ui.readString("Title: ", book.getTitle());
        int authorId = ui.readInt("Author Id: ", book.getAuthor().getId());
        book.setTitle(title);
        book.setAuthor(authorDao.get(authorId));
        bookDao.update(book);
    }
}
