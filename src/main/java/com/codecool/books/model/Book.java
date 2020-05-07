package com.codecool.books.model;

public class Book {
    private int id;
    private Author author;
    private String title;

    public Book(Author author, String title) {
        this.author = author;
        this.title = title;
    }


    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public Author getAuthor(){
        return author;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Book %d: %s | %s",
                id, title, author);
    }

}
