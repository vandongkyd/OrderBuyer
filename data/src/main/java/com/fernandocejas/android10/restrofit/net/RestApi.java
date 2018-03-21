/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.restrofit.net;

import com.fernandocejas.android10.order.domain.repository.TestOrderReposity;
import com.fernandocejas.android10.restrofit.enity.AddressEntityResponse;
import com.fernandocejas.android10.restrofit.enity.AddressListEntityResponse;
import com.fernandocejas.android10.restrofit.enity.ContactEnity;
import com.fernandocejas.android10.restrofit.enity.EventListEntityResponse;
import com.fernandocejas.android10.restrofit.enity.GoogleGeoListEntityResponse;
import com.fernandocejas.android10.restrofit.enity.MessageEntityResponse;
import com.fernandocejas.android10.restrofit.enity.MetaDataEntityResponse;
import com.fernandocejas.android10.restrofit.enity.OrderEntity;
import com.fernandocejas.android10.restrofit.enity.OrderEntityResponse;
import com.fernandocejas.android10.restrofit.enity.OrderListEntityResponse;
import com.fernandocejas.android10.restrofit.enity.PaymentEntityResponse;
import com.fernandocejas.android10.restrofit.enity.PaymentListEntityResponse;
import com.fernandocejas.android10.restrofit.enity.ProductEntityParseResponse;
import com.fernandocejas.android10.restrofit.enity.SettingByCountryEntityResponse;
import com.fernandocejas.android10.restrofit.enity.TickerEnity;
import com.fernandocejas.android10.restrofit.enity.TickerListEnityResponse;
import com.fernandocejas.android10.restrofit.enity.UserEntity;
import com.fernandocejas.android10.restrofit.enity.UserEntityResponse;

import java.util.Collection;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {

    /**
     * Retrieves an {@link Observable} which will emit a List of {@link OrderEntity}.
     */
    @POST("/apikoda/orders")
    @FormUrlEncoded
    Observable<OrderListEntityResponse> orders(
            @Header("Authorization") String token,
            @Field("offset") String offset
    );

    @POST("/apikoda/order_detail")
    @FormUrlEncoded
    Observable<OrderEntityResponse> order_detail(
            @Header("Authorization") String token,
            @Field("order") String order_id
    );

    /**
     * Retrieves an {@link Observable} which will emit a Object of {@link UserEntity}.
     */
    @POST("/apikoda/auto_signin")
    Observable<UserEntityResponse> autoSignIn(
            @Header("Authorization") String token
    );

    /**
     * Retrieves an {@link Observable} which will emit a Object of {@link UserEntity}.
     */
    @POST("/apikoda/signin")
    @FormUrlEncoded
    Observable<UserEntityResponse> signIn(
            @Field("email") String email,
            @Field("password") String password
    );

    /**
     * Retrieves an {@link Observable} which will emit a Object of {@link UserEntity}.
     */
    @POST("/apikoda/signup")
    @FormUrlEncoded
    Observable<UserEntityResponse> signUp(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("type") String type,
            @Field("phonecode") String phone_code
    );

    @GET("https://api.coinmarketcap.com/v1/ticker/?limit=1")
    //@FormUrlEncoded
    Observable<Collection<TickerEnity>> testorder(
            @Header("Authorization") String access_token

    );
    @GET("https://api.androidhive.info/contacts/")
        //@FormUrlEncoded
    Observable<Collection<ContactEnity>> hellocontact(
            @Header("Authorization") String access_token

    );
    /**
     * Retrieves an {@link Observable} which will emit a Object of {@link UserEntity}.
     */
    @POST("/apikoda/activation")
    @FormUrlEncoded
    Observable<UserEntityResponse> activation(
            @Header("Authorization") String token,
            @Field("code") String code
    );

    @POST("/apikoda/resend_activation_code")
    Observable<UserEntityResponse> resendActivationCode(
            @Header("Authorization") String token
    );

    @GET("/apikoda/events")
    Observable<EventListEntityResponse> events(
            @Header("Authorization") String token
    );

    @POST("/apikoda/parse")
    @FormUrlEncoded
    Observable<ProductEntityParseResponse> parse(
            @Header("Authorization") String token,
            @Field("url") String url
    );

    @GET("/apikoda/setting")
    Observable<SettingByCountryEntityResponse> setting(
            @Header("Authorization") String token,
            @Query("country") String country
    );

    @HTTP(method = "POST", path = "/apikoda/create_order", hasBody = true)
    Observable<OrderEntityResponse> create_order(
            @Header("Authorization") String token,
            @Body OrderEntity orderEntity
    );

    @POST("/apikoda/credit_cards")
    Observable<PaymentListEntityResponse> credit_cards(
            @Header("Authorization") String token
    );

    @POST("/apikoda/add_credit_card")
    @FormUrlEncoded
    Observable<PaymentEntityResponse> add_credit_card(
            @Header("Authorization") String token,
            @Field("token") String stripe_token
    );

    @DELETE("/apikoda/delete_credit_card")
    Observable<PaymentEntityResponse> delete_credit_card(
            @Header("Authorization") String token,
            @Query("id") String id
    );

    @GET("https://maps.googleapis.com/maps/api/geocode/json")
    Observable<GoogleGeoListEntityResponse> geocode(
            @Query("latlng") String latlng,
            @Query("key") String key
    );

    @GET("/apikoda/addresses")
    Observable<AddressListEntityResponse> addresses(
            @Header("Authorization") String token
    );

    @POST("/apikoda/new_address")
    @FormUrlEncoded
    Observable<AddressEntityResponse> new_address(
            @Header("Authorization") String token,
            @Field("address") String address,
            @Field("city") String city,
            @Field("postcode") String postcode,
            @Field("country") String country,
            @Field("country code") String country_code,
            @Field("lat") String lat,
            @Field("lng") String lng
    );

    @GET("/apikoda/user_info")
    Observable<UserEntityResponse> user_info(
            @Header("Authorization") String token
    );

    @POST("/apikoda/change_password")
    @FormUrlEncoded
    Observable<UserEntityResponse> change_password(
            @Header("Authorization") String token,
            @Field("old_password") String old_password,
            @Field("new_password") String password
    );

    @POST("/apikoda/update_profile")
    @FormUrlEncoded
    Observable<UserEntityResponse> update_profile(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("avatar") String avatar
    );

    @POST("/apikoda/message_send")
    @FormUrlEncoded
    Observable<MessageEntityResponse> message_send(
            @Header("Authorization") String token,
            @Field("order") String order,
            @Field("receiver") String receiver,
            @Field("message") String message,
            @Field("type") String type
    );

    @POST("/apikoda/device")
    @FormUrlEncoded
    Observable<MetaDataEntityResponse> device(
            @Header("Authorization") String access_token,
            @Field("token") String token
    );


}
