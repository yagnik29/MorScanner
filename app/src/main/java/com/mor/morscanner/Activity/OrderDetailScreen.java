package com.mor.morscanner.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mor.morscanner.Adapter.AdapterBarcodeList;
import com.mor.morscanner.Model.GetAddOrderResponse.GetAddOrderResponse;
import com.mor.morscanner.Model.GetAddOrderResponse.SetOrderJson;
import com.mor.morscanner.Model.GetCheckBarcodeResponse.BarcodeDetails;
import com.mor.morscanner.R;
import com.mor.morscanner.Utils.SessionManager;
import com.mor.morscanner.Utils.Utils;
import com.mor.morscanner.Webservice.ApiHandler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailScreen extends AppCompatActivity {


    RecyclerView rvBarcode;

    ArrayList<BarcodeDetails> data;

    OrderDetailScreen objOrder;
    AdapterBarcodeList adapterBarcodeList;
    Button btnSubmitOrder;

    String strOrderDate, strDeliveryDate, strPartyName;
    Integer partyId;
    TextInputEditText edtNotesText;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_screen);

        objOrder = this;

        sessionManager = new SessionManager(objOrder);

        findView();
        setAction();
        getBundleData();

    }

    private void getBundleData() {

        Bundle b = getIntent().getExtras();

        if (b != null) {

            partyId = b.getInt("partyId");
            strOrderDate = b.getString("orderDate");
            strDeliveryDate = b.getString("deliveryDate");
            strPartyName = b.getString("strPartyName");
//            data = b.getStringArrayList("barcodeList");


        }


        adapterBarcodeList = new AdapterBarcodeList(objOrder, data, new AdapterBarcodeList.ClickCallback() {
            @Override
            public void onItemClick(int position) {

                data.remove(position);
                adapterBarcodeList.notifyDataSetChanged();


            }
        });

        rvBarcode.setAdapter(adapterBarcodeList);


    }

    private void setAction() {


        btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                apiCallAddOrder();

                
            }
        });


    }

    private void apiCallAddOrder() {

        if (Utils.getInternetConnection(objOrder)) {

            Utils.showProgressDialoug(objOrder);


            ApiHandler.getApiService().getAddOrderResponse(new SetOrderJson(0,partyId, strOrderDate,
                    strDeliveryDate, edtNotesText.getText().toString(),sessionManager.getKeyUserid(), data))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetAddOrderResponse>() {
                        @Override
                        public void onNext(GetAddOrderResponse getAddOrderResponse) {
                            Utils.dismissDialoug();

                            if (getAddOrderResponse.getStatusCode() == 200) {

                                Toast.makeText(objOrder, getAddOrderResponse.getMessage(), Toast.LENGTH_SHORT).show();


                            } else {


                                Toast.makeText(objOrder, getAddOrderResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Utils.dismissDialoug();
                            Toast.makeText(objOrder, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Utils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(objOrder, getResources().getString(R.string.internet_alert), Toast.LENGTH_SHORT).show();
        }
        
    }

    private void findView() {

        edtNotesText = findViewById(R.id.edtNotesText);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);
        rvBarcode = findViewById(R.id.rvBarcode);
        rvBarcode.setLayoutManager(new LinearLayoutManager(objOrder, RecyclerView.VERTICAL, false));


    }
}