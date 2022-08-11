package com.mor.morscanner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mor.morscanner.Adapter.AdapterOrderSummery;
import com.mor.morscanner.Adapter.SelectPartyAdapter;
import com.mor.morscanner.Model.GetOrderSummeryResponse.GetOrderSummeryResponse;
import com.mor.morscanner.Model.GetOrderSummeryResponse.SetOrderSummeryJson;
import com.mor.morscanner.Model.GetPartyListResponse.GetPartyListResponse;
import com.mor.morscanner.R;
import com.mor.morscanner.Utils.Utils;
import com.mor.morscanner.Webservice.ApiHandler;
import com.mor.morscanner.databinding.ActivityOrderSummeryBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class OrderSummery extends AppCompatActivity {

    OrderSummery objOrderSummery;
    ActivityOrderSummeryBinding binding;

    Dialog dialogPartyList;
    Calendar newCalendar = Calendar.getInstance();

    ArrayList<GetPartyListResponse> listParty;
    ArrayList<GetOrderSummeryResponse> listOrderSummery;


    Integer partyId = null, orderId = null;


    String strOrderDateFrom, strDeliveryDateFrom, strOrderDateTo, strDeliveryDateTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderSummeryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        objOrderSummery = this;

        setAction();



    }

    private void setAction() {



        binding.ivLogoWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        binding.edtPartyNameList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPartyList();

            }
        });

        binding.edtDateOrderFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDate("orderDateFrom");
            }
        });

        binding.edtDateDeliveryFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate("deliveryDateFrom");
            }
        });

        binding.edtDateToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate("orderDateTo");
            }
        });

        binding.edtDateDeliveryTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate("toDeliveryDate");
            }
        });


        binding.btnGetSummery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.edtOrderNumber.getEditText().getText().toString().isEmpty()) {
                    orderId = null;
                } else {
                    orderId = Integer.valueOf(binding.edtOrderNumber.getEditText().getText().toString());
                }
                getOrderSummery(orderId);

            }
        });

    }

    private void getOrderSummery(Integer orderId) {

        if (Utils.getInternetConnection(objOrderSummery)) {

            Utils.showProgressDialoug(objOrderSummery);


            ApiHandler.getApiService().getOrderSummeryResponse(new SetOrderSummeryJson(orderId,
                    partyId,strOrderDateFrom, strOrderDateTo, strDeliveryDateFrom, strDeliveryDateTo))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<ArrayList<GetOrderSummeryResponse>>() {
                        @Override
                        public void onNext(ArrayList<GetOrderSummeryResponse> getOrderSummeryResponses) {
                            Utils.dismissDialoug();

                            if (getOrderSummeryResponses == null && getOrderSummeryResponses.isEmpty()) {

                                Toast.makeText(objOrderSummery, "No Order Found for the search", Toast.LENGTH_SHORT).show();


                            } else {

                                listOrderSummery = getOrderSummeryResponses;

                                binding.rvOrderSummery.setAdapter(new AdapterOrderSummery(objOrderSummery,
                                        getOrderSummeryResponses, new AdapterOrderSummery.ClickCallback() {
                                    @Override
                                    public void onItemClick(int position) {

                                        Bundle b = new Bundle();

                                        b.putString("orderDate", listOrderSummery.get(position).getOrderDate());
                                        b.putString("deliveryDate", listOrderSummery.get(position).getDeliveryDate());
                                        b.putString("partyName", listOrderSummery.get(position).getPartyName());
                                        b.putString("notes", listOrderSummery.get(position).getNotes());
                                        b.putInt("orderId", listOrderSummery.get(position).getOrderID());


                                        Intent i_Home = new Intent(objOrderSummery, ScannerActivity.class);
                                        i_Home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        i_Home.putExtras(b);


                                        startActivity(i_Home);


                                    }
                                }));

                            }


                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Utils.dismissDialoug();
                            Toast.makeText(objOrderSummery, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Utils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(objOrderSummery, getResources().getString(R.string.internet_alert), Toast.LENGTH_SHORT).show();
        }
        
    }

    private void getPartyList() {


        if (Utils.getInternetConnection(objOrderSummery)) {

            Utils.showProgressDialoug(objOrderSummery);


            ApiHandler.getApiService().getPartyListResponse()
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<ArrayList<GetPartyListResponse>>() {
                        @Override
                        public void onNext(ArrayList<GetPartyListResponse> getPartyListResponse) {
                            Utils.dismissDialoug();

                            if (getPartyListResponse == null && getPartyListResponse.isEmpty()) {

                                Toast.makeText(objOrderSummery, "No Party Name", Toast.LENGTH_SHORT).show();


                            } else {
                                listParty = new ArrayList<>();

                                listParty = getPartyListResponse;
                                Timber.e("Partylist %s", listParty.get(0));
                                openPartyListDialog();


                            }


                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Utils.dismissDialoug();
                            Toast.makeText(objOrderSummery, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Utils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(objOrderSummery, getResources().getString(R.string.internet_alert), Toast.LENGTH_SHORT).show();
        }

    }


    private void setDate(String strDate) {

        DatePickerDialog date = new DatePickerDialog(objOrderSummery, (datePicker, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();

            newDate.set(year, monthOfYear, dayOfMonth);
            if (strDate.equals("orderDateTo")) {

                binding.edtDateToOrder.setText(new SimpleDateFormat("dd-MM-yyyy").format(newDate.getTime()));
                strOrderDateTo = new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime());
            } else if (strDate.equals("orderDateFrom")) {

                binding.edtDateOrderFrom.setText(new SimpleDateFormat("dd-MM-yyyy").format(newDate.getTime()));
                strOrderDateFrom = new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime());
            } else if (strDate.equals("deliveryDateFrom")) {

                binding.edtDateDeliveryFrom.setText(new SimpleDateFormat("dd-MM-yyyy").format(newDate.getTime()));
                strDeliveryDateFrom = new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime());
            } else {

                binding.edtDateDeliveryTo.setText(new SimpleDateFormat("dd-MM-yyyy").format(newDate.getTime()));
                strDeliveryDateTo = new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

//        date.getDatePicker().setMaxDate(System.currentTimeMillis());
        date.show();

    }

    private void openPartyListDialog() {


        dialogPartyList = new Dialog(objOrderSummery);
        dialogPartyList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPartyList.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPartyList.getWindow().setGravity(Gravity.CENTER);

        dialogPartyList.setCanceledOnTouchOutside(false);


        //setting custom layout to dialog_car_variant
        dialogPartyList.setContentView(R.layout.dialog_select_party);


        ListView lv_Country = dialogPartyList.findViewById(R.id.lv_Country);
        EditText edt_SearchCountry = dialogPartyList.findViewById(R.id.edt_Search);

        final SelectPartyAdapter adapterSelectCountry = new SelectPartyAdapter(objOrderSummery, listParty);
        lv_Country.setAdapter(adapterSelectCountry);

        edt_SearchCountry.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapterSelectCountry.filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {


            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        lv_Country.setOnItemClickListener((parent, view, position, id) -> {

            String strName = listParty.get(position).getPartyName();
            binding.edtPartyNameList.setText(strName);
            partyId = listParty.get(position).getPartyID();
            dialogPartyList.dismiss();

        });


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels / 2;
        int width = displaymetrics.widthPixels - 100;


        dialogPartyList.getWindow().setLayout(width, height);

        dialogPartyList.show();


    }


    
}