package com.example.agregator.aggregate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/aggregate")
@RestController
@RequiredArgsConstructor
public class Controller {

    private final AggregateService aggregateService;

    @GetMapping
    public AggregateInfo getAggregateInfo(@RequestParam String currency,
                                             @RequestParam(required = false) String q,
                                             @RequestParam(required = false) Double lat,
                                             @RequestParam(required = false) Double lon) {
        return aggregateService.getAggregateInfo(currency, q, lat, lon);
    }
}
