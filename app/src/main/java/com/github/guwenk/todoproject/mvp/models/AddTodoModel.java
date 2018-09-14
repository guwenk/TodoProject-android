package com.github.guwenk.todoproject.mvp.models;

import android.content.Context;

import com.github.guwenk.todoproject.R;
import com.github.guwenk.todoproject.mvp.presenters.AddTodoPresenter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class AddTodoModel {
    private Context mContext;
    private AddTodoPresenter mAddTodoPresenter;

    public AddTodoModel(Context context, AddTodoPresenter addTodoPresenter) {
        this.mContext = context;
        this.mAddTodoPresenter = addTodoPresenter;
    }

    public void requestAddTodo(int project_id, String text) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse("{\"todo\":{\"text\": \"" + text + "\", \"project_id\": " + project_id + "}}").getAsJsonObject();
        Ion.with(mContext)
                .load(mContext.getString(R.string.server_url) + "/projects/0/todos/")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e == null) {
                            mAddTodoPresenter.responseAddTodo();
                        } else {
                            mAddTodoPresenter.responseAddTodoError();
                        }
                    }
                });

    }
}