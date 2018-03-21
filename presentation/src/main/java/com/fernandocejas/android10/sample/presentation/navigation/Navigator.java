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
package com.fernandocejas.android10.sample.presentation.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.fernandocejas.android10.order.domain.Offer;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.domain.SettingByCountry;
import com.fernandocejas.android10.order.presentation.view.activity.AcceptedActivity;
import com.fernandocejas.android10.order.presentation.view.activity.AccountInformationActivity;
import com.fernandocejas.android10.order.presentation.view.activity.ActivationActivity;
import com.fernandocejas.android10.order.presentation.view.activity.AddAddressActivity;
import com.fernandocejas.android10.order.presentation.view.activity.ChatMessageActivity;
import com.fernandocejas.android10.order.presentation.view.activity.OrderActivity;
import com.fernandocejas.android10.order.presentation.view.activity.OrderDetailActivity;
import com.fernandocejas.android10.order.presentation.view.activity.OrderListActivity;
import com.fernandocejas.android10.order.presentation.view.activity.PaymentListActivity;
import com.fernandocejas.android10.order.presentation.view.activity.ProductListActivity;
import com.fernandocejas.android10.order.presentation.view.activity.SettingActivity;
import com.fernandocejas.android10.order.presentation.view.activity.SignInActivity;
import com.fernandocejas.android10.order.presentation.view.activity.SignUpActivity;
import com.fernandocejas.android10.order.presentation.view.activity.SplashScreenActivity;
import com.fernandocejas.android10.order.presentation.view.activity.TestSignInActivity;
import com.fernandocejas.android10.order.presentation.view.activity.TopWebsiteActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.UserDetailsActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.UserListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    /**
     * Goes to the user list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToUserList(Context context) {
        if (context != null) {
            Intent intentToLaunch = UserListActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the user details screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToUserDetails(Context context, int userId) {
        if (context != null) {
            Intent intentToLaunch = UserDetailsActivity.getCallingIntent(context, userId);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the account information screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToAccountInformation(Context context) {
        if (context != null) {
            Intent intentToLaunch = AccountInformationActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the add address screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToAddAddress(Context context) {
        if (context != null) {
            Intent intentToLaunch = AddAddressActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the order detail screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToOrderDetail(Context context, String id, String status) {
        if (context != null) {
            Intent intentToLaunch = OrderDetailActivity.getCallingIntent(context, id, status);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the order list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToOrderList(Context context) {
        if (context != null) {
            Intent intentToLaunch = OrderListActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the payment screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToPayment(Context context) {
        if (context != null) {
            Intent intentToLaunch = PaymentListActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the setting screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToSetting(Context context) {
        if (context != null) {
            Intent intentToLaunch = SettingActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the signIn screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToSignIn(Context context, boolean is_recall) {
        if (context != null) {
            Intent intentToLaunch = SignInActivity.getCallingIntent(context, is_recall);
            context.startActivity(intentToLaunch);
        }
    }

    ///
    public void navigateToSignin(Context context,boolean is_recall){
        if (context != null){
            Intent intent = TestSignInActivity.getCallingIntent(context,is_recall);
            context.startActivity(intent);
        }
    }



    /**
     * Goes to the signUp screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToSignUp(Context context) {
        if (context != null) {
            Intent intentToLaunch = SignUpActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the splash screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToSplashScreen(Context context) {
        if (context != null) {
            Intent intentToLaunch = SplashScreenActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the activation screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToActivation(Context context, String token, String phone, boolean is_recall) {
        if (context != null) {
            Intent intentToLaunch = ActivationActivity.getCallingIntent(context, token, phone, is_recall);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the top website screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToTopWebsite(Activity context, String url) {
        if (context != null) {
            Intent intentToLaunch = TopWebsiteActivity.getCallingIntent(context, url);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the top website screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToTopWebsiteForResult(Activity context, String url, Fragment fragment, int requestCode) {
        if (context != null) {
            Intent intentToLaunch = TopWebsiteActivity.getCallingIntentForResult(context, url, requestCode);
            fragment.startActivityForResult(intentToLaunch, requestCode);
        }
    }

    /**
     * Goes to product screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToProduct(Context context, Product product) {
        if (context != null) {
            Intent intentToLaunch = ProductListActivity.getCallingIntent(context, product);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to order screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToOrder(Context context, SettingByCountry settingByCountry) {
        if (context != null) {
            Intent intentToLaunch = OrderActivity.getCallingIntent(context, settingByCountry);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to accepted screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToAccepted(Context context,
                                   Offer offer,
                                   String quantity,
                                   String amount,
                                   String weight,
                                   String sale_tax,
                                   String service_fee,
                                   String currency) {
        if (context != null) {
            Intent intentToLaunch = AcceptedActivity.getCallingIntent(context, offer, quantity, amount, weight, sale_tax, service_fee, currency);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to chat screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToChat(Context context,
                               String order_id,
                               String provider_id,
                               String user_id,
                               String provider_avatar) {
        if (context != null) {
            Intent intentToLaunch = ChatMessageActivity.getCallingIntent(context, order_id, provider_id, user_id, provider_avatar);
            context.startActivity(intentToLaunch);
        }
    }

}
