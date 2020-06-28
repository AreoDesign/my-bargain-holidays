package com.areo.design.holidays.acl;

import java.util.List;

public class HotelRainbowACL {
    public Blok1 Blok1;
    public Opinie Opinie;

    public class Blok1 {
        public Integer HotelId;
        public String NazwaHotelu;
        public Double GwiazdkiHotelu;
        public List<Lokalizacja> Lokalizacja;

        public class Lokalizacja {
            public String NazwaLokalizacji;
            public String CzyRegion;
        }
    }

    public class Opinie {
        public Double OcenaOgolna;
    }
}
