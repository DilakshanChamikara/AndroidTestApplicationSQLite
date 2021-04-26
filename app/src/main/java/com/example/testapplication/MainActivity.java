package com.example.testapplication;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editId, editName, editEmail, editMarks;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editId = (EditText)findViewById(R.id.editTextId);
        editName = (EditText)findViewById(R.id.editTextName);
        editEmail = (EditText)findViewById(R.id.editTextEmail);
        editMarks = (EditText)findViewById(R.id.editTextMarks);
        btnAddData = (Button)findViewById(R.id.buttonAdd);
        btnViewAll = (Button)findViewById(R.id.buttonView);
        btnUpdate = (Button)findViewById(R.id.buttonUpdate);
        btnDelete = (Button)findViewById(R.id.buttonDelete);
        AddData();
        ViewAll();
        UpdateData();
        DeleteDataById();
    }

    public void AddData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editName.getText().toString(),
                        editEmail.getText().toString(),
                        editMarks.getText().toString());
                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Data Inserted.", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Inserted.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ViewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0){
                    //show message
                    showMessage("Error", "No Data Found.");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n");
                    buffer.append("Email :" + res.getString(2) + "\n");
                    buffer.append("Marks :" + res.getString(3) + "\n\n");
                }
                //show All data
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editId.getText().toString(),
                        editName.getText().toString(),
                        editEmail.getText().toString(),
                        editMarks.getText().toString());
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data Updated.", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Updated.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void DeleteDataById(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editId.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(MainActivity.this, "Data Deleted.", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Deleted.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}