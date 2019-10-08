package com.example.jsonrndtextview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         textView=findViewById(R.id.tv_json);
         jsonObj jsonObj= new jsonObj();
         jsonObj.execute();


    }
    public class jsonObj extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            String name;
            Integer age;
            String classs;

            try {
                URL url= new URL("https://api.myjson.com/bins/145zpd");
                httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream= httpURLConnection.getInputStream();
             bufferedReader =new BufferedReader( new InputStreamReader(inputStream));
                StringBuffer stringBuffer=new StringBuffer();
                String line="";

                StringBuffer lastStringBuffer = new StringBuffer();

                while ((line=bufferedReader.readLine())!= null){
                    stringBuffer.append(line);
                }



                String file =stringBuffer.toString();

                JSONObject jsonObject= new JSONObject(file);
                JSONArray jsonArray= jsonObject.getJSONArray("student_info");

                for ( int i=0;i<jsonObject.length();i++) {
                    JSONObject arrayObject = jsonArray.getJSONObject(i);
                    name = arrayObject.getString("name");
                    age = arrayObject.getInt("age");
                    classs = arrayObject.getString("classs");

                    lastStringBuffer.append(name+"\n"+age+"\n"+classs+"\n");
                }

                return lastStringBuffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                    httpURLConnection.disconnect();
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);

        }
    }
}
