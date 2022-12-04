package com.example.bookinghotel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    String DATABASE_NAME="hotel.db";
    private MySharedPreferences mySharedPreferences;
    private static final String KEY_FIRSTINSTALL_APP = "KEY_FIRSTINSTALL_APP";
    Database database;
    EditText txtUser, txtPass1, txtPass2;
    TextView txtRegister, txtEnterpass, txtIncorrect;
    Button btnLogin, btnRegister;
    ArrayList<DataUser> dataUser;
    String userName, passWord, enterPassWord;
    boolean check = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        processCopy();
        loadData();
        khoitao();
        if (mySharedPreferences.getBooleanValue(KEY_FIRSTINSTALL_APP) == false){
            mySharedPreferences.putBooleanValue(KEY_FIRSTINSTALL_APP, true);
            callActivity(0);
        }
    }
    public void callActivity(int number){
        Intent intent, intent1;
        switch (number){
            case 0:
                intent = new Intent(LoginActivity.this, OnboardingActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent1 = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent1);
                break;
        }
    }
    public void sendData(DataUser dataUser){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", dataUser);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try{CopyDataBaseFromAsset();
                //Toast.makeText(this, "Copying success from Assets folder", Toast.LENGTH_LONG).show();
                Log.d("status", "Copying success from Assets folder");
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }
    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);

            String outFileName = getDatabasePath();

            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();

            OutputStream myOutput = new FileOutputStream(outFileName);
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadData(){
        dataUser = new ArrayList<>();
        try {
            database = new Database(this, "hotel.db", null, 1);
            database.QueryData("CREATE TABLE IF NOT EXISTS login(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(255), password VARCHAR(255))");
            Cursor a = database.GetData("SELECT * FROM login");
            while (a.moveToNext()){
                String taikhoan = a.getString(1);
                String matkhau = a.getString(2);
                dataUser.add(new DataUser(taikhoan, matkhau));
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void khoitao(){
        mySharedPreferences = new MySharedPreferences(this);
        txtUser = (EditText) findViewById(R.id.editTextUserName);
        txtPass1 = (EditText) findViewById(R.id.editTextPassword);
        txtPass2 = (EditText) findViewById(R.id.editTextPassword2);
        txtRegister = (TextView) findViewById(R.id.textViewRegister);
        txtEnterpass = (TextView) findViewById(R.id.textViewEnterPass);
        txtIncorrect = (TextView) findViewById(R.id.textViewIncorrect);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnRegister = (Button) findViewById(R.id.buttonRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = txtUser.getText().toString();
                passWord = txtPass1.getText().toString();
                if (userName.equals("")){
                    txtUser.setError("Please Enter Your Name");
                }
                else if (passWord.equals("")){
                    txtPass1.setError("Please Enter Pass Word");
                }
                else {
                    for(DataUser data: dataUser)
                    {
                        if (userName.equals("admin") && passWord.equals("admin")){
                            callActivity(1);
                        }
                        else if (userName.equals(data.getUser()) && passWord.equals(data.getPass())) {
                            Log.d("status", "Login thanh cong");
                            sendData(data);
                        }
                        else {
                            txtIncorrect.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkRegister = true;
                userName = txtUser.getText().toString().trim();
                passWord = txtPass1.getText().toString().trim();
                enterPassWord = txtPass2.getText().toString().trim();
                if (userName.equals("")){
                    txtUser.setError("Please Enter Your Name");
                }
                else if (passWord.equals("")){
                    txtPass1.setError("Please Enter Pass Word");
                }
                else if (enterPassWord.equals("")){
                    txtPass2.setError("Please Enter Pass Word");
                }
                else {
                    for(DataUser data: dataUser)
                    {
                        if(userName.equals(data.getUser())) {
                            Toast.makeText(LoginActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            checkRegister = false;
                            break;
                        }
                        else {
                            checkRegister = true;
                        }
                    }
                    if (checkRegister == true){
                        if (passWord.equals(enterPassWord)){
                            database.QueryData("INSERT INTO login VALUES(null, '"+ userName +"', '"+ passWord +"')");
                            Dialog dialog = new Dialog(LoginActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_login);
                            Button btndlLogin = (Button) dialog.findViewById(R.id.buttondlLogin);
                            Window window = dialog.getWindow();
                            if (window == null){
                                return;
                            }
                            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                            btndlLogin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.cancel();
                                    logINlogOUt();
                                }
                            });
                            dataUser.clear();
                            loadData();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Mật khẩu nhập vào không trùng nhau", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logINlogOUt();
            }
        });
    }
    public void logINlogOUt(){
        if (check == true) {
            check = false;
            txtUser.setText("");
            txtPass1.setText("");
            txtPass2.setText("");
            txtIncorrect.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.GONE);
            txtPass2.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
            txtEnterpass.setVisibility(View.VISIBLE);
            txtRegister.setText(R.string.yes_account);
        }
        else {
            check = true;
            txtUser.setText("");
            txtPass1.setText("");
            txtPass2.setText("");
            txtIncorrect.setVisibility(View.INVISIBLE);
            btnRegister.setVisibility(View.GONE);
            txtPass2.setVisibility(View.GONE);
            txtEnterpass.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            txtRegister.setText(R.string.no_account);
        }
    }
}