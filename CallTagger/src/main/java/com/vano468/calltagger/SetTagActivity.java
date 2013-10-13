package com.vano468.calltagger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class SetTagActivity extends Activity {

    static String CallNumber = "CallNumber";

    String[] data = {"", "#one", "#two", "#three", "#four"};

    EditText tagEdit;
    Spinner tagSpinner;
    Button saveTag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_tag_activity);

        tagSpinner = (Spinner) findViewById(R.id.tagSpinner);
        tagEdit = (EditText) findViewById(R.id.tagEdit);
        saveTag = (Button) findViewById(R.id.saveTag);

        setTagSpinnerArray();
        setTagSpinnerListener();
        setTagEditListener();
        setSaveTagListener();
    }

    private void setTagSpinnerArray() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, data);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        tagSpinner.setAdapter(adapter);
    }

    private void setTagSpinnerListener() {
        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                if (position > 0) {
                    tagEdit.setFocusable(false);
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(tagEdit.getWindowToken(), 0);
                }
                else {
                    tagEdit.setFocusableInTouchMode(true);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    private void setTagEditListener() {
        tagEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (tagEdit.getText().toString().length() > 0) {
                    ((Spinner) tagSpinner).getSelectedView().setEnabled(false);
                    ((Spinner) tagSpinner).setEnabled(false);
                } else {
                    ((Spinner) tagSpinner).getSelectedView().setEnabled(true);
                    ((Spinner) tagSpinner).setEnabled(true);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void setSaveTagListener() {
        saveTag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickSaveButton();
            }
        });
    }

    private void onClickSaveButton() {
        Intent intent = getIntent();
        String callNumber = intent.getStringExtra(CallNumber);
        setTitle(callNumber);

        DBWorker dbWorker = new DBWorker(this);
        dbWorker.insert(callNumber, "#" + tagEdit.getText().toString().replace("#", ""));

        finish();
    }
}
