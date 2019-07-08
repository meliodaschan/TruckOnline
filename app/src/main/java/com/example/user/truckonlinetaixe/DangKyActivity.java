package com.example.user.truckonlinetaixe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.truckonlinetaixe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.truckonlinetaixe.Constant.URL_BASE;
import static com.example.user.truckonlinetaixe.Constant.getTAG;
import static com.example.user.truckonlinetaixe.Constant.token;
import static com.example.user.truckonlinetaixe.R.layout.activity_dang_ky;

public class DangKyActivity extends AppCompatActivity {

    Spinner spnLoaiXe;
    Spinner spnTrongTaiXe;
    String tenloaixe;
    String trongtai;

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_IDXE = "id_xe";
    private static final String KEY_TEN_CHU_XE = "tenchuxe";
    private static final String KEY_BIENSOXE = "biensoxe";
    private static final String KEY_PASSWORDTX = "passwordtx";
    private static final String KEY_SDT = "sdttx";
    private static final String KEY_MO_TA = "mota";
    private static final String KEY_LOAI_XE = "loaixe";
    private static final String KEY__TRONGTAIXE = "trongtaixe";
    private static final String KEY__TOKEN = "token";
    private static final String KEY_TAIKHOAN = "taikhoan";
    private static final String KEY_EMPTY = "";
    private EditText etBienSoXe;
    private EditText etPasswordtx;
    private EditText etConfirmPassword;
    private EditText etTenChuXe;
    private EditText etSDT;
    private EditText edMoTa;
    private String id_xe;
    private String biensoxe;
    private String passwordtx;
    private String confirmPassword;
    private String tenchuxe;
    private String sdt;
    private String mota;
    private String loaixe;
    private String trongtaixe;

    private ProgressDialog pDialog;
    private String register_url = URL_BASE+"doan/registertx.php";
    private SessionHandler session;

    private ImageView iconBack;

    String urlGetDataLoaiXe = URL_BASE+"doan/getdataloaixe.php";
    String urlGetDataTrongTaiXe = URL_BASE+"doan/getdatatrongtaixe.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_dang_ky);

        GetDataLoaiXe(urlGetDataLoaiXe);
        spnLoaiXe = (Spinner)findViewById(R.id.spnLoaiXedk);

        GetDataTrongTaiXe(urlGetDataTrongTaiXe);
        spnTrongTaiXe = (Spinner)findViewById(R.id.spnTrongTaiXedk);

        etBienSoXe = findViewById(R.id.etBienSoXe);
        etPasswordtx = findViewById(R.id.etPasswordtx);
        etConfirmPassword = findViewById(R.id.etConfirmPasswordtx);
        etTenChuXe = findViewById(R.id.etTenChuXe);
        etSDT = findViewById(R.id.etSDT);
        edMoTa = findViewById(R.id.etMoTa);

        iconBack = findViewById(R.id.iconBack);

        Button register = findViewById(R.id.btnDangKy);

        //Launch Login screen when Login Button is clicked
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DangKyActivity.this, DangNhapActivity.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                biensoxe = etBienSoXe.getText().toString().toLowerCase().trim();
                passwordtx = etPasswordtx.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                tenchuxe = etTenChuXe.getText().toString().trim();
                sdt = etSDT.getText().toString().trim();
                mota = edMoTa.getText().toString().trim();
                loaixe = tenloaixe;
                trongtaixe = trongtai;
                if (validateInputs()) {
                    registerUser();
                }

            }
        });

    }

    private void GetDataLoaiXe(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String result =  response.toString();
                        final List<LoaiXe> loaiXeList = new ArrayList<>();
                        List<String> loaiXeName = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                loaiXeList.add(new LoaiXe( explrObject.getInt("ID_lx"), explrObject.getString("TenLoaiXe")));
                                loaiXeName.add(explrObject.getString("TenLoaiXe"));
                            }
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item, loaiXeName);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnLoaiXe.setAdapter(arrayAdapter);
                            spnLoaiXe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    tenloaixe =loaiXeList.get(position).getTenLoaiXe();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangKyActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


    private void GetDataTrongTaiXe(String url){
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String result =  response.toString();
                        final List<TrongTaiXe> trongTaiXeList = new ArrayList<>();
                        List<String> trongTaiXeName = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                trongTaiXeList.add(new TrongTaiXe( explrObject.getInt("ID_ttx"), explrObject.getString("TrongTai")));
                                trongTaiXeName.add(explrObject.getString("TrongTai"));
                            }
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item, trongTaiXeName);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnTrongTaiXe.setAdapter(arrayAdapter);
                            spnTrongTaiXe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    trongtai =trongTaiXeList.get(position).getTrongTai();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangKyActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(DangKyActivity.this);
        pDialog.setMessage("Vui lòng đợi trong giây lát...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
//    private void loadDashboard() {
//        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
//        startActivity(i);
//        finish();
//
//    }

    private void loadDangNhap() {
        Intent i = new Intent(getApplicationContext(), DangNhapActivity.class);
        startActivity(i);
        finish();

    }

    private void registerUser() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_IDXE, id_xe);
            request.put(KEY_BIENSOXE, biensoxe);
            request.put(KEY_PASSWORDTX, passwordtx);
            request.put(KEY_TEN_CHU_XE, tenchuxe);
            request.put(KEY_SDT, sdt);
            request.put(KEY_MO_TA, mota);
            request.put(KEY_LOAI_XE, loaixe);
            request.put(KEY__TRONGTAIXE, trongtaixe);
            request.put(KEY__TOKEN,  token);
            Log.d(getTAG(DangKyActivity.class),request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
//                                session.loginUser(biensoxe,loaixe,trongtaixe,tenchuxe,sdt,mota,token);
                                loadDangNhap();

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existsing
                                etBienSoXe.setError("Biển số xe đã tồn tại!");
                                etBienSoXe.requestFocus();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(tenchuxe)) {
            etTenChuXe.setError("Vui lòng điền Tên chủ xe");
            etTenChuXe.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(sdt)) {
            etSDT.setError("Vui lòng điền Số điện thoại");
            etSDT.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(mota)) {
            edMoTa.setError("Vui lòng điền Mô tả xe");
            edMoTa.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(biensoxe)) {
            etBienSoXe.setError("Vui lòng điền Biển số xe");
            etBienSoXe.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(passwordtx)) {
            etPasswordtx.setError("Vui lòng điền Mật khẩu");
            etPasswordtx.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            etConfirmPassword.setError("Vui lòng điền Xác nhận mật khẩu");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!passwordtx.equals(confirmPassword)) {
            etConfirmPassword.setError("Xác nhận mật khẩu sai !");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }



}