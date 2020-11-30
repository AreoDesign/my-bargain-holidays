package com.areo.design.holidays.controller;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.service.HolidayOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Downloader {

    private final HolidayOfferService holidayOfferService;

    @GetMapping("/offers/{travelAgency}")
    public HttpStatus downloadOffers(@RequestParam TravelAgency travelAgency) {
        holidayOfferService.getOffers(travelAgency);
        return HttpStatus.OK;
    }

    @GetMapping("/offers")
    public HttpStatus downloadOffers() {
        holidayOfferService.getOffers();
        return HttpStatus.OK;
    }
}
