package com.github.guwenk.todoproject.mvp.models;

import android.content.Context;

import com.github.guwenk.todoproject.R;
import com.github.guwenk.todoproject.mvp.presenters.MainPresenter;
import com.github.guwenk.todoproject.ui.items.ProjectItem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainModel {
    private Context mContext;
    private MainPresenter mMainPresenter;

    public MainModel(Context context, MainPresenter mainPresenter) {
        this.mContext = context;
        this.mMainPresenter = mainPresenter;
    }

    public void requestData() {
        Ion.with(mContext)
                .load(mContext.getString(R.string.server_url) + "/projects.json")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e == null) {
                            String json = result.toString();
                            Gson gson = new Gson();
                            ProjectItem[] projectItems = gson.fromJson(json, ProjectItem[].class);
                            mMainPresenter.responseData(projectItems);
                        } else {
                            mMainPresenter.responseDataError();
                        }
                    }
                });
    }

    public void requestUpdateTodoStatus(final int id, final boolean isCompleted) {
        JsonObject json = new JsonObject();
        json.addProperty("isCompleted", isCompleted);
        Ion.with(mContext)
                .load(mContext.getString(R.string.server_url) + "/projects/0/todos/" + id + "/toggle")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e == null) {
                            mMainPresenter.responseTodoStatus(id, isCompleted);
                        } else {
                            mMainPresenter.responseTodoStatusError();
                        }
                    }
                });
    }
}
