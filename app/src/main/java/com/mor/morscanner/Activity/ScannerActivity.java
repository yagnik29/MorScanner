package com.mor.morscanner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.FocusingProcessor;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;
import com.mor.morscanner.Adapter.AdapterBarcodeList;
import com.mor.morscanner.Model.GetAddOrderResponse.GetAddOrderResponse;
import com.mor.morscanner.Model.GetAddOrderResponse.SetOrderJson;
import com.mor.morscanner.Model.GetCheckBarcodeResponse.BarcodeDetails;
import com.mor.morscanner.Model.GetCheckBarcodeResponse.GetCheckBarcodeResponse;
import com.mor.morscanner.Model.GetCheckBarcodeResponse.SetCheckBarcodeJson;
import com.mor.morscanner.Model.GetOrderDetailsResponse.SetOrderDetailsJson;
import com.mor.morscanner.Model.GetOrderItemDeleteResponse.GetOrderItemDeleteResponse;
import com.mor.morscanner.Model.GetOrderItemDeleteResponse.SetOrderItemDeleteJson;
import com.mor.morscanner.R;
import com.mor.morscanner.Utils.SessionManager;
import com.mor.morscanner.Utils.Utils;
import com.mor.morscanner.Webservice.ApiHandler;
import com.mor.morscanner.databinding.ActivityScannerBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ScannerActivity extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;


    int MY_PER;

    RecyclerView rvBarcode;

    ArrayList<String> data;

    SessionManager sessionManager;

    ScannerActivity objScanner;
    private String strBarcodeData;

    ArrayList<BarcodeDetails> listBarcode = new ArrayList<>();
    ArrayList<String> listBarcodeString = new ArrayList<>();

    String strOrderDate, strDeliveryDate, strPartyName, strNotes;
    Integer partyId, orderId;
    AdapterBarcodeList adapterBarcodeList;

    ActivityScannerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        objScanner = this;
        sessionManager = new SessionManager(objScanner);


        setAction();
        getBundleData();

    }

    private void getBundleData() {

        Bundle b = getIntent().getExtras();

        if (b != null) {

            partyId = b.getInt("partyId");
            orderId = b.getInt("orderId");
            strOrderDate = b.getString("orderDate");
            strDeliveryDate = b.getString("deliveryDate");
            strPartyName = b.getString("strPartyName");
            strNotes = b.getString("notes");
            binding.edtNotes.getEditText().setText(strNotes);

            if (orderId == 0) {
                Timber.e("Log here for update");
                orderId = 0;

            } else {
                apiCallOrderDetails();
            }

        }

    }

    private void apiCallOrderDetails() {

        if (Utils.getInternetConnection(objScanner)) {

            Utils.showProgressDialoug(objScanner);


            ApiHandler.getApiService().getOrderDetailsResponse(new SetOrderDetailsJson(orderId, sessionManager.getKeyUserid()))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<ArrayList<BarcodeDetails>>() {
                        @Override
                        public void onNext(ArrayList<BarcodeDetails> barcodeDetails) {
                            Utils.dismissDialoug();

                            if (!barcodeDetails.isEmpty()) {

//                                addToList(getCheckBarcodeResponse.getBarcodeDetails().getBarcode());
                                listBarcode = barcodeDetails;

                                for (BarcodeDetails model : listBarcode) {
                                    listBarcodeString.add(model.getBarcode());
                                }

                                adapterBarcodeList = new AdapterBarcodeList(objScanner, listBarcode, new AdapterBarcodeList.ClickCallback() {
                                    @Override
                                    public void onItemClick(int position) {

                                        if (listBarcode.get(position).getOrderId() == null) {

                                            listBarcode.remove(position);
                                            listBarcodeString.remove(position);
                                            adapterBarcodeList.notifyDataSetChanged();

                                        } else {

                                            apiCallDeleteOrder(position, listBarcode.get(position).getOrderId(), listBarcode.get(position).getBarcode());

                                        }

                                    }
                                });

                                binding.rvBarcode.setAdapter(adapterBarcodeList);


                            } else {

                                Toast.makeText(objScanner, "No Order Data", Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Utils.dismissDialoug();
                            Toast.makeText(objScanner, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Utils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(objScanner, getResources().getString(R.string.internet_alert), Toast.LENGTH_SHORT).show();
        }


    }



    private void apiCallBarcodeDetail(String strBarcodeData) {

        if (Utils.getInternetConnection(objScanner)) {

            Utils.showProgressDialoug(objScanner);


            ApiHandler.getApiService().getCheckBarcodeResponse(new SetCheckBarcodeJson(sessionManager.getKeyUserid(), strBarcodeData))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetCheckBarcodeResponse>() {
                        @Override
                        public void onNext(GetCheckBarcodeResponse getCheckBarcodeResponse) {
                            Utils.dismissDialoug();

                            if (getCheckBarcodeResponse.getMessage().equalsIgnoreCase("success")) {

//                                addToList(getCheckBarcodeResponse.getBarcodeDetails().getBarcode());
                                addBarcodeDetails(getCheckBarcodeResponse.getBarcodeDetails());


                            } else {

                                Toast.makeText(objScanner, getCheckBarcodeResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Utils.dismissDialoug();
                            Toast.makeText(objScanner, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Utils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(objScanner, getResources().getString(R.string.internet_alert), Toast.LENGTH_SHORT).show();
        }


    }


    private void addBarcodeDetails(BarcodeDetails barcodeDetails) {

        if (listBarcode != null) {

            listBarcode.add(barcodeDetails);
            listBarcodeString.add(barcodeDetails.getBarcode());
            Timber.e("barcode_added");


            adapterBarcodeList = new AdapterBarcodeList(objScanner, listBarcode, new AdapterBarcodeList.ClickCallback() {
                @Override
                public void onItemClick(int position) {

                    if (listBarcode.get(position).getOrderId() == null) {

                        listBarcode.remove(position);
                        listBarcodeString.remove(position);
                        adapterBarcodeList.notifyDataSetChanged();

                    } else {
                        apiCallDeleteOrder(position, listBarcode.get(position).getOrderId(), listBarcode.get(position).getBarcode());

                    }


                }
            });

            binding.rvBarcode.setAdapter(adapterBarcodeList);

        }

    }

    private void apiCallDeleteOrder(int position, Integer orderId, String barcode) {

        if (Utils.getInternetConnection(objScanner)) {

            Utils.showProgressDialoug(objScanner);


            ApiHandler.getApiService().getOrderItemDeleteResponse(new SetOrderItemDeleteJson(orderId, barcode, sessionManager.getKeyUserid()))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetOrderItemDeleteResponse>() {
                        @Override
                        public void onNext(GetOrderItemDeleteResponse getOrderItemDeleteResponse) {
                            Utils.dismissDialoug();

                            if (getOrderItemDeleteResponse.getMessage().equalsIgnoreCase("success")) {

//                                addToList(getCheckBarcodeResponse.getBarcodeDetails().getBarcode());
                                listBarcode.remove(position);
                                listBarcodeString.remove(position);

                                adapterBarcodeList.notifyDataSetChanged();


                            } else {

                                Toast.makeText(objScanner, getOrderItemDeleteResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Utils.dismissDialoug();
                            Toast.makeText(objScanner, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Utils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(objScanner, getResources().getString(R.string.internet_alert), Toast.LENGTH_SHORT).show();
        }


    }

    @SuppressLint("MissingPermission")
    private void addBarcodeToList(String barcode) {

        if (!listBarcodeString.contains(barcode)) {

            apiCallBarcodeDetail(barcode);


        } else {

            Toast.makeText(objScanner, "Barcode scanned already.", Toast.LENGTH_SHORT).show();

        }


    }




    private void setAction() {


        binding.btnScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(objScanner, ScanCamera.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);

            }
        });



        binding.ivLogoWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        binding.btnAddBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(binding.edtBarcode.getEditText().getText().toString())) {
                    Toast.makeText(objScanner, "Please Enter Barcode", Toast.LENGTH_SHORT).show();

                } else {

                    addBarcodeToList(binding.edtBarcode.getEditText().getText().toString());

                }

            }
        });

       /* binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strText = binding.barcodeText.getText().toString();

                if (!strText.isEmpty()) {

                    if (listBarcode != null) {
                        if (!listBarcode.contains(binding.barcodeText.getText().toString())) {

                            listBarcode.add(binding.barcodeText.getText().toString());
                            binding.barcodeText.setText("");

                        } else {

                            Toast.makeText(objScanner, "Parcel scanned already.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });*/

        binding.btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if (listBarcode != null && !listBarcode.isEmpty()) {

                    for (BarcodeDetails model : listBarcode) {
                        Timber.e(model.getBarcode());

                    }

                    Timber.e("array %s", Arrays.toString(listBarcode.toArray()));

                } else {
                    Timber.e("Print null");
                }*/

                if (listBarcode != null && !listBarcode.isEmpty()) {

                    apiCallAddOrder();
                }


                /*Bundle b = new Bundle();

                b.putString("orderDate", strOrderDate);
                b.putString("deliveryDate", strDeliveryDate);
                b.putString("partyName", strPartyName);
                b.putInt("partyId", partyId);
                b.putStringArrayList("barcodeList", listBarcode);


                Intent i_Home = new Intent(objScanner, OrderDetailScreen.class);
                i_Home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i_Home.putExtras(b);


                startActivityForResult(i_Home, 1);*/

            }
        });

    }

    private void apiCallAddOrder() {

        if (Utils.getInternetConnection(objScanner)) {

            Utils.showProgressDialoug(objScanner);

            assert binding.edtNotes != null;
            Timber.e("gsonOrder %s", new Gson().toJson(new SetOrderJson(orderId, partyId, strOrderDate,
                    strDeliveryDate, binding.edtNotes.getEditText().getText().toString(), sessionManager.getKeyUserid(), listBarcode)));

            ApiHandler.getApiService().getAddOrderResponse(new SetOrderJson(orderId, partyId, strOrderDate,
                    strDeliveryDate, binding.edtNotes.getEditText().getText().toString(), sessionManager.getKeyUserid(), listBarcode))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetAddOrderResponse>() {
                        @Override
                        public void onNext(GetAddOrderResponse getAddOrderResponse) {
                            Utils.dismissDialoug();

                            if (getAddOrderResponse.getStatusCode() == 200) {

                                Toast.makeText(objScanner, getAddOrderResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();


                            } else {


                                Toast.makeText(objScanner, getAddOrderResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Utils.dismissDialoug();
                            Toast.makeText(objScanner, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Utils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(objScanner, getResources().getString(R.string.internet_alert), Toast.LENGTH_SHORT).show();
        }

    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                String returnString = data.getStringExtra("barcode");

                Timber.e("Barcode %s", returnString);

                addBarcodeToList(returnString);


            }
        }
    }


}