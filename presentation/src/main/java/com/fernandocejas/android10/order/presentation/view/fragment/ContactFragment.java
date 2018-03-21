package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.ContactModel;
import com.fernandocejas.android10.order.presentation.presenter.ContactListPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.view.ContactListView;
import com.fernandocejas.android10.order.presentation.view.adapter.ContactAdapter;
import com.fernandocejas.android10.order.presentation.view.adapter.ItemTouchHelperCallback;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vandongluong on 3/15/18.
 */

public class ContactFragment extends BaseFragment implements ContactListView {

    @Inject
    ContactListPresenter contactListPresenter;
    @Inject
    ContactAdapter contactAdapter;

    @Bind(R.id.rv_contact)
    RecyclerView rv_contact;

    private ProgressDialog progressDialog;


    public ContactFragment() {
        setRetainInstance(true);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(OrderComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecycleView();

        return fragmentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.contactListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.contactListPresenter.loadContact();

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        this.contactListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.contactListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.contactListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.contactListPresenter = null;
    }




    @Override
    public void renderContactMethodList(Collection<ContactModel> contactModels) {
      this.contactAdapter.setContactCollection(contactModels);
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = DialogFactory.createProgressDialog(activity(), R.string.processing);
        }
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(activity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }
    private void setupRecycleView() {
        this.rv_contact.setLayoutManager(new LinearLayoutManager(context()));
        this.rv_contact.setAdapter(contactAdapter);
        this.initSwipe();
    }

    private void initSwipe() {
        ItemTouchHelperCallback callback = new ItemTouchHelperCallback();
        ItemTouchHelperExtension itemTouchHelper = new ItemTouchHelperExtension(callback);
        itemTouchHelper.attachToRecyclerView(rv_contact);
        contactAdapter.setItemTouchHelperExtension(itemTouchHelper);
    }
}
