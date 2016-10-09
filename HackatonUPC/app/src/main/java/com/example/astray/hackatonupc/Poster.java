package com.example.astray.hackatonupc;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.data;

public class Poster  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);


    }

    public void sendPost(View view) {

        Poster.AsyncT asyncT = new Poster.AsyncT();
        asyncT.execute();


    }






    class AsyncT extends AsyncTask<Void,Void,Void> {



        @Override
        protected Void doInBackground(Void... params) {




            try {
                String url = "http://ec2-54-71-98-237.us-west-2.compute.amazonaws.com/API/post.php";
                URL object = new URL(url);

                HttpURLConnection con = (HttpURLConnection) object.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("POST");

                JSONObject my_jason = new JSONObject();



                try {



                    my_jason.put("titulo", "Arròs amb llet");
                    my_jason.put("usuario", "Aitor P.");
                    my_jason.put("cantidad", "2");
                    my_jason.put("unidad", "Kg");
                    my_jason.put("ubicacion", "Valencia, 45");
                    my_jason.put("caducidad", "04-01-2017");
                    my_jason.put("fechapublicacion", "09-10-2016");
                    my_jason.put("img", "ruta");
                    my_jason.put("descripcion", "6 raciones");
                    my_jason.put("status", "bueno");



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

                    String json = sb.toString();

                    JSONArray jObj = null;
                    try {
                        jObj = new JSONArray(json);
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
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


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            Toast.makeText(getApplicationContext(), "POST sent to server", Toast.LENGTH_SHORT).show();

        }






    }





}
