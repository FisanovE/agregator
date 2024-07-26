package com.example.agregator;

import com.example.agregator.aggregate.AggregateInfo;
import com.example.agregator.aggregate.AggregateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для для обработки запроса на получение агрегированной информации.
 */
@RequestMapping(path = "/aggregate")
@RestController
@RequiredArgsConstructor
public class Controller {

    private final AggregateService aggregateService;

    /**
     * Обрабатывает GET запрос на получение агрегированной информации.
     *
     * @param currency - идентификатор валютной пары, может принимать несколько значений через запятую
     * @param cityName - название города, имеет приоритет перед поиском по координатам
     * @param latitude - координата широты
     * @param longitude - координата долготы
     * @return объект AggregateInfo с агрегированной информацией
     */
    @GetMapping
    public AggregateInfo getAggregateInfo(@RequestParam String currency,
                                          @RequestParam(name = "city", required = false) String cityName,
                                          @RequestParam(name = "lat", required = false) Double latitude,
                                          @RequestParam(name = "lon", required = false) Double longitude) {
        return aggregateService.getAggregateInfo(currency, cityName, latitude, longitude);
    }
}
