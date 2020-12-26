package com.areo.design.holidays.acl.impl.rainbow;

import com.areo.design.holidays.acl.ResponseBodyACL;
import lombok.Getter;

import java.util.List;

@Getter
public class RainbowResponseBodyACL implements ResponseBodyACL {

    private List<Bloczek> Bloczki;

    @Getter
    public static class Bloczek {
        private Blok1 Blok1;
        private Opinie Opinie;
        private String OfertaUrl;                   //Offer.url without http://r.pl prefix)
        private String DataWKodzieProduktu;         //Offer.departure date
        private List<Wyzywienie> Wyzywienia;
        private List<Oferta> Ceny;

        @Getter
        public static class Blok1 {
            private Integer HotelId;                //Hotel.code
            private String NazwaHotelu;             //Hotel.name
            private Double GwiazdkiHotelu;          //Hotel.standard
            private List<Lokalizacja> Lokalizacja;

            @Getter
            public static class Lokalizacja {
                private String NazwaLokalizacji;    //CzyRegion ? HotelDto.region : HotelDto.country
                private boolean CzyRegion;
            }
        }

        @Getter
        public static class Opinie {
            private Double OcenaOgolna;             //Hotel.opinion
        }

        @Getter
        public static class Wyzywienie {
            private String NazwaUrl;                //Offer.boardType
            private boolean CzyPodstawowe;
        }

        @Getter
        public static class Oferta {
            private String PakietId;                //Offer.agencyId
            private Integer CenaAktualna;           //Offer.overall price
            private Integer CenaZaOsobeAktualna;    //Offer.adult price
            private Integer LiczbaDni;              //Offer.duration
            private String MiastoWyjazduUrl;        //Offer.departure city
        }
    }

}
