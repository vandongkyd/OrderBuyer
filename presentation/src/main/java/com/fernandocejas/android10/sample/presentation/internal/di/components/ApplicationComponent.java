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
package com.fernandocejas.android10.sample.presentation.internal.di.components;

import android.content.Context;

import com.fernandocejas.android10.order.domain.repository.AddressRepository;
import com.fernandocejas.android10.order.domain.repository_buyer.AddressRepository_Buyer;
import com.fernandocejas.android10.order.domain.repository.ContactRepository;
import com.fernandocejas.android10.order.domain.repository.CountryRepository;
import com.fernandocejas.android10.order.domain.repository.EventRepository;
import com.fernandocejas.android10.order.domain.repository.GoogleGeoRepository;
import com.fernandocejas.android10.order.domain.repository.MessageRepository;
import com.fernandocejas.android10.order.domain.repository.MetaDataRepository;
import com.fernandocejas.android10.order.domain.repository.OrderRepository;
import com.fernandocejas.android10.order.domain.repository.PaymentRepository;
import com.fernandocejas.android10.order.domain.repository.ProductRepository;
import com.fernandocejas.android10.order.domain.repository.SettingRepository;
import com.fernandocejas.android10.order.domain.repository.StripeRepository;
import com.fernandocejas.android10.order.domain.repository.TickerRepository;
import com.fernandocejas.android10.order.domain.repository.UserRepository;
import com.fernandocejas.android10.order.domain.repository_buyer.OfferRepository_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.OrderRepository_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.UserRepository_Buyer;
import com.fernandocejas.android10.order.presentation.service.MyFirebaseInstanceIDService;
import com.fernandocejas.android10.order.presentation.view.dialog.SlideshowDialog;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ApplicationModule;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    void inject(SlideshowDialog slideshowDialog);

    void inject(MyFirebaseInstanceIDService service);

    //Exposed to sub-graphs.
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    com.fernandocejas.android10.sample.domain.repository.UserRepository userRepository_inSample();

    OrderRepository orderRepository();

    UserRepository userRepository();

    CountryRepository countryRepository();

    EventRepository eventRepository();

    ProductRepository productRepository();

    UserRepository_Buyer userRepository_buyer();

    AddressRepository_Buyer addressRepository_buyer();

    OrderRepository_Buyer orderRepository_buyer();

    OfferRepository_Buyer offerRepository_buyer();

    TickerRepository tickerRepository();

    ContactRepository contactRepository();

    SettingRepository settingRepository();

    PaymentRepository paymentRepository();

    StripeRepository tripeRepository();

    AddressRepository addressRepository();

    GoogleGeoRepository googleGeoRepository();

    MessageRepository messageRepository();

    MetaDataRepository metaDataRepository();

}
