/**
 * Copyright 2016 Google Inc. All Rights Reserved.
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

package com.fernandocejas.android10.order.presentation.service;

import android.util.Log;

import com.fernandocejas.android10.order.domain.MetaData;
import com.fernandocejas.android10.order.domain.interactor.PostFirebaseToken;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.AndroidApplication;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Inject
    PostFirebaseToken postFirebaseTokenUseCase;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        //save token to Local
        PreferencesUtility.getInstance(this)
                .writeString(PreferencesUtility.PREF_FIRE_BASE_TOKEN, token);

        //fetch to server
        String access_token = PreferencesUtility.getInstance(this)
                .readString(PreferencesUtility.PREF_TOKEN, null);
        if (access_token != null && !access_token.isEmpty()) {//has signIn
            Log.d(TAG, "post_start : " + token);
            fetchFirebaseToken(access_token, token);
        }
    }

    private void fetchFirebaseToken(String access_token, String firebase_token) {
        postFirebaseTokenUseCase.execute(new PostFirebaseTokenObserver(), PostFirebaseToken.Params.forPostToken(access_token, firebase_token));
    }

    private final class PostFirebaseTokenObserver extends DefaultObserver<MetaData> {

        @Override
        public void onComplete() {
            Log.d(TAG, "post_complete");
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            Log.d(TAG, "post_error");
        }

        @Override
        public void onNext(MetaData metaData) {

        }
    }

}
