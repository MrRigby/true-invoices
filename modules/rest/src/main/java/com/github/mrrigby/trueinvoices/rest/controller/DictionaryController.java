package com.github.mrrigby.trueinvoices.rest.controller;

import com.github.mrrigby.trueinvoices.model.PaymentKind;
import com.github.mrrigby.trueinvoices.model.TaxRate;
import com.github.mrrigby.trueinvoices.rest.domain.DictionaryItem;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All the dictionary data used within the application.
 *
 * @author MrRigby
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    /**
     * The most frequently used tax rates.
     */
    private static final List<DictionaryItem<Short>> COMMON_TAX_RATES =
            Arrays.asList(
                    (short) 0, (short) 3, (short) 7, (short) 23
            ).stream()
                    .map(taxValue -> TaxRate.valueOf(taxValue))
                    .map(rate -> new DictionaryItem<>(rate.toShort(), rate.toString()))
                    .collect(Collectors.toList());

    @RequestMapping(value = "/taxRates", method = RequestMethod.GET)
    public HttpEntity<List<DictionaryItem<Short>>> listTaxRates() {
        return new ResponseEntity<>(COMMON_TAX_RATES, HttpStatus.OK);
    }

    /**
     * All payment kinds.
     */
    private static final List<DictionaryItem<String>> COMMON_PAYMENT_KINDS =
            Arrays.asList(PaymentKind.values()).stream()
                    .map(kind -> new DictionaryItem<>(kind.name(), kind.name()))
                    .collect(Collectors.toList());

    @RequestMapping(value = "/paymentKinds", method = RequestMethod.GET)
    public HttpEntity<List<DictionaryItem<String>>> listPaymentKinds() {
        return new ResponseEntity<>(COMMON_PAYMENT_KINDS, HttpStatus.OK);
    }
}
