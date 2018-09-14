package com.github.guwenk.todoproject.ui.items;

public class TodoItem {
    private int id;
    private String text;
    private boolean isCompleted;

    public TodoItem(int id, String title, boolean isCompleted) {
        this.id = id;
        this.text = title;
        this.isCompleted = isCompleted;
    }

    public TodoItem() {
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean complete) {
        isCompleted = complete;
    }

    public TodoItem clone(TodoItem item) {
        this.id = item.getId();
        this.text = item.getText();
        this.isCompleted = item.isCompleted();
        return this;
    }
}
