package com.areo.design.holidays.repository;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.entity.Hotel;
import com.areo.design.holidays.entity.Offer;
import com.areo.design.holidays.entity.OfferDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.areo.design.holidays.dictionary.BoardType.ALL_INCLUSIVE;
import static com.areo.design.holidays.dictionary.Country.GREECE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class HotelOfferAndOfferDetailsRepositoryTest {

    private static final String SAMPLE_HOTEL_NAME = "Rethymno Village";
    private static final String SAMPLE_HOTEL_CODE = "HER80035";
    private static final Country SAMPLE_COUNTRY = GREECE;
    private static final double SAMPLE_OPINION_VALUE = 4.50d;
    private static final double SAMPLE_STANDARD_VALUE = 5d;

    private static final String OFFERS_FIELD_NAME_LAZILY_INITIATED = "offers";
    private static final String ID = "id";

    private static final String SAMPLE_OFFER_CODE = "WAWCHQ20201004185020201004202010110840L07HER80035DZX1AA02Ch01BD20190305";
    private static final String SAMPLE_OFFER_URL = "https://www.tui.pl/wypoczynek/grecja/kreta/rethymno-village-her80035/OfferCodeWS/WAWCHQ20201004185020201004202010110840L07HER80035DZX1AA02Ch01BD20190305";
    private static final BoardType SAMPLE_BOARD_TYPE = ALL_INCLUSIVE;
    private static final Integer SAMPLE_PRICE = 1589;
    private static final Integer SAMPLE_DURATION = 7;
    private static final LocalDateTime SAMPLE_REQUEST_TIME = LocalDateTime.of(2019, 12, 16, 23, 8);
    private static final LocalDateTime SAMPLE_DEPARTURE_TIME = LocalDateTime.of(2020, 10, 4, 18, 50);

    private final HotelRepository hotelRepository;
    private final OfferRepository offerRepository;
    private final OfferDetailRepository offerDetailRepository;

    @Autowired
    public HotelOfferAndOfferDetailsRepositoryTest(HotelRepository hotelRepository,
                                                   OfferRepository offerRepository,
                                                   OfferDetailRepository offerDetailRepository) {
        this.hotelRepository = hotelRepository;
        this.offerRepository = offerRepository;
        this.offerDetailRepository = offerDetailRepository;
    }

    @Test
    @Order(1)
    void whenEnteringTest_thenRepositoryIsEmpty() {
        assertThat(hotelRepository.findAll()).isEmpty();
        assertThat(offerRepository.findAll()).isEmpty();
        assertThat(offerDetailRepository.findAll()).isEmpty();
    }

    @Test
    @Order(2)
    void whenSavedHotel_thenHotelPersistedToDB() {
        //given
        Hotel hotel = prepareHotel();
        //when
        hotelRepository.save(hotel);
        //then
        assertThat(hotel.getId()).isNotNull();
        assertThat(hotelRepository.findById(hotel.getId()).orElseThrow(EntityNotFoundException::new))
                .hasNoNullFieldsOrProperties()
                .isEqualTo(hotel);
        assertThat(hotelRepository.findAll())
                .hasSize(1);
        assertThat(offerRepository.findAll())
                .isEmpty();
        assertThat(offerDetailRepository.findAll())
                .isEmpty();
    }

    @Test
    @Order(3)
    void whenSavingExistingHotel_thenThrowException() {
        //given
        Hotel hotel = prepareHotel();
        //when
        Throwable thrown = catchThrowable(() -> {
            hotelRepository.save(hotel);
        });
        //then
        assertThat(thrown).isExactlyInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @Order(4)
    void whenFindHotelByNameAndCountry_thenEntityFetched() {
        assertThat(hotelRepository.findByNameAndCountry(SAMPLE_HOTEL_NAME, SAMPLE_COUNTRY).orElseThrow(EntityNotFoundException::new))
                .isEqualToIgnoringGivenFields(prepareHotel(), ID);
    }

    @Test
    @Order(5)
    void whenHotelSavedWithOffer_thenOfferPersistedOnCascade() {
        //given
        Hotel hotel = hotelRepository.findByNameAndCountry(SAMPLE_HOTEL_NAME, SAMPLE_COUNTRY).orElseThrow(EntityNotFoundException::new);
        hotel.addOffer(prepareOffer());
        //when
        hotel = hotelRepository.save(hotel);
        //then
        assertThat(offerRepository.findByUrl(SAMPLE_OFFER_URL).orElseThrow(EntityNotFoundException::new))
                .isIn(hotel.getOffers());
    }

    @Test
    @Order(6)
    void whenHotelFetchedWithOfferAndSavedWithOfferDetails_thenOfferDetailsPersistedOnCascade() {
        //given
        Hotel hotel = hotelRepository.findByNameAndCountry(SAMPLE_HOTEL_NAME, SAMPLE_COUNTRY).orElseThrow(EntityNotFoundException::new);
        hotel.getOffers().stream()
                .filter(offer -> SAMPLE_OFFER_URL.equals(offer.getUrl()))
                .findFirst()
                .ifPresent(offer -> offer.addOfferDetail(prepareOfferDetail()));
        //when
        hotel = hotelRepository.save(hotel);
        //then
        assertThat(hotel)
                .extracting(Hotel::getOffers)
                .isNotNull();
        assertThat(hotel.getOffers())
                .hasSize(1)
                .extracting(Offer::getOfferDetails)
                .isNotNull();
        assertThat(offerDetailRepository.findByOfferUrl(SAMPLE_OFFER_URL).orElseThrow(EntityNotFoundException::new))
                .contains(hotel.getOffers().stream()
                        .map(Offer::getOfferDetails)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
                        .toArray(OfferDetail[]::new)
                );
    }

    @Test
    @Order(7)
    void whenOfferDetailsSaved_thenOfferUpdatedWithDetail() {
        Hotel hotel = hotelRepository.findByNameAndCountry(SAMPLE_HOTEL_NAME, SAMPLE_COUNTRY).orElseThrow(EntityNotFoundException::new);
        OfferDetail offerDetail = prepareOfferDetail(SAMPLE_REQUEST_TIME.plusHours(4));
        hotel.getOffers().stream()
                .filter(offer -> SAMPLE_OFFER_URL.equals(offer.getUrl()))
                .findFirst()
                .ifPresent(offer -> offer.addOfferDetail(offerDetail));
        //when
        hotel = hotelRepository.save(hotel);
        //then
        assertThat(hotel)
                .extracting(Hotel::getOffers)
                .isNotNull();
        assertThat(hotel.getOffers())
                .hasSize(1)
                .extracting(Offer::getOfferDetails)
                .isNotNull();
        assertThat(offerDetailRepository.findByOfferUrl(SAMPLE_OFFER_URL).orElseThrow(EntityNotFoundException::new))
                .contains(hotel.getOffers().stream()
                        .map(Offer::getOfferDetails)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
                        .toArray(OfferDetail[]::new)
                );
        assertThat(offerRepository.findByUrl(SAMPLE_OFFER_URL).orElseThrow(EntityNotFoundException::new).getOfferDetails())
                .hasSize(2)
                .usingElementComparatorIgnoringFields("id")
                .contains(offerDetail);
    }

    @Test
    @Order(8)
    void whenDeleteAllInBatch_thenEachRepositoryIsEmpty() {
        //when
        offerDetailRepository.deleteAllInBatch();
        offerRepository.deleteAllInBatch();
        hotelRepository.deleteAllInBatch();
        //then
        assertThat(hotelRepository.findAll()).isEmpty();
        assertThat(offerRepository.findAll()).isEmpty();
        assertThat(offerDetailRepository.findAll()).isEmpty();
    }

    private Hotel prepareHotel() {
        return Hotel.builder()
                .name(SAMPLE_HOTEL_NAME)
                .code(SAMPLE_HOTEL_CODE)
                .opinion(SAMPLE_OPINION_VALUE)
                .standard(SAMPLE_STANDARD_VALUE)
                .country(SAMPLE_COUNTRY)
                .build();
    }

    private Offer prepareOffer() {
        return Offer.builder()
                .code(SAMPLE_OFFER_CODE)
                .url(SAMPLE_OFFER_URL)
                .departureTime(SAMPLE_DEPARTURE_TIME)
                .boardType(SAMPLE_BOARD_TYPE)
                .duration(SAMPLE_DURATION)
                .build();
    }

    private OfferDetail prepareOfferDetail(LocalDateTime requestTime) {
        return OfferDetail.builder()
                .requestTime(requestTime)
                .originalPricePerPerson(SAMPLE_PRICE)
                .discountPricePerPerson(SAMPLE_PRICE)
                .build();
    }

    private OfferDetail prepareOfferDetail() {
        return prepareOfferDetail(SAMPLE_REQUEST_TIME);
    }
}