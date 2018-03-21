package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.ContactModel;
import com.fernandocejas.android10.order.presentation.model.TickerModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.Collection;

/**
 * Created by vandongluong on 3/9/18.
 */

public interface ContactListView extends LoadDataView {
    void renderContactMethodList(Collection<ContactModel> contactModels);
}
