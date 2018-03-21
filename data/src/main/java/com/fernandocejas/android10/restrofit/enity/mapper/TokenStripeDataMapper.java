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
package com.fernandocejas.android10.restrofit.enity.mapper;

import com.fernandocejas.android10.order.domain.Token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 *
 */
@Singleton
public class TokenStripeDataMapper {

    @Inject
    TokenStripeDataMapper() {
    }

    public Token transform(com.stripe.android.model.Token stripeToken) {
        Token token = null;
        if (stripeToken != null) {
            token = new Token();
            token.setId(stripeToken.getId());
        }
        return token;
    }

    public List<Token> transform(Collection<com.stripe.android.model.Token> stripeTokenCollection) {
        final List<Token> tokens = new ArrayList<>();
        for (com.stripe.android.model.Token stripeToken : stripeTokenCollection) {
            final Token token = transform(stripeToken);
            if (token != null) {
                tokens.add(token);
            }
        }
        return tokens;
    }
}
