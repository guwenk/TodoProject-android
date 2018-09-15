package com.github.guwenk.todoproject.mvp.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.guwenk.todoproject.mvp.models.AddTodoModel;
import com.github.guwenk.todoproject.mvp.views.AddTodoView;
import com.github.guwenk.todoproject.ui.items.ProjectItem;
import com.google.gson.Gson;

@InjectViewState
public class AddTodoPresenter extends MvpPresenter<AddTodoView> {

    private boolean isInitialized = false;
    private Context mContext;
    private String text = "";
    private int project_id = 0;

    public boolean isInitialized() {
        return isInitialized;
    }

    public void init(Context appContext, String json) {
        if (!isInitialized) {
            isInitialized = true;
            mContext = appContext;
            ProjectItem[] projectItems = new Gson().fromJson(json, ProjectItem[].class);
            for (ProjectItem item : projectItems) {
                getViewState().addCategory(item.getTitle());
            }
        }
    }

    public void requestAddTodo() {
        if (text.equals("")) {
            getViewState().showErrorText();
            return;
        }
        if (project_id <= 0) {
            getViewState().showErrorCategory();
            return;
        }
        getViewState().showProgressDialog();
        new AddTodoModel(mContext, this).requestAddTodo(project_id, text);
    }

    public void setCategory(int project_id) {
        this.project_id = project_id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void responseAddTodo() {
        getViewState().hideProgressDialog();
        getViewState().finishActivity();
    }

    public void responseAddTodoError() {
        getViewState().hideProgressDialog();
        getViewState().showErrorUpload();
    }
}
