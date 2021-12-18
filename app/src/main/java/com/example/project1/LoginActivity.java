package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText et_phone, et_pass;
    private Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_phone = findViewById(R.id.et_phone);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        et_phone.setInputType(InputType.TYPE_CLASS_PHONE);
        et_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPhone = et_phone.getText().toString();
                String userPass = et_pass.getText().toString();

                if(userPhone.equals("") || userPhone == null && userPass.equals("")||userPass == null) {

                    Toast.makeText(getApplicationContext(), "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success"); // Login.php의 success가 참인지 거짓인지

                                if(success) { // 로그인에 성공한 경우
                                    String userPhone = jsonObject.getString("phone");
                                    String userPass = jsonObject.getString("password");
                                    String userName = jsonObject.getString("name");
                                    String userStamps = jsonObject.getString("stamps");

                                    Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userPhone", userPhone);
                                    intent.putExtra("userPass", userPass);
                                    intent.putExtra("userName", userName);
                                    intent.putExtra("userStamps", userStamps);


                                    startActivity(intent);
                                } else { // 로그인에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(userPhone, userPass, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);

                }
            }
        });
    }
}