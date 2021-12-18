 package com.example.project1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CouponAddRequest extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동) , final은 재할당할 수 없다, static은 변화가 없고 해당 객체를 공유하겠다 
    final static private String URL = "http://175.121.166.150:8000/capston/application/CouponAdd.php";
    private Map<String, String> map;

    public CouponAddRequest(String userPhone, String userStamps, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userPhone", userPhone);
        map.put("userStamps", userStamps);
    };

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
} 