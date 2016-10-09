package com.example.astray.hackatonupc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Registration extends AppCompatActivity {

    public static String email = "0", password = "0", DNI = "0", telefono = "0", name = "0", ONG = "0";
    public Boolean completed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
    }

    public void submit(View view){

        System.out.println("Submit pressed");

        EditText mEdit   = (EditText)findViewById(R.id.editText);
        EditText mEdit2   = (EditText)findViewById(R.id.editText2);
        EditText mEdit3   = (EditText)findViewById(R.id.editText5);
        EditText mEdit4   = (EditText)findViewById(R.id.editText6);
        EditText mEdit5   = (EditText)findViewById(R.id.editText7);
        CheckBox chkIos = (CheckBox) findViewById(R.id.checkBox);

        System.out.println(mEdit.getText().toString());
        System.out.println(mEdit2.getText().toString());
        System.out.println(mEdit3.getText().toString());
        System.out.println(mEdit4.getText().toString());
        System.out.println(mEdit5.getText().toString());

        email = mEdit.getText().toString();
        password = mEdit2.getText().toString();
        DNI = mEdit3.getText().toString();
        telefono = mEdit4.getText().toString();
        name =  mEdit5.getText().toString();

        if (chkIos.isChecked()) {
            ONG = "1";
        }

        Registration.AsyncT asyncT = new Registration.AsyncT();
        asyncT.execute();



    }

    class AsyncT extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (completed) {


            Toast.makeText(getApplicationContext(), "Registration Completed", Toast.LENGTH_SHORT).show();   }
            else{

                Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected Void doInBackground(Void... params) {




            try {
                String url = "http://ec2-54-71-98-237.us-west-2.compute.amazonaws.com/API/createUser.php";
                //String url = "http://ec2-54-71-98-237.us-west-2.compute.amazonaws.com/API/post.php";
                URL object = new URL(url);

                HttpURLConnection con = (HttpURLConnection) object.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("POST");

                JSONObject my_jason = new JSONObject();

                JSONObject my_jason_sebas = new JSONObject();

                try {


                    my_jason.put("email", email);
                    my_jason.put("password", password);
                    my_jason.put("dni", DNI);
                    my_jason.put("telefono", telefono);
                    my_jason.put("ong", ONG);
                    my_jason.put("nombre", name);


                    //my_jason.put("name", "juan");
                    //my_jason.put("pass", "123");

                        /*my_jason.put("titulo","aaa");
                        my_jason.put("usuario","bbb");
                        my_jason.put("cantidad","1.0");
                        my_jason.put("unidad","");
                        my_jason.put("ubicacion","");
                        my_jason.put("caducidad","");
                        my_jason.put("fechapublicacion","asdfadsf");
                        my_jason.put("img","aaa");
                        my_jason.put("descripcion","aaa");
                        my_jason.put("status","true");*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(my_jason.toString());
                wr.flush();

                //display what returns the POST request

                StringBuilder sb = new StringBuilder();
                int HttpResult = con.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(con.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);

                    }
                    br.close();



                    String response = sb.toString();
                    System.out.println(response);




                    //AQUI FALTA IMPLEMENTAR LA GESTIÓN DE LA RESPUESTA POR PARTE DEL SERVIDOR.

                    completed = true;

                    if (completed) {



                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                    startActivity(intent);

                    }









                } else {

                    System.out.println(con.getResponseMessage());

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }


    }


}


