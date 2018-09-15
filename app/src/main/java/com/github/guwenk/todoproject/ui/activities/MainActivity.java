package com.github.guwenk.todoproject.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.guwenk.todoproject.R;
import com.github.guwenk.todoproject.mvp.presenters.MainPresenter;
import com.github.guwenk.todoproject.mvp.views.MainView;
import com.github.guwenk.todoproject.ui.adapters.TodoListAdapter;
import com.github.guwenk.todoproject.ui.items.TodoItem;
import com.google.gson.Gson;

import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mMainPresenter;

    int ADD_TODO_REQUEST_CODE = 1;

    ListView mListView;
    TodoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_main);

        mListView = (ListView) findViewById(R.id.main_ListView);
        mAdapter = new TodoListAdapter(this, mMainPresenter);

        mListView.setAdapter(mAdapter);

        if (!mMainPresenter.isInitialized())
            mMainPresenter.init(getApplicationContext());

    }

    public void fabOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
        intent.putExtra("obj", new Gson().toJson(mMainPresenter.getProjectItems()));
        startActivityForResult(intent, ADD_TODO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        if (data.getBooleanExtra("updated", true))
            mMainPresenter.requestData();
    }

    public void btnTryAgainOnClick(View view) {
        mMainPresenter.requestData();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void addItem(TodoItem item) {
        mAdapter.addItem(item);
    }

    @Override
    public void addSectionHeaderItem(String section) {
        mAdapter.addSectionHeaderItem(new TodoItem(0, section, false));
    }

    @Override
    public void clearData() {
        mAdapter.clearData();
    }

    @Override
    public void updateItem(int id, boolean isComplete) {
        mAdapter.updateItem(id, isComplete);
    }

    @Override
    public void updateTodoStatusError() {
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Ошибка обновления todo статуса", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showList() {
        findViewById(R.id.main_progressBar).setVisibility(View.GONE);
        findViewById(R.id.main_try_again_button).setVisibility(View.GONE);
        findViewById(R.id.main_errorTV).setVisibility(View.GONE);
        findViewById(R.id.main_ListView).setVisibility(View.VISIBLE);
        findViewById(R.id.main_floatingActionButton).setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        findViewById(R.id.main_ListView).setVisibility(View.GONE);
        findViewById(R.id.main_floatingActionButton).setVisibility(View.GONE);
        findViewById(R.id.main_try_again_button).setVisibility(View.GONE);
        findViewById(R.id.main_errorTV).setVisibility(View.GONE);
        findViewById(R.id.main_progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    public void showTryAgainButton() {
        findViewById(R.id.main_ListView).setVisibility(View.GONE);
        findViewById(R.id.main_floatingActionButton).setVisibility(View.GONE);
        findViewById(R.id.main_progressBar).setVisibility(View.GONE);
        findViewById(R.id.main_try_again_button).setVisibility(View.VISIBLE);
        findViewById(R.id.main_errorTV).setVisibility(View.VISIBLE);
    }
}
