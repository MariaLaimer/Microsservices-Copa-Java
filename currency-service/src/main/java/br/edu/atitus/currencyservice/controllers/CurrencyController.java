package br.edu.atitus.currencyservice.controllers;

import br.edu.atitus.currencyservice.clients.BCBClient;
import br.edu.atitus.currencyservice.clients.BCBResponse;
import br.edu.atitus.currencyservice.dtos.CurrencyDTO;
import br.edu.atitus.currencyservice.entities.CurrencyEntity;
import br.edu.atitus.currencyservice.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("currency")
public class CurrencyController {

    @Value("${server.port}")
    private String port;

    @Value("${convert.sleep:0}")
    private int sleep;

    private final CurrencyRepository repository;
    private final BCBClient bcbClient;
    private final CacheManager cacheManager;

    public CurrencyController(CurrencyRepository repository, BCBClient bcbClient, CacheManager cacheManager) {
        this.repository = repository;
        this.bcbClient = bcbClient;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/convert")
    public ResponseEntity<CurrencyDTO> getConvert(
            @RequestParam String source,
            @RequestParam String target
    ) throws Exception {

        Thread.sleep(sleep);

        source = source.toUpperCase();
        target = target.toUpperCase();

        String dataSource = "None";
        CurrencyEntity currency = new CurrencyEntity();
        currency.setSourceCurrency(source);
        currency.setTargetCurrency(target);

        if (source.equals(target)) {
            currency.setConversionRate(1.0);
        } else {
            try {
                Double sourceRate = 1.0;
                Double targetRate = 1.0;
                String nameCache = "BCBCache";


                if (!source.equals("BRL")) {
                    BCBResponse cacheResponse = cacheManager.getCache(nameCache).get(source, BCBResponse.class);

                    if (cacheResponse == null) {
                        cacheResponse = bcbClient.getBCBCurrency(source);
                        if (cacheResponse.value().isEmpty()) throw new Exception("Currency Not Found " + source);
                        cacheManager.getCache(nameCache).put(source, cacheResponse);
                    }

                    sourceRate = cacheResponse.value().get(0).cotacaoVenda();
                }


                if (!target.equals("BRL")) {
                    BCBResponse cacheResponse = cacheManager.getCache(nameCache).get(target, BCBResponse.class);

                    if (cacheResponse == null) {
                        cacheResponse = bcbClient.getBCBCurrency(target);
                        if (cacheResponse.value().isEmpty()) throw new Exception("Currency Not Found " + target);
                        cacheManager.getCache(nameCache).put(target, cacheResponse);
                    }
                    targetRate = cacheResponse.value().get(0).cotacaoVenda();
                }

                currency.setConversionRate(sourceRate / targetRate);
                dataSource = "Banco Central do Brasil";
            } catch (Exception e) {
                currency = repository.findBySourceCurrencyAndTargetCurrency(source, target)
                        .orElseThrow(() -> new Exception("Currency Not Found"));
                dataSource = "Local database";
            }
        }

        String enviroment = "Currency Service running on Port " + port + " - " + dataSource;

        CurrencyDTO dto = new CurrencyDTO(
                currency.getSourceCurrency(),
                currency.getTargetCurrency(),
                currency.getConversionRate(),
                enviroment
        );

        return ResponseEntity.ok(dto);
    }
}