package com.mul_alexautoprogramm.clientlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mul_alexautoprogramm.clientlist.database.AppDataBase;
import com.mul_alexautoprogramm.clientlist.database.AppExecutor;
import com.mul_alexautoprogramm.clientlist.database.Client;
import com.mul_alexautoprogramm.clientlist.utils.Constance;

public class EditActivity extends AppCompatActivity {
    private AppDataBase mayDataBase;
    private EditText edName,edSecName,edTelNumb,edDesc;
    private CheckBox cbImportant,cbNormal,cbNoImportant,cbSpecial;
    private int importance = 3;
    private int special = 0;
    private FloatingActionButton fabSave;
    private CheckBox[] checkBoxesArray = new CheckBox[3];
    private boolean isEdit = false;
    private boolean newUser = false;
    private int id;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        init();
        getMainIntent();

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImportanceFromCb();
                //write info user in newThread
                if (!TextUtils.isEmpty(edName.getText().toString())
                        && !TextUtils.isEmpty(edSecName.getText().toString())
                        && !TextUtils.isEmpty(edTelNumb.getText().toString())
                        && !TextUtils.isEmpty(edDesc.getText().toString())
                ) {
                    AppExecutor.getInstance().getDiscIO().execute(() -> {
                        if (isEdit) {
                            Client client = new Client(
                                    edName.getText().toString(),
                                    edSecName.getText().toString(),
                                    edTelNumb.getText().toString(),
                                    importance,
                                    edDesc.getText().toString(),
                                    special);

                            client.setId(id);
                            mayDataBase.clientDAO().updateClient(client);
                            finish();
                        } else {

                            Client client = new Client(
                                    edName.getText().toString(),
                                    edSecName.getText().toString(),
                                    edTelNumb.getText().toString(),
                                    importance,
                                    edDesc.getText().toString(),
                                    special);

                            mayDataBase.clientDAO().insertClient(client);
                            finish();
                        }

                    });

                }
            }
        });
    }

    private void init(){
        fabSave = findViewById(R.id.fbSave);
        mayDataBase = AppDataBase.getInstanceDB(getApplicationContext());
        edName = findViewById(R.id.edTextName);
        edSecName = findViewById(R.id.edTextSecondName);
        edTelNumb = findViewById(R.id.edTextTelNumber);
        edDesc = findViewById(R.id.edDescryption);

        cbImportant = findViewById(R.id.cbImportant);
        cbNormal = findViewById(R.id.cbNormal);
        cbNoImportant = findViewById(R.id.cbNoImportant);
        cbSpecial = findViewById(R.id.cbSpecial);

        checkBoxesArray[0] = cbImportant;
        checkBoxesArray[1] = cbNormal;
        checkBoxesArray[2] = cbNoImportant;

    }

    //Onclick

    public void onClickcb1(View view){

        cbNormal.setChecked(false);
        cbNoImportant.setChecked(false);

    }

    public void onClickcb2(View view){

        cbImportant.setChecked(false);
        cbNoImportant.setChecked(false);


    }

    public void onClickcb3(View view){

        cbImportant.setChecked(false);
        cbNormal.setChecked(false);


    }

    private void getImportanceFromCb(){

        if(cbImportant.isChecked()){
            importance = 0;
        }else if(cbNormal.isChecked()){
            importance = 1;
        }else if (cbNoImportant.isChecked()){
            importance = 2;
        }


        if(cbSpecial.isChecked()){
            special = 1;
        }
    }
    //intent for Main
    private void getMainIntent(){

        Intent i = getIntent();
        if(i != null){

            if(i.getStringExtra(Constance.NAME_KEY) != null){

                setIsEditVisible(false);
                edName.setText(i.getStringExtra(Constance.NAME_KEY));
                edSecName.setText(i.getStringExtra(Constance.SEC_NAME_KEY));
                edTelNumb.setText(i.getStringExtra(Constance.TEL_KEY));
                edDesc.setText(i.getStringExtra(Constance.DESC_KEY));

                checkBoxesArray[i.getIntExtra(Constance.IMPORTANCE_KEY, 0)].setChecked(true);

                if(i.getIntExtra(Constance.SPECIAL_KEY, 0) == 1) cbSpecial.setChecked(true);
                newUser = false;
                id = i.getIntExtra(Constance.ID_KEY, 0);

            }else {
                newUser = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!newUser) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }
    //on click Edit/Delete
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.id_edit){

            setIsEditVisible(true);

        }else if ( id == R.id.id_delete){

            deleteDialog();

        }

        return true;

    }

    private void setIsEditVisible(boolean isEditVisible){

        if(isEditVisible){

            fabSave.show();

        }else{

            fabSave.hide();

        }
        isEdit = isEditVisible;
        cbImportant.setClickable(isEditVisible);
        cbNormal.setClickable(isEditVisible);
        cbNoImportant.setClickable(isEditVisible);
        cbSpecial.setClickable(isEditVisible);

        edName.setClickable(isEditVisible);
        edName.setFocusable(isEditVisible);
        edName.setFocusableInTouchMode(isEditVisible);

        edSecName.setClickable(isEditVisible);
        edSecName.setFocusable(isEditVisible);
        edSecName.setFocusableInTouchMode(isEditVisible);

        edTelNumb.setClickable(isEditVisible);
        edTelNumb.setFocusable(isEditVisible);
        edTelNumb.setFocusableInTouchMode(isEditVisible);

        edDesc.setFocusable(isEditVisible);
        edDesc.setFocusable(isEditVisible);
        edDesc.setFocusableInTouchMode(isEditVisible);

    }

    private void deleteDialog(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(R.string.delete_message);
        b.setTitle(R.string.delete);

        b.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppExecutor.getInstance().getDiscIO().execute(() -> {
                        Client client = new Client(
                                edName.getText().toString(),
                                edSecName.getText().toString(),
                                edTelNumb.getText().toString(),
                                importance,
                                edDesc.getText().toString(),
                                special);

                        client.setId(id);
                        mayDataBase.clientDAO().deleteClient(client);
                        finish();

                });
            }
        });

        b.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        b.show();

    }

}
