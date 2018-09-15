package com.github.guwenk.todoproject.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.guwenk.todoproject.R;
import com.github.guwenk.todoproject.mvp.presenters.AddTodoPresenter;
import com.github.guwenk.todoproject.mvp.views.AddTodoView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class AddTodoActivity extends MvpAppCompatActivity implements AddTodoView {

    @InjectPresenter
    AddTodoPresenter mAddTodoPresenter;

    ArrayAdapter<String> mAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        if (!mAddTodoPresenter.isInitialized()) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Toast.makeText(this, "No extras", Toast.LENGTH_SHORT).show();
                finish();
            }
            mAddTodoPresenter.init(getApplicationContext(), extras.getString("obj", "{}"));
        }

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.action_bar_add_todo);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        mAdapter = new ArrayAdapter<String>(this, R.layout.lv_category_item);
        ListView lv = findViewById(R.id.add_todo_listView);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAddTodoPresenter.setCategory(position + 1);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Отправка данных...");
        progressDialog.setCancelable(false);

        EditText editText = findViewById(R.id.add_todo_editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAddTodoPresenter.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent();
        intent.putExtra("updated", false);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_todo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                mAddTodoPresenter.requestAddTodo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra("updated", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showErrorText() {
        ((TextView) findViewById(R.id.add_todo_editText)).setError("Введите текст");
    }

    @Override
    public void showErrorCategory() {
        Toast.makeText(this, "Выберете категорию", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorUpload() {
        Toast.makeText(this, "Не удалось загрузить данные.\nПовторите попытку", Toast.LENGTH_LONG).show();
    }

    @Override
    public void addCategory(String category) {
        mAdapter.add(category);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }
}
