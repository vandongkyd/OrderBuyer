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
package com.fernandocejas.android10.sample.presentation.internal.di.modules;

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
import com.fernandocejas.android10.restrofit.repository.AddressDataRepository;
import com.fernandocejas.android10.restrofit.repository.ContactDataRepository;
import com.fernandocejas.android10.restrofit.repository.CountryDataRepository;
import com.fernandocejas.android10.restrofit.repository.EventDataRepository;
import com.fernandocejas.android10.restrofit.repository.GoogleGeoDataRepository;
import com.fernandocejas.android10.restrofit.repository.MessageDataRepository;
import com.fernandocejas.android10.restrofit.repository.MetaDataDataRepository;
import com.fernandocejas.android10.restrofit.repository.OrderDataRepository;
import com.fernandocejas.android10.restrofit.repository.PaymentDataRepository;
import com.fernandocejas.android10.restrofit.repository.ProductDataRepository;
import com.fernandocejas.android10.restrofit.repository.SettingDataRepository;
import com.fernandocejas.android10.restrofit.repository.StripeDataRepository;
import com.fernandocejas.android10.restrofit.repository.TickerDataRepository;
import com.fernandocejas.android10.restrofit.repository.UserDataRepository;
import com.fernandocejas.android10.restrofit.repository_buyer.AddressDataRepository_Buyer;
import com.fernandocejas.android10.restrofit.repository_buyer.OfferDataRepository_Buyer;
import com.fernandocejas.android10.restrofit.repository_buyer.OrderDataRepository_Buyer;
import com.fernandocejas.android10.restrofit.repository_buyer.UserDataRepository_Buyer;
import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.cache.UserCacheImpl;
import com.fernandocejas.android10.sample.data.executor.JobExecutor;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.presentation.AndroidApplication;
import com.fernandocejas.android10.sample.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    UserCache provideUserCache(UserCacheImpl userCache) {
        return userCache;
    }

    @Provides
    @Singleton
    com.fernandocejas.android10.sample.domain.repository.UserRepository provideUserRepository_inSample(
            com.fernandocejas.android10.sample.data.repository.UserDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides
    @Singleton
    OrderRepository provideOrderRepository(OrderDataRepository orderDataRepository) {
        return orderDataRepository;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides
    @Singleton
    UserRepository_Buyer provideUserRepository_buyer(UserDataRepository_Buyer userDataRepository_buyer) {
        return userDataRepository_buyer;
    }
    @Provides
    @Singleton
    AddressRepository_Buyer provideAddressRepository_buyer(AddressDataRepository_Buyer addressDataRepository_buyer) {
        return addressDataRepository_buyer;
    }
    @Provides
    @Singleton
    OrderRepository_Buyer provideOrderRepository_buyer(OrderDataRepository_Buyer orderDataRepository_buyer) {
        return orderDataRepository_buyer;
    }

    @Provides
    @Singleton
    OfferRepository_Buyer provideOfferRepository_buyer(OfferDataRepository_Buyer offerDataRepository_buyer) {
        return offerDataRepository_buyer;
    }


    @Provides
    @Singleton
    ContactRepository provideContacttRepository(ContactDataRepository contactDataRepository) {
        return contactDataRepository;
    }

    @Provides
    @Singleton
    CountryRepository provideCountryRepository(CountryDataRepository countryDataRepository) {
        return countryDataRepository;
    }

    @Provides
    @Singleton
    EventRepository provideEventRepository(EventDataRepository eventDataRepository) {
        return eventDataRepository;
    }

    @Provides
    @Singleton
    ProductRepository provideProductRepository(ProductDataRepository productDataRepository) {
        return productDataRepository;
    }
    @Provides
    @Singleton
    TickerRepository provideTickerRepository(TickerDataRepository tickerDataRepository) {
        return tickerDataRepository;
    }


    @Provides
    @Singleton
    SettingRepository provideSettingRepository(SettingDataRepository settingDataRepository) {
        return settingDataRepository;
    }

    @Provides
    @Singleton
    PaymentRepository providePaymentRepository(PaymentDataRepository paymentDataRepository) {
        return paymentDataRepository;
    }

    @Provides
    @Singleton
    StripeRepository provideStripeRepository(StripeDataRepository stripeDataRepository) {
        return stripeDataRepository;
    }

    @Provides
    @Singleton
    AddressRepository provideAddressRepository(AddressDataRepository addressDataRepository) {
        return addressDataRepository;
    }

    @Provides
    @Singleton
    GoogleGeoRepository provideGoogleGeoRepository(GoogleGeoDataRepository googleGeoDataRepository) {
        return googleGeoDataRepository;
    }

    @Provides
    @Singleton
    MessageRepository provideMessageRepository(MessageDataRepository messageDataRepository) {
        return messageDataRepository;
    }

    @Provides
    @Singleton
    MetaDataRepository provideMetaDataRepository(MetaDataDataRepository metaDataDataRepository) {
        return metaDataDataRepository;
    }
}
