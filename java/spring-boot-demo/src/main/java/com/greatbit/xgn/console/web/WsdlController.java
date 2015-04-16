package com.greatbit.xgn.console.web;

import com.greatbit.xgn.console.service.RedisDemo;
import hello.my.WeatherClient;
import hello.wsdl.Forecast;
import hello.wsdl.GetCityForecastByZIPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/wsdl")
public class WsdlController {
    @Autowired
    private RedisDemo redisDemo;

    @Autowired
    private WeatherClient weatherClient;

    @RequestMapping("/test/{code}")
    public String test(@PathVariable("code") String zipCode, Model model) {
        GetCityForecastByZIPResponse response = weatherClient.getCityForecastByZip(zipCode);
        weatherClient.printResponse(response);


        Set<Forecast> forecasts = new HashSet<>();

        if (response != null && response.getGetCityForecastByZIPResult() != null && response.getGetCityForecastByZIPResult().getForecastResult() != null && response.getGetCityForecastByZIPResult().getForecastResult().getForecast() != null)
            forecasts.addAll(response.getGetCityForecastByZIPResult().getForecastResult().getForecast());

        forecasts.forEach(f -> System.out.println(f));

        model.addAttribute("result", response.getGetCityForecastByZIPResult());
        model.addAttribute("forecasts", forecasts);
        return "wsdl/index";
    }
}
