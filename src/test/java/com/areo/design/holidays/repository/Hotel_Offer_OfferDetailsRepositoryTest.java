package com.areo.design.holidays.repository;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.entity.HotelEntity;
import com.areo.design.holidays.entity.OfferDetailEntity;
import com.areo.design.holidays.entity.OfferEntity;
import com.google.common.collect.Sets;
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
class Hotel_Offer_OfferDetailsRepositoryTest {

    private static final String SAMPLE_HOTEL_NAME = "Rethymno Village";
    private static final String SAMPLE_HOTEL_CODE = "HER80035";
    private static final Country SAMPLE_COUNTRY = GREECE;
    private static final double SAMPLE_OPINION_VALUE = 4.50d;
    private static final double SAMPLE_STANDARD_VALUE = 5d;
    private static final String SAMPLE_OFFER_CODE = "WAWCHQ20201004185020201004202010110840L07HER80035DZX1AA02Ch01BD20190305";
    private static final String SAMPLE_OFFER_URL = "https://www.tui.pl/wypoczynek/grecja/kreta/rethymno-village-her80035/OfferCodeWS/WAWCHQ20201004185020201004202010110840L07HER80035DZX1AA02Ch01BD20190305";
    private static final BoardType SAMPLE_BOARD_TYPE = ALL_INCLUSIVE;
    private static final Integer SAMPLE_PRICE = 1589;
    private static final Integer SAMPLE_DURATION = 7;
    private static final LocalDateTime SAMPLE_REQUEST_TIME = LocalDateTime.of(2019, 12, 16, 23, 8);
    private static final LocalDateTime SAMPLE_DEPARTURE_TIME = LocalDateTime.of(2020, 10, 4, 18, 50);

    private static final String ID = "id";

    private final HotelRepository hotelRepository;
    private final OfferRepository offerRepository;
    private final OfferDetailRepository offerDetailRepository;

    @Autowired
    public Hotel_Offer_OfferDetailsRepositoryTest(HotelRepository hotelRepository,
                                                  OfferRepository offerRepository,
                                                  OfferDetailRepository offerDetailRepository) {
        this.hotelRepository = hotelRepository;
        this.offerRepository = offerRepository;
        this.offerDetailRepository = offerDetailRepository;
    }

    @Test
    @Order(1)
    void whenEnteringTest_thenRepositoriesAreEmpty() {
        assertThat(hotelRepository.findAll()).isEmpty();
        assertThat(offerRepository.findAll()).isEmpty();
        assertThat(offerDetailRepository.findAll()).isEmpty();
    }

    @Test
    @Order(2)
    void whenSavedHotel_thenHotelPersistedToDB() {
        //given
        HotelEntity hotel = prepareHotel();
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
        HotelEntity hotel = prepareHotel();
        //when
        Throwable thrown = catchThrowable(() -> hotelRepository.save(hotel));
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
        HotelEntity hotel = hotelRepository.findByNameAndCountry(SAMPLE_HOTEL_NAME, SAMPLE_COUNTRY).orElseThrow(EntityNotFoundException::new);
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
        HotelEntity hotel = hotelRepository.findByNameAndCountry(SAMPLE_HOTEL_NAME, SAMPLE_COUNTRY).orElseThrow(EntityNotFoundException::new);
        hotel.getOffers().stream()
                .filter(offer -> SAMPLE_OFFER_URL.equals(offer.getUrl()))
                .findFirst()
                .ifPresent(offer -> offer.addOfferDetail(prepareOfferDetail()));
        //when
        hotel = hotelRepository.save(hotel);
        //then
        assertThat(hotel)
                .extracting(HotelEntity::getOffers)
                .isNotNull();
        assertThat(hotel.getOffers())
                .hasSize(1)
                .extracting(OfferEntity::getOfferDetails)
                .isNotNull();
        assertThat(offerDetailRepository.findByOfferUrl(SAMPLE_OFFER_URL))
                .contains(hotel.getOffers().stream()
                        .map(OfferEntity::getOfferDetails)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
                        .toArray(OfferDetailEntity[]::new)
                );
    }

    @Test
    @Order(7)
    void whenOfferDetailsSaved_thenOfferUpdatedWithDetail() {
        HotelEntity hotel = hotelRepository.findByNameAndCountry(SAMPLE_HOTEL_NAME, SAMPLE_COUNTRY).orElseThrow(EntityNotFoundException::new);
        OfferDetailEntity offerDetail = prepareOfferDetail(SAMPLE_REQUEST_TIME.plusHours(4));
        hotel.getOffers().stream()
                .filter(offer -> SAMPLE_OFFER_URL.equals(offer.getUrl()))
                .findFirst()
                .ifPresent(offer -> offer.addOfferDetail(offerDetail));
        //when
        hotel = hotelRepository.save(hotel);
        //then
        assertThat(hotel)
                .extracting(HotelEntity::getOffers)
                .isNotNull();
        assertThat(hotel.getOffers())
                .hasSize(1)
                .extracting(OfferEntity::getOfferDetails)
                .isNotNull();
        assertThat(offerDetailRepository.findByOfferUrl(SAMPLE_OFFER_URL))
                .contains(hotel.getOffers().stream()
                        .map(OfferEntity::getOfferDetails)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
                        .toArray(OfferDetailEntity[]::new)
                );
        assertThat(offerRepository.findByUrl(SAMPLE_OFFER_URL).orElseThrow(EntityNotFoundException::new).getOfferDetails())
                .hasSize(2)
                .usingElementComparatorIgnoringFields(ID)
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

    private HotelEntity prepareHotel() {
        return HotelEntity.builder()
                .name(SAMPLE_HOTEL_NAME)
                .code(SAMPLE_HOTEL_CODE)
                .opinion(SAMPLE_OPINION_VALUE)
                .standard(SAMPLE_STANDARD_VALUE)
                .country(SAMPLE_COUNTRY)
                .offers(Sets.newLinkedHashSet())
                .build();
    }

    private OfferEntity prepareOffer() {
        return OfferEntity.builder()
                .code(SAMPLE_OFFER_CODE)
                .url(SAMPLE_OFFER_URL)
                .departureTime(SAMPLE_DEPARTURE_TIME)
                .boardType(SAMPLE_BOARD_TYPE)
                .duration(SAMPLE_DURATION)
                .offerDetails(Sets.newLinkedHashSet())
                .build();
    }

    private OfferDetailEntity prepareOfferDetail(LocalDateTime requestTime) {
        return OfferDetailEntity.builder()
                .requestTime(requestTime)
                .standardPricePerPerson(SAMPLE_PRICE)
                .build();
    }

    private OfferDetailEntity prepareOfferDetail() {
        return prepareOfferDetail(SAMPLE_REQUEST_TIME);
    }
}