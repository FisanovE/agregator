package com.example.agregator;

import com.example.agregator.aggregate.AggregateInfo;
import com.example.agregator.aggregate.AggregateService;
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
                                          @RequestParam(name = "q", required = false) String cityName,
                                          @RequestParam(name = "lat", required = false) Double latitude,
                                          @RequestParam(name = "lon", required = false) Double longitude) {
        return aggregateService.getAggregateInfo(currency, cityName, latitude, longitude);
    }
}
