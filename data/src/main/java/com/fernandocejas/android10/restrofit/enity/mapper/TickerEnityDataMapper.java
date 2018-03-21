package com.fernandocejas.android10.restrofit.enity.mapper;

import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.order.domain.Ticker;
import com.fernandocejas.android10.restrofit.enity.PaymentEntity;
import com.fernandocejas.android10.restrofit.enity.PaymentEntityResponse;
import com.fernandocejas.android10.restrofit.enity.PaymentListEntityResponse;
import com.fernandocejas.android10.restrofit.enity.TickerEnity;
import com.fernandocejas.android10.restrofit.enity.TickerEnityResponse;
import com.fernandocejas.android10.restrofit.enity.TickerListEnityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by vandongluong on 3/8/18.
 */
@Singleton
public class TickerEnityDataMapper {

    @Inject
    TickerEnityDataMapper() {
    }
    public Ticker transform(TickerEnity tickerEnity) {
        Ticker ticker = null;
        if (tickerEnity != null) {
            ticker = new Ticker();
            ticker.setId(tickerEnity.getId());
            ticker.setName(tickerEnity.getName());
            ticker.setSymbol(tickerEnity.getSymbol());
            ticker.setRank(tickerEnity.getRank());
            ticker.setPrice_usd(tickerEnity.getPrice_usd());
            ticker.setPrice_btc(tickerEnity.getPrice_btc());
            ticker.setH_volume_usd(tickerEnity.getH_volume_usd());
            ticker.setMarket_cap_usd(tickerEnity.getMarket_cap_usd());
            ticker.setAvailable_supply(tickerEnity.getAvailable_supply());
            ticker.setTotal_supply(tickerEnity.getTotal_supply());
            ticker.setMax_supply(tickerEnity.getMax_supply());
            ticker.setPercent_change_1h(tickerEnity.getPercent_change_1h());
            ticker.setPercent_change_24h(tickerEnity.getPercent_change_24h());
            ticker.setPercent_change_7d(tickerEnity.getPercent_change_7d());
            ticker.setLast_updated(tickerEnity.getLast_updated());

        }
        return ticker;
    }
    public TickerEnity transform(Ticker ticker) {
        TickerEnity tickerEnity = null;
        if (ticker != null) {
            tickerEnity = new TickerEnity();
            tickerEnity.setId(ticker.getId());
            tickerEnity.setName(ticker.getName());
            tickerEnity.setSymbol(ticker.getSymbol());
            tickerEnity.setRank(ticker.getRank());
            tickerEnity.setPrice_usd(ticker.getPrice_usd());
            tickerEnity.setPrice_btc(ticker.getPrice_btc());
            tickerEnity.setH_volume_usd(ticker.getH_volume_usd());
            tickerEnity.setMarket_cap_usd(ticker.getMarket_cap_usd());
            tickerEnity.setAvailable_supply(ticker.getAvailable_supply());
            tickerEnity.setTotal_supply(ticker.getTotal_supply());
            tickerEnity.setMax_supply(ticker.getMax_supply());
            tickerEnity.setPercent_change_1h(ticker.getPercent_change_1h());
            tickerEnity.setPercent_change_24h(ticker.getPercent_change_24h());
            tickerEnity.setPercent_change_7d(ticker.getPercent_change_7d());
            tickerEnity.setLast_updated(ticker.getLast_updated());
        }
        return tickerEnity;
    }
    public List<Ticker> transform(Collection<TickerEnity> tickerEnities) {
        final List<Ticker> tickers = new ArrayList<>();
        for (TickerEnity tickerEnity : tickerEnities) {
            final Ticker ticker = transform(tickerEnity);
            if (ticker != null) {
                tickers.add(ticker);
            }
        }
        return tickers;
    }
    public List<TickerEnity> transformToEntity(Collection<Ticker> tickers) {
        final List<TickerEnity> tickerEnities = new ArrayList<>();
        for (Ticker ticker : tickers) {
            final TickerEnity tickerEnity = transform(ticker);
            if (tickerEnity != null) {
                tickerEnities.add(tickerEnity);
            }
        }
        return tickerEnities;
    }
//    public List<Ticker> transform(TickerListEnityResponse tickerListEnityResponse) throws Exception {
//        List<Ticker> tickerList = null;
//        if (tickerListEnityResponse != null) {
////            if (tickerListEnityResponse.status() == false) {
////                throw new Exception(tickerListEnityResponse.message());
////            }
//           // tickerList = transform(tickerListEnityResponse.);
//        }
//        return tickerList;
//    }

    public Ticker transform(TickerEnityResponse tickerEnityResponse) throws Exception {
        Ticker ticker = null;
        if (tickerEnityResponse != null) {
            if (tickerEnityResponse.status() == false) {
                throw new Exception(tickerEnityResponse.message());
            }
            ticker = transform(tickerEnityResponse.data());
        }
        return ticker;
    }
}
