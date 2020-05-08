package com.codecool.books.model;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJDBC implements Dao<Book, Book>{
    private DataSource dataSource;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private List<Book> bookList;
    List<Author> authorList;

    public BookDaoJDBC(DataSource dataSource, Dao<Author, Author> authorDao) throws SQLException {
        this.dataSource = dataSource;
        bookList = new ArrayList<>();
        authorList = authorDao.getAll();
    }

    @Override
    public void add(Book book) throws SQLException {
        preparedStatement = dataSource.getConnection().prepareStatement("INSERT INTO book (author_id, title)" +
                " VALUES(?, ?)");
        preparedStatement.setInt(1, book.getAuthor().getId());
        preparedStatement.setString(2, book.getTitle());
        int insertRow = preparedStatement.executeUpdate();
    }

    @Override
    public void update(Book book) throws SQLException {
        preparedStatement = dataSource.getConnection().prepareStatement(
                "UPDATE book " +
                "SET title = ?, author_id = ? " +
                "WHERE id = ?");
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setInt(2, book.getAuthor().getId());
        preparedStatement.setInt(3, book.getId());

        int updateRow = preparedStatement.executeUpdate();

    }

    @Override
    public Book get(int id) {
        for(Book book : bookList){
            if(id == book.getId()){
                return book;
            }
        }
        return null;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        bookList.clear();
        preparedStatement = dataSource.getConnection().prepareStatement("SELECT * FROM book");
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            int bookId = resultSet.getInt("id");
            int authorId = resultSet.getInt("author_id");
            String bookTitle = resultSet.getString("title");

            for(Author author : authorList){
                if(author.getId() == authorId){
                    Book book = new Book(author, bookTitle);
                    book.setId(bookId);
                    bookList.add(book);
                }
            }
        }
        return bookList;
    }
}
