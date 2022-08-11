package com.mor.morscanner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mor.morscanner.Adapter.SelectPartyAdapter;
import com.mor.morscanner.Model.GetPartyListResponse.AddPartyJson;
import com.mor.morscanner.Model.GetPartyListResponse.GetAddPartyResponse;
import com.mor.morscanner.Model.GetPartyListResponse.GetPartyListResponse;
import com.mor.morscanner.R;
import com.mor.morscanner.Utils.SessionManager;
import com.mor.morscanner.Utils.Utils;
import com.mor.morscanner.Webservice.ApiHandler;
import com.mor.morscanner.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    MainActivity objMain;

    SessionManager sessionManager;

    ArrayList<GetPartyListResponse> listParty;
    Dialog addParty;

    Integer partyId = null;

    String strOrderDate, strDeliveryDate;


    Dialog dialogPartyList;
    Calendar newCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        objMain = this;

        sessionManager = new SessionManager(objMain);

        setAction();

        binding.edtDateOrder.setText(new SimpleDateFormat("dd-MM-yyyy").format(newCalendar.getTime()));
        binding.edtDateDelivery.setText(new SimpleDateFormat("dd-MM-yyyy").format(newCalendar.getTime()));
        strOrderDate = new SimpleDateFormat("yyyy-MM-dd").format(newCalendar.getTime());
        strDeliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(newCalendar.getTime());


    }


    private void getPartyList() {


        if (Utils.getInternetConnection(objMain)) {

            Utils.showProgressDialoug(objMain);


            ApiHandler.getApiService().getPartyListResponse()
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<ArrayList<GetPartyListResponse>>() {
                        @Override
                        public void onNext(ArrayList<GetPartyListResponse> getPartyListResponse) {
                            Utils.dismissDialoug();

                            if (getPartyListResponse == null && getPartyListResponse.isEmpty()) {

                                Toast.makeText(objMain, "No Party Name", Toast.LENGTH_SHORT).show();


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
                            Toast.makeText(objMain, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Utils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(objMain, getResources().getString(R.string.internet_alert), Toast.LENGTH_SHORT).show();
        }

    }

    private void setAction() {

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        binding.edtDateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate("orderDate");
            }
        });

        binding.edtDateDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDate("deliveryDate");

            }
        });


        binding.edtPartyNameList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPartyList();

            }
        });


        binding.btnAddParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();

            }
        });



        binding.btnAddScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!TextUtils.isEmpty(binding.edtPartyNameList.getText())) {

                    Bundle b = new Bundle();
                    b.putString("orderDate", strOrderDate);
                    b.putString("deliveryDate", strDeliveryDate);
                    b.putString("partyName", binding.edtPartyNameList.getText().toString());
                    b.putInt("orderId", 0);
                    b.putInt("partyId", partyId);



                    Intent i_Home = new Intent(objMain, ScannerActivity.class);
                    i_Home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i_Home.putExtras(b);


                    startActivity(i_Home);
                } else {

                    Toast.makeText(objMain, "Please Select Party Name", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    private void setDate(String strDate) {

        DatePickerDialog date = new DatePickerDialog(objMain, (datePicker, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();

            newDate.set(year, monthOfYear, dayOfMonth);
            if (strDate.equals("orderDate")) {

                binding.edtDateOrder.setText(new SimpleDateFormat("dd-MM-yyyy").format(newDate.getTime()));
                strOrderDate = new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime());
            } else {

                binding.edtDateDelivery.setText(new SimpleDateFormat("dd-MM-yyyy").format(newDate.getTime()));
                strDeliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

//        date.getDatePicker().setMaxDate(System.currentTimeMillis());
        date.show();

    }

    private void openPartyListDialog() {
        

            dialogPartyList = new Dialog(objMain);
            dialogPartyList.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogPartyList.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogPartyList.getWindow().setGravity(Gravity.CENTER);

            dialogPartyList.setCanceledOnTouchOutside(false);


            //setting custom layout to dialog_car_variant
            dialogPartyList.setContentView(R.layout.dialog_select_party);


            ListView lv_Country = dialogPartyList.findViewById(R.id.lv_Country);
            EditText edt_SearchCountry = dialogPartyList.findViewById(R.id.edt_Search);

            final SelectPartyAdapter adapterSelectCountry = new SelectPartyAdapter(objMain, listParty);
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

    private void openDialog() {

        addParty = new Dialog(objMain);
        addParty.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addParty.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addParty.getWindow().setGravity(Gravity.CENTER);
        addParty.setCanceledOnTouchOutside(false);

        //setting custom layout to dialog_car_variant
        addParty.setContentView(R.layout.dialog_add_party);


        TextInputLayout edtPartyName = addParty.findViewById(R.id.edtPartyNameNew);
        Button btnAdd = addParty.findViewById(R.id.btnAdd);
        ImageView ivClose = addParty.findViewById(R.id.ivClose);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addParty.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(edtPartyName.getEditText().getText().toString())) {

                    callAddParty(edtPartyName.getEditText().getText().toString());

                } else {
                    Toast.makeText(objMain, "Please Enter Party Name", Toast.LENGTH_SHORT).show();
                }
            }
        });


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;


        addParty.getWindow().setLayout(width, height);

        addParty.show();

    }

    private void callAddParty(String strPartyName) {


        if (Utils.getInternetConnection(objMain)) {

            Utils.showProgressDialoug(objMain);


            ApiHandler.getApiService().getAddPartyResponse(new AddPartyJson(strPartyName.trim(), sessionManager.getKeyUserid()))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetAddPartyResponse>() {
                        @Override
                        public void onNext(GetAddPartyResponse getAddPartyResponse) {
                            Utils.dismissDialoug();

                            if (getAddPartyResponse.getStatusCode() != 200) {

                                Toast.makeText(objMain, getAddPartyResponse.getMessage(), Toast.LENGTH_SHORT).show();


                            } else {

                                Toast.makeText(objMain, getAddPartyResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                addParty.dismiss();
                            }


                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Utils.dismissDialoug();
                            Toast.makeText(objMain, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Utils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(objMain, getResources().getString(R.string.internet_alert), Toast.LENGTH_SHORT).show();
        }


    }


}