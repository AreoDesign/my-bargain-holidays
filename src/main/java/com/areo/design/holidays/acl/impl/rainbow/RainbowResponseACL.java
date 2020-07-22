package com.areo.design.holidays.acl.impl.rainbow;

import com.areo.design.holidays.acl.ResponseACL;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RainbowResponseACL implements ResponseACL {

    @Setter
    @Expose(serialize = false, deserialize = false)
    private LocalDateTime timestamp;
    private List<Bloczek> Bloczki;

    @Getter
    public static class Bloczek {
        private Blok1 Blok1;
        private Opinie Opinie;
        private String OfertaUrl;                   //OfferDto.url (without http://r.pl prefix)
        private String DataWKodzieProduktu;         //OfferDto.departureTime
        private List<Wyzywienie> Wyzywienia;
        private List<Oferta> Ceny;

        @Getter
        public static class Blok1 {
            private Integer HotelId;                //HotelDto.code
            private String NazwaHotelu;             //HotelDto.name
            private Double GwiazdkiHotelu;          //HotelDto.standard
            private List<Lokalizacja> Lokalizacja;

            @Getter
            public static class Lokalizacja {
                private String NazwaLokalizacji;    //CzyRegion ? HotelDto.region : HotelDto.country
                private boolean CzyRegion;
            }
        }

        @Getter
        public static class Opinie {
            private Double OcenaOgolna;             //HotelDto.opinion
        }

        @Getter
        public static class Wyzywienie {
            private String NazwaUrl;                //OfferDto.boardType
            private boolean CzyPodstawowe;
        }

        @Getter
        public static class Oferta {
            private Integer LiczbaDni;              //OfferDto.duration
            private String PakietId;                //OfferDetailDto.offerId
            private Integer CenaAktualna;           //OfferDetailDto.price
        }
    }

}
