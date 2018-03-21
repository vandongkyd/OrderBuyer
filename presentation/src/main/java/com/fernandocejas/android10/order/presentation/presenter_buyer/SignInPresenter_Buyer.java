package com.fernandocejas.android10.order.presentation.presenter_buyer;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.order.domain.Model_buyer.User_Buyer;
import com.fernandocejas.android10.order.domain.interactor.PostFirebaseToken;
import com.fernandocejas.android10.order.domain.interactor_buyer.SignIn_Buyer;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.SignInView;
import com.fernandocejas.android10.order.presentation.view.activity.SignInActivity;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import javax.inject.Inject;

/**
 * Created by vandongluong on 3/12/18.
 */
@PerActivity
public class SignInPresenter_Buyer implements Presenter {

    private SignInView signInView;
    private final SignIn_Buyer signInUseCase;
    private final PostFirebaseToken postFirebaseTokenUseCase;

    private boolean is_recall;
    @Inject
    public SignInPresenter_Buyer(SignIn_Buyer signInUseCase,PostFirebaseToken postFirebaseTokenUseCase) {
        this.signInUseCase = signInUseCase;
        this.postFirebaseTokenUseCase = postFirebaseTokenUseCase;
    }
    public void setView(@NonNull SignInView view) {
        this.signInView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.signInUseCase.dispose();
        this.postFirebaseTokenUseCase.dispose();
        this.signInView = null;

    }
    public void setIs_recall(boolean is_recall) {
        this.is_recall = is_recall;
    }

    public void callSignIn_buyer(String email, String password) {
        this.hideViewRetry();
        this.showViewLoading();
        this.signIn_buyer(email, password);
    }

    private void signIn_buyer(String email, String password) {
        this.signInUseCase.execute(new SignInObserver(), SignIn_Buyer.Params.forSignIn(email, password));
    }


    public void navigateToSignUp() {
        if (signInView.activity() instanceof SignInActivity) {
            ((SignInActivity) signInView.activity()).navigateToSignUp();
        }
    }

    public void navigateToOrderList() {
        if (signInView.activity() instanceof SignInActivity) {
            ((SignInActivity) signInView.activity()).navigateToOrderList();
        }
    }

    public void navigationActivation(String token, String phone, boolean is_recall) {
        if (signInView.activity() instanceof SignInActivity) {
            ((SignInActivity) signInView.activity()).navigateToActivation(token, phone, is_recall);
        }
    }

    private void goBack() {
        if (signInView.activity() instanceof SignInActivity) {
            ((SignInActivity) signInView.activity()).goBack();
        }
    }

    private void showViewLoading() {
        this.signInView.showLoading();
    }

    private void hideViewLoading() {
        this.signInView.hideLoading();
    }

    private void showViewRetry() {
        this.signInView.showRetry();
    }

    private void hideViewRetry() {
        this.signInView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.signInView.context(),
                errorBundle.getException());
        this.signInView.showError(errorMessage);
    }
    private void saveAccessToken(String token) {
        PreferencesUtility.getInstance(this.signInView.context().getApplicationContext())
                .writeString(PreferencesUtility.PREF_TOKEN, token);
    }


    private final class SignInObserver extends DefaultObserver<User_Buyer> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //

        }

        @Override
        public void onNext(User_Buyer user) {
            if (user != null) {
                if (user.getStatus().equals(Constants.USER_ACTIVE)) {
                    Constants.USER_ID = user.getId();
                    Constants.USER_NAME = user.getName();
                    Constants.USER_AVATAR = user.getAvatar();
                    Constants.USER_PHONE = user.getPhone();
                    Constants.USER_EMAIL = user.getEmail();
                    //save token
                    if (!is_recall) {
                        //go to order list

                    } else {

                    }
                } else if (user.getStatus().equals(Constants.USER_NOT_ACTIVE)) {
                    //go to activation

                }
            }
        }
    }
}
