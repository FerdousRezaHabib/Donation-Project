package org.example.donation_project.model;

import javafx.beans.property.*;

public class Notification {

    private final LongProperty id;
    private final StringProperty title;
    private final StringProperty message;
    private final BooleanProperty isRead;

    public Notification(long id, String title, String message, boolean isRead) {
        this.id = new SimpleLongProperty(id);
        this.title = new SimpleStringProperty(title);
        this.message = new SimpleStringProperty(message);
        this.isRead = new SimpleBooleanProperty(isRead);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public boolean isRead() {
        return isRead.get();
    }

    public BooleanProperty isReadProperty() {
        return isRead;
    }

    public void markAsRead() {
        isRead.set(true);
    }
}
