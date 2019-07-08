package com.example.user.truckonlinetaixe;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.user.truckonlinetaixe.Constant.URL_BASE;
import static com.example.user.truckonlinetaixe.Constant.getTAG;
import static com.example.user.truckonlinetaixe.Constant.id_kh;
import static com.example.user.truckonlinetaixe.Constant.token;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener {

    String TAG = MapsActivity.class.getSimpleName();

    EditText etTenMatHang;
    EditText etLoaiHang;
    EditText etKhoiLuongHang;
    EditText etGiaCuoc;


    AutoCompleteTextView edtDiemDi;
    AutoCompleteTextView edtDiemDen;

    ExpandableRelativeLayout expandableLayout;

    Spinner spnLoaiXe;
    Spinner spnTrongTaiXe;
    String tenloaixe;
    String trongtai;

    private String id_xe;
    private String tenmathang;
    private String loaihang;
    private String khoiluonghang;
    private String thoigiankhoihanh;
    private String giacuoc;
    private String loaixe;
    private String trongtaixe;
    private String diemdi;
    private String diemden;

    double kinhdo;
    double vido;

    double latDiemDi;
    double lngDiemDi;
    double latDiemDen;
    double lngDiemDen;
    String diemDen;
    String diemDi;
    float quangduong;

    EditText etThoiGian;

    private GoogleMap mMap;

    private DrawerLayout drawer;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;

    Marker marker;

    String urlGetDataLoaiXe = URL_BASE+"doan/getdataloaixe.php";
    String urlGetDataTrongTaiXe = URL_BASE+"doan/getdatatrongtaixe.php";
    String urlGetDiaDiem = URL_BASE+"doan/getdatadiadiem.php";
    String urlGetDiaDiemTaiXe = URL_BASE+"doan/getvitritx.php";
    String urlSendPush = URL_BASE+"doan/send_push.php";
    String urlPostDataChuyenXe = URL_BASE+"doan/postdatachuyenxe.php";
    String urlPostViTriHienTai = URL_BASE+"doan/capnhatvitrihientai.php";

    private SessionHandlerChuyenXe session;
    private SessionHandlerKH sessionkh;
    public static Handler mHandlerThread;
    public static Handler mHandlerThread3;

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    private List<DiaDiem> diaDiemList;

    String tmp="1";

    private ProgressDialog pDialog;

    String tokentxgannhat;
    String idtxgannhat;
    String loaixetxhannhat;
    String trongtaixetxhannhat;

    TaiXe taiXe;
    List<TaiXe> vtri = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandlerChuyenXe(getApplicationContext());
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        etTenMatHang = (EditText) findViewById(R.id.etTenMH_DatXe);
        etLoaiHang = (EditText) findViewById(R.id.etLoaiHang);
        etKhoiLuongHang = (EditText) findViewById(R.id.etKhoiLuongHang);
        etGiaCuoc = (EditText) findViewById(R.id.etGiaCuoc);


        GetDataLoaiXe(urlGetDataLoaiXe);
        spnLoaiXe = (Spinner)findViewById(R.id.spnLoaiXe);

        GetDataTrongTaiXe(urlGetDataTrongTaiXe);
        spnTrongTaiXe = (Spinner)findViewById(R.id.spnTrongTaiXe);

        etThoiGian = (EditText) findViewById(R.id.etThoiGian);
        etThoiGian.setOnClickListener(v -> ChonNgay());

        getLocationPermission();
        sessionkh = new SessionHandlerKH(getApplicationContext());
        KhachHang user = sessionkh.getUserDetails();
        id_kh = user.getId_khachhang();

        edtDiemDi =(AutoCompleteTextView) findViewById(R.id.edtDiemDi);
        edtDiemDen =(AutoCompleteTextView) findViewById(R.id.edtDiemDen);

        GetDiaDiem(urlGetDiaDiem);
        GetTaiXeGanNhat(urlGetDiaDiemTaiXe);

        findViewById(R.id.btnDatXe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenmathang = etTenMatHang.getText().toString().trim();
                loaihang = etLoaiHang.getText().toString().trim();
                khoiluonghang = etKhoiLuongHang.getText().toString();
                giacuoc = etGiaCuoc.getText().toString();
                thoigiankhoihanh = etThoiGian.getText().toString();
                loaixe = tenloaixe;
                trongtaixe = trongtai;
                diemdi = diemDi;
                diemden = diemDen;
                id_xe = idtxgannhat;
                if (validateInputs()) {
                    DatXe();
                    guiThongBaoChoTaiXe(tokentxgannhat);
                }
            }
        });


        mHandlerThread = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(MapsActivity.this, msg.arg1+" ", Toast.LENGTH_SHORT).show();

                vtri.remove(taiXe);
                taiXe = TimTaiXeGanNhat(vtri);
                tokentxgannhat = taiXe.getToken();
                idtxgannhat = taiXe.getId_xe();
                loaixetxhannhat = taiXe.getLoaixe();
                trongtaixetxhannhat = taiXe.getTrongtaixe();
                DatXe();
                guiThongBaoChoTaiXe(tokentxgannhat);
            }
        };

        mHandlerThread3 = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(MapsActivity.this, msg.arg2+" ", Toast.LENGTH_SHORT).show();
                if(pDialog.isShowing()){
                    pDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(),
                        "Đơn hàng của bạn đã được chấp nhận !", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(MapsActivity.this,DonHangDangChoKHActivity.class);
                startActivity(intent);
            }
        };

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        return true;
    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Báo giá", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Đơn hàng", true, true, ""); //Menu of Java Tutorials
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Đang chờ", false, false, "https://www.journaldev.com/7153/core-java-tutorial");
        childModelsList.add(childModel);

        childModel = new MenuModel("Đang thực hiện", false, false, "https://www.journaldev.com/19187/java-fileinputstream");
        childModelsList.add(childModel);

        childModel = new MenuModel("Đã hoàn thành", false, false, "https://www.journaldev.com/19115/java-filereader");
        childModelsList.add(childModel);
        childModel = new MenuModel("Đã hủy", false, false, "https://www.journaldev.com/19115/java-filereader");
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }

//        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Cài đặt", true, false, ""); //Menu of Python Tutorials
        headerList.add(menuModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Liên hệ", true, false, ""); //Menu of Python Tutorials
        headerList.add(menuModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Đăng xuất", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
    }

    private void populateExpandableList() {


        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        if(groupPosition == 0){
//                            startFragment(new BlankFragment());
                            Intent intent =new Intent(MapsActivity.this,YeuCauBaoGiaKHActivity.class);
                            startActivity(intent);
                        }
                        if(groupPosition == 2){
//                            startFragment(new BlankFragment());
                            Intent intent =new Intent(MapsActivity.this,HoSoCaNhanActivity.class);
                            startActivity(intent);
                        }
                        if(groupPosition == 3){
//                            startFragment(new BlankFragment());
                            Intent intent =new Intent(MapsActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }
                        if(groupPosition == 4){
                            sessionkh.logoutUser();

//                            startFragment(new BlankFragment());
                            Intent intent =new Intent(MapsActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        onBackPressed();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    final MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if(childPosition == 0){
//                            startFragment(new BlankFragment());
                        Intent intent =new Intent(MapsActivity.this,DonHangDangChoKHActivity.class);
                        startActivity(intent);
                    }
                    if(childPosition == 1){
//                            startFragment(new BlankFragment());
                        Intent intent =new Intent(MapsActivity.this,DonHangDangThucHienKHActivity.class);
                        startActivity(intent);
                    }
                    if(childPosition == 2){
//                            startFragment(new BlankFragment());
                        Intent intent =new Intent(MapsActivity.this,DonHangDangThucHienKHActivity.class);
                        startActivity(intent);
                    }
                    if(childPosition == 3){
//                            startFragment(new BlankFragment());
                        Intent intent =new Intent(MapsActivity.this,DonHangDaHuyKHActivity.class);
                        startActivity(intent);
                    }
                    if (model.url.length() > 0) {

                        onBackPressed();
                    }
                }

                return false;
            }
        });
    }

    private void guiThongBaoChoTaiXe(String tokenTx) {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("token",  tokenTx);
            request.put("tmp", tmp  );
            Log.d(getTAG(MapsActivity.class),request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, urlSendPush, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
    }





    public void expandableButton(View view) {
        expandableLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
        expandableLayout.toggle(); // toggle expand and collapse
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private void CapNhatViTriHienTai(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(MapsActivity.this, "Đã cập nhật vị trí thành công", Toast.LENGTH_SHORT).show();


                        }
                        else {
                            Toast.makeText(MapsActivity.this,"Lỗi",Toast.LENGTH_SHORT);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsActivity.this,"Xảy ra lỗi",Toast.LENGTH_SHORT);

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("latdd", String.valueOf(vido));
                params.put("longdd", String.valueOf(kinhdo));
                return params;

            }
        };
        requestQueue.add(stringRequest);
    }


    private void GetDiaDiem(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String result =  response.toString();
                        final List<DiaDiem> diaDiems = new ArrayList<>();
                        final List<DiaDiem> diaDiems2 = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                diaDiems.add(new DiaDiem( explrObject.getInt("id"), explrObject.getString("name"), explrObject.getDouble("latdd"), explrObject.getDouble("longdd")));
                                diaDiems2.add(diaDiems.get(i));
                            }
//                            diaDiems_global.addAll(diaDiems2);
                            AutoCompleteDiaDiemAdapter myAdapter = new AutoCompleteDiaDiemAdapter(getApplicationContext(),diaDiems2);
                            edtDiemDi.setAdapter(myAdapter);
                            edtDiemDen.setAdapter(myAdapter);
                            edtDiemDi.setThreshold(1);
                            edtDiemDen.setThreshold(1);

                            latDiemDi=-1;
                            latDiemDen=-1;
                            lngDiemDi=-1;
                            lngDiemDen=-1;
                            edtDiemDi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    latDiemDi = diaDiems2.get(position).getLat();
                                    lngDiemDi = diaDiems2.get(position).getLng();
                                    diemDi = diaDiems2.get(position).getName();
                                    if(latDiemDen!=-1 && lngDiemDen!=-1){
                                        Location loc1 = new Location("");
                                        loc1.setLatitude(latDiemDi);
                                        loc1.setLongitude(lngDiemDi);
                                        Location loc2 = new Location("");
                                        loc2.setLatitude(latDiemDen);
                                        loc2.setLongitude(lngDiemDen);
                                        float distanceInMeters = loc1.distanceTo(loc2);
                                        quangduong = distanceInMeters/1000;
                                        int giacuoctmp = 0;
                                        switch (loaixetxhannhat){
                                            case "Xe Tải":
                                                {
                                                    switch (trongtaixetxhannhat){
                                                        case "30 tấn":{
                                                            giacuoctmp = 20000;
                                                            break;
                                                        }

                                                        case "20 tấn":{
                                                            giacuoctmp = 20000;
                                                            break;
                                                        }

                                                        case "15 tấn":{
                                                            giacuoctmp = 20000;
                                                            break;
                                                        }

                                                        case "8 tấn":{
                                                            giacuoctmp = 17000;
                                                            break;
                                                        }

                                                        case "6 tấn":{
                                                            giacuoctmp = 17000;
                                                            break;
                                                        }

                                                        case "1,5 tấn":{
                                                            giacuoctmp = 17000;
                                                            break;
                                                        }

                                                        case "500kg":{
                                                            giacuoctmp = 15000;
                                                            break;
                                                        }

                                                        case "<500kg":{
                                                            giacuoctmp = 15000;
                                                            break;
                                                        }

                                                    }
                                                }
                                                break;
                                            case "Xe Đông Lạnh":
                                                {
                                                    switch (trongtaixetxhannhat){
                                                        case "6 tấn":{
                                                            giacuoctmp = 25000;
                                                            break;
                                                        }

                                                        case "1,5 tấn":{
                                                            giacuoctmp = 25000;
                                                            break;
                                                        }

                                                        case "500kg":{
                                                            giacuoctmp = 20000;
                                                            break;
                                                        }

                                                        case "<500kg":{
                                                            giacuoctmp = 20000;
                                                            break;
                                                        }

                                                    }
                                                }
                                                break;
                                            case "Xe Bán Tải":
                                                {
                                                    switch (trongtaixetxhannhat){
                                                        case "6 tấn":{
                                                            giacuoctmp = 15000;
                                                            break;
                                                        }

                                                        case "1,5 tấn":{
                                                            giacuoctmp = 15000;
                                                            break;
                                                        }

                                                        case "500kg":{
                                                            giacuoctmp = 15000;
                                                            break;
                                                        }

                                                        case "<500kg":{
                                                            giacuoctmp = 15000;
                                                            break;
                                                        }

                                                    }
                                                }
                                                break;
                                            case "Xe Ba Gác":
                                                {
                                                    switch (trongtaixetxhannhat){
                                                        case "500kg":{
                                                            giacuoctmp = 10000;
                                                            break;
                                                        }

                                                        case "<500kg":{
                                                            giacuoctmp = 10000;
                                                            break;
                                                        }

                                                    }
                                                }
                                                break;
                                            case "Xe Container":
                                                {
                                                    switch (trongtaixetxhannhat){
                                                        case "30 tấn":{
                                                            giacuoctmp = 30000;
                                                            break;
                                                        }

                                                        case "20 tấn":{
                                                            giacuoctmp = 30000;
                                                            break;
                                                        }

                                                        case "15 tấn":{
                                                            giacuoctmp = 30000;
                                                            break;
                                                        }

                                                        case "8 tấn":{
                                                            giacuoctmp = 30000;
                                                            break;
                                                        }
                                                    }
                                                }
                                                break;
                                        }
                                        Log.d("aaaaaaaaaaaaaaaaaaaaaaa",giacuoctmp+"");
                                        etGiaCuoc.setText(quangduong*giacuoctmp+"");
                                        Log.d(getTAG(MapsActivity.class),(distanceInMeters/1000)+"");
                                    }
                                }
                            });
                            edtDiemDen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    latDiemDen = diaDiems2.get(position).getLat();
                                    lngDiemDen = diaDiems2.get(position).getLng();
                                    diemDen = diaDiems2.get(position).getName();
                                    if(latDiemDi!=-1 && lngDiemDi!=-1){
                                        Location loc1 = new Location("");
                                        loc1.setLatitude(latDiemDi);
                                        loc1.setLongitude(lngDiemDi);
                                        Location loc2 = new Location("");
                                        loc2.setLatitude(latDiemDen);
                                        loc2.setLongitude(lngDiemDen);
                                        float distanceInMeters = loc1.distanceTo(loc2);
                                        quangduong = distanceInMeters/1000;
                                        int giacuoctmp = 0;
                                        switch (loaixetxhannhat){
                                            case "Xe Tải":
                                            {
                                                switch (trongtaixetxhannhat){
                                                    case "30 tấn":{
                                                        giacuoctmp = 20000;
                                                        break;
                                                    }

                                                    case "20 tấn":{
                                                        giacuoctmp = 20000;
                                                        break;
                                                    }

                                                    case "15 tấn":{
                                                        giacuoctmp = 20000;
                                                        break;
                                                    }

                                                    case "8 tấn":{
                                                        giacuoctmp = 17000;
                                                        break;
                                                    }

                                                    case "6 tấn":{
                                                        giacuoctmp = 17000;
                                                        break;
                                                    }

                                                    case "1,5 tấn":{
                                                        giacuoctmp = 17000;
                                                        break;
                                                    }

                                                    case "500kg":{
                                                        giacuoctmp = 15000;
                                                        break;
                                                    }

                                                    case "<500kg":{
                                                        giacuoctmp = 15000;
                                                        break;
                                                    }

                                                }
                                            }
                                            break;
                                            case "Xe Đông Lạnh":
                                            {
                                                switch (trongtaixetxhannhat){
                                                    case "6 tấn":{
                                                        giacuoctmp = 25000;
                                                        break;
                                                    }

                                                    case "1,5 tấn":{
                                                        giacuoctmp = 25000;
                                                        break;
                                                    }

                                                    case "500kg":{
                                                        giacuoctmp = 20000;
                                                        break;
                                                    }

                                                    case "<500kg":{
                                                        giacuoctmp = 20000;
                                                        break;
                                                    }

                                                }
                                            }
                                            break;
                                            case "Xe Bán Tải":
                                            {
                                                switch (trongtaixetxhannhat){
                                                    case "6 tấn":{
                                                        giacuoctmp = 15000;
                                                        break;
                                                    }

                                                    case "1,5 tấn":{
                                                        giacuoctmp = 15000;
                                                        break;
                                                    }

                                                    case "500kg":{
                                                        giacuoctmp = 15000;
                                                        break;
                                                    }

                                                    case "<500kg":{
                                                        giacuoctmp = 15000;
                                                        break;
                                                    }

                                                }
                                            }
                                            break;
                                            case "Xe Ba Gác":
                                            {
                                                switch (trongtaixetxhannhat){
                                                    case "500kg":{
                                                        giacuoctmp = 10000;
                                                        break;
                                                    }

                                                    case "<500kg":{
                                                        giacuoctmp = 10000;
                                                        break;
                                                    }

                                                }
                                            }
                                            break;
                                            case "Xe Container":
                                            {
                                                switch (trongtaixetxhannhat){
                                                    case "30 tấn":{
                                                        giacuoctmp = 30000;
                                                        break;
                                                    }

                                                    case "20 tấn":{
                                                        giacuoctmp = 30000;
                                                        break;
                                                    }

                                                    case "15 tấn":{
                                                        giacuoctmp = 30000;
                                                        break;
                                                    }

                                                    case "8 tấn":{
                                                        giacuoctmp = 30000;
                                                        break;
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                        Log.d("aaaaaaaaaaaaaaaaaaaaaaa",giacuoctmp+"");
                                        etGiaCuoc.setText(quangduong*giacuoctmp+"");
                                        Log.d(getTAG(MapsActivity.class),(distanceInMeters/1000)+"");
                                    }
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
                        Toast.makeText(MapsActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


    private TaiXe TimTaiXeGanNhat(List<TaiXe> taiXeList){
        TaiXe taiXe_ = new TaiXe();
        for(TaiXe taiXe: taiXeList){
            Location loc1 = new Location("");
            loc1.setLatitude(vido);
            loc1.setLongitude(kinhdo);

            Location loc2 = new Location("");
            loc2.setLatitude(taiXe.getLattx());
            loc2.setLongitude(taiXe.getLngtx());

            float distanceInMeters = loc1.distanceTo(loc2);
            taiXe.setKhoangcach(distanceInMeters);
        }

        double khoangCachNhoNhat = 0;
        khoangCachNhoNhat = taiXeList.get(0).getKhoangcach();
        for(int i = 1; i < taiXeList.size(); i++){
            if(khoangCachNhoNhat > taiXeList.get(i).getKhoangcach()){
                taiXe_ = taiXeList.get(i);
            }
        }
        Log.d("Tai_Xe_Gan_Nhat",taiXe_.toString());
        return taiXe_;
    }

    private void GetTaiXeGanNhat(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String result =  response.toString();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                vtri.add(new TaiXe(explrObject.getString("id_xe"),explrObject.getString("loaixe"),explrObject.getString("trongtaixe"),
                                        explrObject.getString("biensoxe"),explrObject.getString("tenchuxe"), explrObject.getDouble("lattx"),
                                        explrObject.getDouble("longtx"),explrObject.getString("sdttx"),explrObject.getString("mota"),explrObject.getString("token"),explrObject.getString("taikhoan")));
                            }
                            taiXe = TimTaiXeGanNhat(vtri);
                            tokentxgannhat = taiXe.getToken();
                            idtxgannhat = taiXe.getId_xe();
                            loaixetxhannhat = taiXe.getLoaixe();
                            trongtaixetxhannhat = taiXe.getTrongtaixe();
                            Log.d("aaaaaaaTaiXe",loaixetxhannhat + "" + trongtaixetxhannhat);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
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
                        Toast.makeText(MapsActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MapsActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void ChonNgay()
    {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                etThoiGian.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }



    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                            kinhdo = currentLocation.getLongitude();
                            vido = currentLocation.getLatitude();
                            CapNhatViTriHienTai(urlPostViTriHienTai);


                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(MapsActivity.this);
        pDialog.setMessage("Đang tìm tài xế... vui lòng đợi...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void DatXe() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("id_xe",id_xe);
            request.put("id_khachhang",id_kh);
            request.put("tenhang", tenmathang);
            request.put("loaihang", loaihang);
            request.put("trongluonghang", khoiluonghang);
            request.put("diemdi", diemdi);
            request.put("diemden", diemden);
            request.put("giacuoc", giacuoc);
            request.put("thoigiankhoihanh", thoigiankhoihanh);

            Log.d(getTAG(MapsActivity.class),request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, urlPostDataChuyenXe, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getInt("status") == 0) {
                            session.postChuyenXe(id_xe,id_kh,tenmathang,loaihang,khoiluonghang,diemdi,diemden,giacuoc,thoigiankhoihanh);

                        }else{
                            Toast.makeText(getApplicationContext(),
                                    response.getString("message"), Toast.LENGTH_SHORT).show();

                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(MapsActivity.this, "Bạn đã đặt xe thành công !!!", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private boolean validateInputs() {
        if ("".equals(tenmathang)) {
            etTenMatHang.setError("Vui lòng điền Tên mặt hàng");
            etTenMatHang.requestFocus();
            return false;

        }
        if ("".equals(loaihang)) {
            etLoaiHang.setError("Vui lòng điền Loại hàng");
            etLoaiHang.requestFocus();
            return false;

        }
        if ("".equals(khoiluonghang)) {
            etKhoiLuongHang.setError("Vui lòng điền Khối lượng hàng");
            etKhoiLuongHang.requestFocus();
            return false;

        }
        if ("".equals(diemdi)) {
            edtDiemDi.setError("Vui lòng chọn điểm đi");
            edtDiemDi.requestFocus();
            return false;
        }
        if ("".equals(diemden)) {
            edtDiemDen.setError("Vui lòng chọn điểm đến");
            edtDiemDen.requestFocus();
            return false;
        }

        if ("".equals(thoigiankhoihanh)) {
            etThoiGian.setError("Vui lòng chọn thời gian khời hành");
            etThoiGian.requestFocus();
            return false;
        }

        return true;
    }


}
