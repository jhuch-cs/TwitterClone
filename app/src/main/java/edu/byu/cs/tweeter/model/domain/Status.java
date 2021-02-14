package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Status implements Comparable<User>, Serializable {

    private String content;
    private User author;
    private LocalDateTime timePublished;

    public Status(String content, User author) {
        this.content = content;
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getTimePublished() {
        return timePublished;
    }

    public void setTimePublished(LocalDateTime timePublished) {
        this.timePublished = timePublished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return content.equals(status.content) &&
                author.equals(status.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, author);
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
