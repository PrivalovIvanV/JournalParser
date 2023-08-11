package com.example.journalparser.code;

public class AuthorAndArticle {
    private String author;
    private String article;

    @Override
    public String toString() {
        return "Unit{" +
                "author='" + author + '\'' +
                ", article='" + article + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
