package com.areo.design.holidays.service.request.payload.impl.rainbow;

import com.areo.design.holidays.service.request.payload.Payload;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
//FIXME: consider migration to ACL. Is Payload interface really needed?
public class RainbowPayload implements Payload {
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
}
