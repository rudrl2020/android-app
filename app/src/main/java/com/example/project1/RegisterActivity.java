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

public class RegisterActivity extends AppCompatActivity {

    private EditText et_phone, et_pass, et_name;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        et_phone = findViewById(R.id.et_phone);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        btn_register = findViewById(R.id.btn_register);

        et_phone.setInputType(InputType.TYPE_CLASS_PHONE);
        et_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // 회원가입 버튼 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다) 해온다.
                String userPhone = et_phone.getText().toString();
                String userPassword = et_pass.getText().toString();
                String userName = et_name.getText().toString();

                if(userPhone.equals("") || userPhone == null && userPassword.equals("") || userPassword == null && userName.equals("")||userName == null) {
                    Toast.makeText(getApplicationContext(), "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success"); // Register.php의 success가 참인지 거짓인지
                                if(success) { // 회원가입 성공한 경우
                                    Toast.makeText(getApplicationContext(), "회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else { // 회원가입에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "회원가입을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    // 서버로 Volley를 이용해서 요청을 함
                    RegisterRequest registerRequest = new RegisterRequest(userPhone, userPassword, userName, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }


            }
        });


    }
}