package com.areo.design.holidays.acl.impl.rainbow;

import com.areo.design.holidays.acl.PayloadTemplateACL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;

@Data
@Slf4j
//FIXME: Is Payload interface really needed?
public class RainbowPayloadTemplateACL implements PayloadTemplateACL {
    private List<String> miastaWyjazdu;
    private List<String> panstwa;
    private List<String> regiony; //TODO: use regions in RELEASE 2
    private String terminWyjazduMin;
    private String terminWyjazduMax;
    private List<String> typyTransportu;
    private List<String> wyzywienia;
    private Konfiguracja konfiguracja;
    private Sortowanie sortowanie;
    private String kategoriaHoteluMin;
    private String kategoriaHoteluMax;
    private boolean czyGrupowac;
    private boolean czyCenaZaWszystkich;
    private Paginacja paginacja;
    private boolean czyImprezaWeekendowa;

    @Data
    @AllArgsConstructor
    public static class Konfiguracja {
        private List<String> wiek;
        private String liczbaPokoi;
    }

    @Data
    @AllArgsConstructor
    public static class Paginacja {
        private String przeczytane;
        private String iloscDoPobrania;
    }

    @Data
    @AllArgsConstructor
    public static class Sortowanie {
        private boolean czyPoDacie;
        private boolean czyPoCenie;
        private boolean czyPoOcenach;
        private boolean czyPoPolecanych;
        private boolean czyDesc;
    }

    public void zwiekszPaginacje() {
        int nowePrzeczytane = Integer.parseInt(this.paginacja.przeczytane) + RAINBOW_TOURS.getOffersToDownload();
        log.info("zmieniono paginacje z: {}, na: {}", this.paginacja.przeczytane, nowePrzeczytane);
        this.getPaginacja().setPrzeczytane(String.valueOf(nowePrzeczytane));
    }
}
