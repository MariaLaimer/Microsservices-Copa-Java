package br.edu.atitus.currencyservice.clients;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BCBClientFallback implements BCBClient {

    @Override
    public BCBResponse getBCBCurrency(String moeda) {
        BCBResponse.BCBCurrencies mockCotacao = new BCBResponse.BCBCurrencies(5.05);
        return new BCBResponse(List.of(mockCotacao));
    }
}