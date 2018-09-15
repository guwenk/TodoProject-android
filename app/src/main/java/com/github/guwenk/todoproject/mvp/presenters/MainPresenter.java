package com.github.guwenk.todoproject.mvp.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.guwenk.todoproject.mvp.models.MainModel;
import com.github.guwenk.todoproject.mvp.views.MainView;
import com.github.guwenk.todoproject.ui.items.ProjectItem;
import com.github.guwenk.todoproject.ui.items.TodoItem;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private Context mContext;
    private boolean isInitialized = false;
    private ProjectItem[] projectItems;

    public ProjectItem[] getProjectItems() {
        return projectItems;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void init(Context appContext) {
        if (!isInitialized) {
            isInitialized = true;
            mContext = appContext;
            requestData();
        }
    }

    public void requestData() {
        new MainModel(mContext, this).requestData();
        getViewState().showProgressBar();
    }

    public void requestUpdateTodoStatus(int id, boolean isCompleted) {
        new MainModel(mContext, this).requestUpdateTodoStatus(id, isCompleted);
    }

    public void responseData(ProjectItem[] projectItems) {
        this.projectItems = projectItems;
        getViewState().clearData();
        for (ProjectItem project : projectItems) {
            getViewState().addSectionHeaderItem(project.getTitle());
            for (TodoItem todo : project.getTodos()) {
                getViewState().addItem(todo);
            }
        }
        getViewState().showList();
    }

    public void responseDataError() {
        getViewState().showTryAgainButton();
    }

    public void responseTodoStatus(int id, boolean isCompleted) {
        getViewState().updateItem(id, isCompleted);
    }

    public void responseTodoStatusError() {
        getViewState().updateTodoStatusError();
    }
}
