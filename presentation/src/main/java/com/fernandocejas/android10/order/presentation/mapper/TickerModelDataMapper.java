package com.fernandocejas.android10.order.presentation.mapper;

import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.order.domain.Ticker;
import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.order.presentation.model.TickerModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by vandongluong on 3/8/18.
 */
@PerActivity
public class TickerModelDataMapper {
    @Inject
    public TickerModelDataMapper() {
    }
    public TickerModel transform(Ticker ticker) {
        if (ticker == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final TickerModel tickerModel = new TickerModel();
        tickerModel.setId(ticker.getId());
        tickerModel.setName(ticker.getName());
        tickerModel.setSymbol(ticker.getSymbol());
        tickerModel.setRank(ticker.getRank());
        tickerModel.setPrice_usd(ticker.getPrice_usd());
        tickerModel.setPrice_btc(ticker.getPrice_btc());
        tickerModel.setH_volume_usd(ticker.getH_volume_usd());
        tickerModel.setMarket_cap_usd(ticker.getMarket_cap_usd());
        tickerModel.setAvailable_supply(ticker.getAvailable_supply());
        tickerModel.setTotal_supply(ticker.getTotal_supply());
        tickerModel.setMax_supply(ticker.getMax_supply());
        tickerModel.setPercent_change_1h(ticker.getPercent_change_1h());
        tickerModel.setPercent_change_24h(ticker.getPercent_change_24h());
        tickerModel.setPercent_change_7d(ticker.getPercent_change_7d());
        tickerModel.setLast_updated(ticker.getLast_updated());
        return tickerModel;
    }
    public Collection<TickerModel> transform(Collection<Ticker> tickers) {
        Collection<TickerModel> tickerModels;

        if (tickers != null && !tickers.isEmpty()) {
            tickerModels = new ArrayList<>();
            for (Ticker ticker : tickers) {
                tickerModels.add(transform(ticker));
            }
        } else {
            tickerModels = Collections.emptyList();
        }

        return tickerModels;
    }
    public Ticker transform(TickerModel tickerModel) {
        if (tickerModel == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final Ticker ticker = new Ticker();
        ticker.setId(tickerModel.getId());
        ticker.setName(tickerModel.getName());
        ticker.setSymbol(tickerModel.getSymbol());
        ticker.setRank(tickerModel.getRank());
        ticker.setPrice_usd(tickerModel.getPrice_usd());
        ticker.setPrice_btc(tickerModel.getPrice_btc());
        ticker.setH_volume_usd(tickerModel.getH_volume_usd());
        ticker.setMarket_cap_usd(tickerModel.getMarket_cap_usd());
        ticker.setAvailable_supply(tickerModel.getAvailable_supply());
        ticker.setTotal_supply(tickerModel.getTotal_supply());
        ticker.setMax_supply(tickerModel.getMax_supply());
        ticker.setPercent_change_1h(tickerModel.getPercent_change_1h());
        ticker.setPercent_change_24h(tickerModel.getPercent_change_24h());
        ticker.setPercent_change_7d(tickerModel.getPercent_change_7d());
        ticker.setLast_updated(tickerModel.getLast_updated());
        return ticker;
    }
    public Collection<Ticker> transformToDomain(Collection<TickerModel> tickerModels) {
        Collection<Ticker> tickers;

        if (tickerModels != null && !tickerModels.isEmpty()) {
            tickers = new ArrayList<>();
            for (TickerModel tickerModel : tickerModels) {
                tickers.add(transform(tickerModel));
            }
        } else {
            tickers = Collections.emptyList();
        }

        return tickers;
    }
}
