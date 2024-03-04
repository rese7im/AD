package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_URL = "http://192.168.1.8/CRUDPHP/create.php"; // Assuming your server is running locally
    private static final String UPDATE_URL = "http://192.168.1.8/CRUDPHP/update.php"; // Assuming your server is running locally

    EditText name, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        name = findViewById(R.id.editTextName);
        age = findViewById(R.id.editTextAge);
    }

    public void insertData(View view) {
        String nameValue = name.getText().toString();
        String ageValue = age.getText().toString();
        SendData sendData = new SendData();
        sendData.execute(nameValue, ageValue);
    }

    public void updateData(View view) {
        String nameValue = name.getText().toString();
        String ageValue = age.getText().toString();
        UpdateData updateData = new UpdateData();
        updateData.execute(nameValue, ageValue);
    }

    private class SendData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String nameValue = params[0];
            String ageValue = params[1];


            try {
                URL url = new URL(SERVER_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                HashMap<String, String> postDataParams = new HashMap<>();
                postDataParams.put("name", nameValue);
                postDataParams.put("age", ageValue);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, String> param : postDataParams.entrySet()) {
                    if (postData.length() != 0)
                        postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                }

                bufferedWriter.write(postData.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Data Inserted Successfully";
                } else {
                    return "Error: " + responseCode;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "Error inserting data";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

    private class UpdateData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String nameValue = params[0];
            String ageValue = params[1];

            try {
                URL url = new URL(UPDATE_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                HashMap<String, String> postDataParams = new HashMap<>();
                postDataParams.put("name", nameValue);
                postDataParams.put("age", ageValue);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, String> param : postDataParams.entrySet()) {
                    if (postData.length() != 0)
                        postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                }

                bufferedWriter.write(postData.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Data Updated Successfully";
                } else {
                    return "Error: " + responseCode;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "Error updating data";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}
