package com.example.loan_project_app_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText person_income, person_home_ownership, person_emp_length, loan_grade, loan_amnt, loan_int_rate, loan_percent_income, cb_person_default_on_file;
    Button predict;
    TextView result;
    String url = "https://loan-shashank-predictor-app.herokuapp.com/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        person_income = findViewById(R.id.person_income);
        person_home_ownership = findViewById(R.id.person_home_ownership);
        person_emp_length = findViewById(R.id.person_emp_length);
        loan_grade = findViewById(R.id.loan_grade);
        loan_amnt = findViewById(R.id.loan_amnt);
        loan_int_rate = findViewById(R.id.loan_int_rate);
        loan_percent_income = findViewById(R.id.loan_percent_income);
        cb_person_default_on_file = findViewById(R.id.cb_person_default_on_file);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hit the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("Defaulter");
                                    if(data.equals("1")){
                                        result.setText("Won't return the Loan ");
                                    }else {
                                        result.setText("Will return the Loan ");
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){

                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("person_income",person_income.getText().toString());
                        params.put("person_home_ownership",person_home_ownership.getText().toString());
                        params.put("person_emp_length",person_emp_length.getText().toString());
                        params.put("loan_grade",loan_grade.getText().toString());
                        params.put("loan_amnt",loan_amnt.getText().toString());
                        params.put("loan_int_rate",loan_int_rate.getText().toString());
                        params.put("loan_percent_income",loan_percent_income.getText().toString());
                        params.put("cb_person_default_on_file",cb_person_default_on_file.getText().toString());

                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });

    }
}