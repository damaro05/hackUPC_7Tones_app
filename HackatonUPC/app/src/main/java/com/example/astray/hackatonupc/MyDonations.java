package com.example.astray.hackatonupc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;


public class MyDonations extends AppCompatActivity {

    //String[] myarray = new String[numberofstrings];
    //myarray[23] = string24;

    String[] titulo =  new String[5], usuario =  new String[5], cantidad =  new String[5], unidad =  new String[5], ubicacion =  new String[5];


    //mylist.add(mystring); //this adds an element to the list. titulo, usuario, cantidad, unidad, ubicacion;

    Boolean loaded = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydonations);

        MyDonations.AsyncT asyncT = new MyDonations.AsyncT();
        asyncT.execute();










        //text2.setText("Izquierda 1");





    }



    public void myCreator(View view){


        System.out.println("New thing");

        Intent intent = new Intent(getApplicationContext(), Poster.class);

        startActivity(intent);




    }




/**********************************/

class AsyncT extends AsyncTask<Void,Void,Void> {



    @Override
    protected Void doInBackground(Void... params) {




        try {
            String url = "http://ec2-54-71-98-237.us-west-2.compute.amazonaws.com/API/getPost.php";
            URL object = new URL(url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            JSONObject my_jason = new JSONObject();



            try {


                my_jason.put("name", "Restaurant La Pineda");


                my_jason.put("ong", "asdfasdf");


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

                try {


                    //titulo.addElement("hola");


                    //System.out.println(titulo.get(0));



                    for(int i = 0; i < 4; i++){

                        //System.out.println(jObj.getJSONObject(i));
                        System.out.println(jObj.getJSONObject(i).getString("titulo"));
                        System.out.println(jObj.getJSONObject(i).getString("usuario"));
                        System.out.println(jObj.getJSONObject(i).getString("cantidad"));
                        System.out.println(jObj.getJSONObject(i).getString("unidad"));
                        System.out.println(jObj.getJSONObject(i).getString("ubicacion"));

                        titulo[i] = jObj.getJSONObject(i).getString("titulo");
                        usuario[i] = jObj.getJSONObject(i).getString("usuario");
                        cantidad[i] = jObj.getJSONObject(i).getString("cantidad");
                        unidad[i] = jObj.getJSONObject(i).getString("unidad");
                        ubicacion[i] = jObj.getJSONObject(i).getString("ubicacion");




                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {




                            TextView text2=(TextView)findViewById(R.id.textView2);
                            text2.setText("");

                            TextView text12=(TextView)findViewById(R.id.textView12);
                            text12.setText("");

                            TextView text13=(TextView)findViewById(R.id.textView13);
                            text13.setText("");

                            TextView text7=(TextView)findViewById(R.id.textView7);
                            text7.setText("");

                            TextView text14=(TextView)findViewById(R.id.textView14);
                            text14.setText("");

                            TextView text15=(TextView)findViewById(R.id.textView15);
                            text15.setText("");

                            TextView text8=(TextView)findViewById(R.id.textView8);
                            text8.setText("");

                            TextView text3=(TextView)findViewById(R.id.textView3);
                            text3.setText("");



                            //this will run on UI thread, so its safe to modify UI views.
                            text2.setText(titulo[0] + ", ");
                            text12.setText(cantidad[0]+" "+unidad[0]+", "+ubicacion[0]);
                            text3.setText(titulo[1] + ", ");
                            text13.setText(cantidad[1]+" "+unidad[1]+", "+ubicacion[1]);
                            text7.setText(titulo[2] + ", ");
                            text14.setText(cantidad[2]+" "+unidad[2]+", "+ubicacion[2]);
                            text8.setText(titulo[3] + ", ");
                            text15.setText(cantidad[3]+" "+unidad[3]+", "+ubicacion[3]);
                        }
                    });

                    loaded = true;

                } catch (JSONException e) {
                    e.printStackTrace();
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





    }






}







}
