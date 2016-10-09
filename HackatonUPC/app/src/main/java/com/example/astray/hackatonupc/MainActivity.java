package com.example.astray.hackatonupc;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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


public class MainActivity extends AppCompatActivity {

    Boolean login = false;
    String user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void goToMyDonations(View view)
    {
        Intent intent = new Intent(getApplicationContext(), MyDonations.class);
        //intent.putExtra("epc", firstAnimalEpc);

        startActivity(intent);

        System.out.println("We go to to MyDonations!");
    }

    public void testPost(View view){

        System.out.println("Post Started");

        AsyncT asyncT = new AsyncT();
        asyncT.execute();

    }

    public void registration(View view){

        Intent intent = new Intent(getApplicationContext(), Registration.class);
        //intent.putExtra("epc", firstAnimalEpc);

        startActivity(intent);


    }



        class AsyncT extends AsyncTask<Void,Void,Void>{

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(!login){

                    Toast.makeText(getApplicationContext(), "Authentication  Failed", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            protected Void doInBackground(Void... params) {




                try {
                    String url = "http://ec2-54-71-98-237.us-west-2.compute.amazonaws.com/API/login.php";
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


                        my_jason.put("name", user);
                        my_jason.put("pass", password);

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


                        // 3 y 15 son las longitudes de los strings de las respuestas del servidore acceptada y rechazada respectivamente.





                        String response = sb.toString();
                        response = response.replace(" ","");
                        System.out.println(response);
                        System.out.println("Longitud: "+response.length());

                        if(response.equals("1"))
                        {
                            //Autentificación confirmada
                            login = true;


                            Intent intent = new Intent(getApplicationContext(), MyDonations.class);

                            startActivity(intent);


                        }

                        if(response.equals("2"))
                        {
                            //Autentificación confirmada
                            login = true;


                            Intent intent2 = new Intent(getApplicationContext(), MyOngView.class);

                            startActivity(intent2);

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
                EditText mEdit   = (EditText)findViewById(R.id.editText3);
                EditText mEdit2   = (EditText)findViewById(R.id.editText4);

                System.out.println(mEdit.getText().toString());
                System.out.println(mEdit2.getText().toString());

                user = mEdit.getText().toString();
                password = mEdit2.getText().toString();



            }


        }




































}
