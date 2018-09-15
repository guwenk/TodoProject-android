package com.github.guwenk.todoproject.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.github.guwenk.todoproject.ui.items.ProjectItem;
import com.github.guwenk.todoproject.ui.items.TodoItem;

public interface MainView extends MvpView {
    void addItem(TodoItem item);

    void addSectionHeaderItem(String name);

    void clearData();

    void updateItem(int id, boolean isComplete);

    void updateTodoStatusError();

    void showList();

    void showProgressBar();

    void showTryAgainButton();
}
