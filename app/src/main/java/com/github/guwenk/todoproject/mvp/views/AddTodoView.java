package com.github.guwenk.todoproject.mvp.views;

import com.arellomobile.mvp.MvpView;

public interface AddTodoView extends MvpView {
    void finishActivity();

    void showErrorText();

    void showErrorCategory();

    void showErrorUpload();

    void addCategory(String category);

    void showProgressDialog();

    void hideProgressDialog();
}
