package com.example.user.truckonlinetaixe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.user.truckonlinetaixe.Adapter.YeuCauBaoGiaKHAdapter;
import com.example.user.truckonlinetaixe.Model.MenuModel;
import com.example.user.truckonlinetaixe.Model.TaiXe;
import com.example.user.truckonlinetaixe.R;
import com.example.user.truckonlinetaixe.SessionHandler.SessionHandler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

public class HomeTXActivity extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener {
    String TAG = MapsActivity.class.getSimpleName();

    SessionHandler sessiontx;
    private GoogleMap mMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;

    Marker marker;


    YeuCauBaoGiaKHAdapter.ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu_tx);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        sessiontx = new SessionHandler(getApplicationContext());
        TaiXe user = sessiontx.getUserDetails();


        expandableListView = findViewById(R.id.expandableListView1);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // getLocationPermission();


        NavigationView navigationView = findViewById(R.id.nav_view1);
//        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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


        expandableListAdapter = new YeuCauBaoGiaKHAdapter.ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        if(groupPosition == 0){
//                            startFragment(new BlankFragment());
                            Intent intent =new Intent(HomeTXActivity.this, YeuCauBaoGiaActivity.class);
                            startActivity(intent);
                        }
                        if(groupPosition == 2){
//                            startFragment(new BlankFragment());
                            Intent intent =new Intent(HomeTXActivity.this, HoSoCaNhanTXActivity.class);
                            startActivity(intent);
                        }
                        if(groupPosition == 3){
//                            startFragment(new BlankFragment());
                            Intent intent =new Intent(HomeTXActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        }
                        if(groupPosition == 4){
                            sessiontx.logoutUser();

//                            startFragment(new BlankFragment());
                            Intent intent =new Intent(HomeTXActivity.this, MainActivity.class);
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
                        Intent intent =new Intent(HomeTXActivity.this, DonHangDangChoTXActivity.class);
                        startActivity(intent);
                    }
                    if(childPosition == 1){
//                            startFragment(new BlankFragment());
                        Intent intent =new Intent(HomeTXActivity.this, DonHangDangThucHienTXActivity.class);
                        startActivity(intent);
                    }
                    if(childPosition == 2){
//                            startFragment(new BlankFragment());
                        Intent intent =new Intent(HomeTXActivity.this, DonHangDaHoanThanhTXActivity.class);
                        startActivity(intent);
                    }
                    if(childPosition == 3){
//                            startFragment(new BlankFragment());
                        Intent intent =new Intent(HomeTXActivity.this, DonHangDaHuyTXActivity.class);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
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

//                            kinhdo = currentLocation.getLongitude();
//                            vido = currentLocation.getLatitude();
//                            CapNhatViTriHienTai(urlPostViTriHienTai);


                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(HomeTXActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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

        mapFragment.getMapAsync(HomeTXActivity.this);
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

}

//package com.example.user.truckonlinetaixe;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
//import android.support.design.widget.Snackbar;
//import android.util.Log;
//import android.view.View;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.widget.ExpandableListView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class HomeTXActivity extends AppCompatActivity  {
//    SessionHandler sessiontx;
//
//
//    ExpandableListAdapter expandableListAdapter;
//    ExpandableListView expandableListView;
//    List<MenuModel> headerList = new ArrayList<>();
//    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_trangchu_tx);
//        Toolbar toolbar = findViewById(R.id.toolbar2);
//        setSupportActionBar(toolbar);
//        sessiontx = new SessionHandler(getApplicationContext());
//        TaiXe user = sessiontx.getUserDetails();
//
//
//        expandableListView = findViewById(R.id.expandableListView1);
//        prepareMenuData();
//        populateExpandableList();
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = findViewById(R.id.nav_view1);
////        navigationView.setNavigationItemSelectedListener(this);
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//
//
//    private void prepareMenuData() {
//
//        MenuModel menuModel = new MenuModel("Báo giá", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
//        headerList.add(menuModel);
//
//        if (!menuModel.hasChildren) {
//            childList.put(menuModel, null);
//        }
//
//        menuModel = new MenuModel("Đơn hàng", true, true, ""); //Menu of Java Tutorials
//        headerList.add(menuModel);
//        List<MenuModel> childModelsList = new ArrayList<>();
//        MenuModel childModel = new MenuModel("Đang chờ", false, false, "https://www.journaldev.com/7153/core-java-tutorial");
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Đang thực hiện", false, false, "https://www.journaldev.com/19187/java-fileinputstream");
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Đã hoàn thành", false, false, "https://www.journaldev.com/19115/java-filereader");
//        childModelsList.add(childModel);
//        childModel = new MenuModel("Đã hủy", false, false, "https://www.journaldev.com/19115/java-filereader");
//        childModelsList.add(childModel);
//
//
//
//        if (menuModel.hasChildren) {
//            Log.d("API123","here");
//            childList.put(menuModel, childModelsList);
//        }
//
////        childModelsList = new ArrayList<>();
//        menuModel = new MenuModel("Cài đặt", true, false, ""); //Menu of Python Tutorials
//        headerList.add(menuModel);
//
//        if (menuModel.hasChildren) {
//            childList.put(menuModel, null);
//        }
//
//        menuModel = new MenuModel("Liên hệ", true, false, ""); //Menu of Python Tutorials
//        headerList.add(menuModel);
//
//        if (menuModel.hasChildren) {
//            childList.put(menuModel, null);
//        }
//
//        menuModel = new MenuModel("Đăng xuất", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
//        headerList.add(menuModel);
//
//        if (!menuModel.hasChildren) {
//            childList.put(menuModel, null);
//        }
//    }
//
//
//    private void populateExpandableList() {
//
//
//        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
//        expandableListView.setAdapter(expandableListAdapter);
//
//        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//
//                if (headerList.get(groupPosition).isGroup) {
//                    if (!headerList.get(groupPosition).hasChildren) {
//                        if(groupPosition == 0){
////                            startFragment(new BlankFragment());
//                            Intent intent =new Intent(HomeTXActivity.this,YeuCauBaoGiaActivity.class);
//                            startActivity(intent);
//                        }
//                        if(groupPosition == 2){
////                            startFragment(new BlankFragment());
//                            Intent intent =new Intent(HomeTXActivity.this,HoSoCaNhanTXActivity.class);
//                            startActivity(intent);
//                        }
//                        if(groupPosition == 3){
////                            startFragment(new BlankFragment());
//                            Intent intent =new Intent(HomeTXActivity.this,LienHeActivity.class);
//                            startActivity(intent);
//                        }
//                        if(groupPosition == 4){
//                            sessiontx.logoutUser();
//
////                            startFragment(new BlankFragment());
//                            Intent intent =new Intent(HomeTXActivity.this,MainActivity.class);
//                            startActivity(intent);
//                        }
//                        onBackPressed();
//                    }
//                }
//
//                return false;
//            }
//        });
//
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//
//                if (childList.get(headerList.get(groupPosition)) != null) {
//                    final MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
//                    if(childPosition == 0){
////                            startFragment(new BlankFragment());
//                        Intent intent =new Intent(HomeTXActivity.this,DonHangDangChoTXActivity.class);
//                        startActivity(intent);
//                    }
//                    if(childPosition == 1){
////                            startFragment(new BlankFragment());
//                        Intent intent =new Intent(HomeTXActivity.this,DonHangDangThucHienTXActivity.class);
//                        startActivity(intent);
//                    }
//                    if(childPosition == 2){
////                            startFragment(new BlankFragment());
//                        Intent intent =new Intent(HomeTXActivity.this,DonHangDaHoanThanhTXActivity.class);
//                        startActivity(intent);
//                    }
//                    if(childPosition == 3){
////                            startFragment(new BlankFragment());
//                        Intent intent =new Intent(HomeTXActivity.this,DonHangDaHuyTXActivity.class);
//                        startActivity(intent);
//                    }
//                    if (model.url.length() > 0) {
//
//                        onBackPressed();
//                    }
//                }
//
//                return false;
//            }
//        });
//    }
//}
