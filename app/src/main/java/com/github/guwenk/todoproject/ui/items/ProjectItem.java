package com.github.guwenk.todoproject.ui.items;

import java.util.ArrayList;

public class ProjectItem {
    private int id;
    private String title;
    private ArrayList<TodoItem> todos;

    ProjectItem(int id, String title, ArrayList<TodoItem> todos) {
        this.id = id;
        this.title = title;
        this.todos = todos;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<TodoItem> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<TodoItem> todos) {
        this.todos = todos;
    }
}
