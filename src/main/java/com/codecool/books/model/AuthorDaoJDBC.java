package com.codecool.books.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoJDBC implements Dao<Author, Author> {
    private DataSource dataSource;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private List<Author> authorList;

    public AuthorDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        authorList = new ArrayList<>();
    }

    @Override
    public void add(Author author) throws SQLException {
        preparedStatement = dataSource.getConnection().prepareStatement(
                "INSERT INTO author (first_name, last_name, birth_date) " +
                        "VALUES (?, ?, ?)"
        );
        preparedStatement.setString(1, author.getFirstName());
        preparedStatement.setString(2, author.getLastName());
        preparedStatement.setDate(3, author.getBirthDate());

        int addAuthor = preparedStatement.executeUpdate();

        System.out.println("Added new author");

    }

    @Override
    public void update(Author author) throws SQLException {
        preparedStatement = dataSource.getConnection().prepareStatement(
                "UPDATE author " +
                "SET first_name = ?, last_name = ?, birth_date = ? " +
                "WHERE ID = ?");
        preparedStatement.setString(1, author.getFirstName());
        preparedStatement.setString(2, author.getLastName());
        preparedStatement.setDate(3, author.getBirthDate());
        preparedStatement.setInt(4, author.getId());
        int updateRow = preparedStatement.executeUpdate();
        System.out.println("Updated id: " + updateRow);
    }

    @Override
    public Author get(int id) throws SQLException {
        String firstName = "";
        String lastName = "";
        try {
            preparedStatement = dataSource.getConnection().prepareStatement("SELECT * FROM author WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception ex){
            System.out.println("error: " + ex.getMessage());
        }

        while(resultSet.next()){
            firstName = resultSet.getString("first_name");
            lastName = resultSet.getString("last_name");
        }

        for (Author author : authorList) {
            if (author.getLastName().equals(lastName)
            && author.getFirstName().equals(firstName)) {
                return author;
            }
        }
        return null;
    }

    @Override
    public List<Author> getAll() throws SQLException {
        authorList.clear();
        preparedStatement = dataSource.getConnection().prepareStatement("SELECT * FROM author");
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            Date birth_date = resultSet.getDate("birth_date");
            Author author = new Author(first_name, last_name, birth_date);
            author.setId(id);
            authorList.add(author);
        }


        return authorList;
    }


}
