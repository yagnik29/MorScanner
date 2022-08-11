package com.mor.morscanner.Webservice;


import com.mor.morscanner.Model.GetAddOrderResponse.GetAddOrderResponse;
import com.mor.morscanner.Model.GetAddOrderResponse.SetOrderJson;
import com.mor.morscanner.Model.GetCheckBarcodeResponse.BarcodeDetails;
import com.mor.morscanner.Model.GetCheckBarcodeResponse.GetCheckBarcodeResponse;
import com.mor.morscanner.Model.GetCheckBarcodeResponse.SetCheckBarcodeJson;
import com.mor.morscanner.Model.GetLoginResponse.GetLoginResponse;
import com.mor.morscanner.Model.GetLoginResponse.SetLoginJson;
import com.mor.morscanner.Model.GetOrderDetailsResponse.SetOrderDetailsJson;
import com.mor.morscanner.Model.GetOrderItemDeleteResponse.GetOrderItemDeleteResponse;
import com.mor.morscanner.Model.GetOrderItemDeleteResponse.SetOrderItemDeleteJson;
import com.mor.morscanner.Model.GetOrderSummeryResponse.GetOrderSummeryResponse;
import com.mor.morscanner.Model.GetOrderSummeryResponse.SetOrderSummeryJson;
import com.mor.morscanner.Model.GetPartyListResponse.AddPartyJson;
import com.mor.morscanner.Model.GetPartyListResponse.GetAddPartyResponse;
import com.mor.morscanner.Model.GetPartyListResponse.GetPartyListResponse;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface WebServices {


    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
    /*@FormUrlEncoded
    @POST("customer/login")
    io.reactivex.Observable<GetCustomerLoginResponse> getCustomerLoginResponse(@Field("email") String email, @Field("password") String password);*/


    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
    /*@FormUrlEncoded
    @POST("driver/login")
    io.reactivex.Observable<GetDriverLoginResponse> getDriverLoginResponse(@Field("email") String email, @Field("password") String password);
*/





    /*
     * Get Cancel Running Job Response
     *
     * */

    /*@POST("job/Canceljob")
    io.reactivex.Observable<GetCancleRunningJobResponse>
    getCancelRunningJobResponse(@Header("Content-Type") String authorization,
                                @Header("Apikey") String apikey,
                                @Body SetCancelJob setCancelJob);*/

    @POST("UserLogin")
    io.reactivex.Observable<GetLoginResponse>
    getLoginResponse(@Body SetLoginJson setLoginJson);


    @POST("AddParty")
    io.reactivex.Observable<GetAddPartyResponse>
    getAddPartyResponse(@Body AddPartyJson addPartyJson);


    @GET("GetPartyList")
    io.reactivex.Observable<ArrayList<GetPartyListResponse>>
    getPartyListResponse();


    @POST("CheckBarcode")
    io.reactivex.Observable<GetCheckBarcodeResponse>
    getCheckBarcodeResponse(@Body SetCheckBarcodeJson setCheckBarcodeJson);


    @POST("AddOrder")
    io.reactivex.Observable<GetAddOrderResponse>
    getAddOrderResponse(@Body SetOrderJson setOrderResponse);

    @POST("Order/ItemDelete")
    io.reactivex.Observable<GetOrderItemDeleteResponse>
    getOrderItemDeleteResponse(@Body SetOrderItemDeleteJson setOrderItemDeleteJson);


    @POST("Order/Summary")
    io.reactivex.Observable<ArrayList<GetOrderSummeryResponse>>
    getOrderSummeryResponse(@Body SetOrderSummeryJson setOrderSummeryJson);

    @POST("Order/Detail")
    io.reactivex.Observable<ArrayList<BarcodeDetails>>
    getOrderDetailsResponse(@Body SetOrderDetailsJson setOrderDetailsJson);


}
