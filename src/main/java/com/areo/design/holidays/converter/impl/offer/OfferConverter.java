//package com.areo.design.holidays.converter.impl.offer;
//
//import com.areo.design.holidays.converter.EntityDtoConverter;
//import com.areo.design.holidays.dictionary.City;
//import com.areo.design.holidays.entity.offer.OfferEntity;
//import com.areo.design.holidays.valueobjects.atomic.Board;
//import com.areo.design.holidays.valueobjects.atomic.DateAndTime;
//import com.areo.design.holidays.valueobjects.atomic.Duration;
//import com.areo.design.holidays.valueobjects.atomic.OfferCode;
//import com.areo.design.holidays.valueobjects.atomic.RequestTime;
//import com.areo.design.holidays.valueobjects.atomic.Url;
//import com.areo.design.holidays.valueobjects.complex.Departure;
//import com.areo.design.holidays.valueobjects.complex.Price;
//import com.areo.design.holidays.valueobjects.offer.Offer;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//import static java.util.Objects.isNull;
//
//@RequiredArgsConstructor
//public class OfferConverter implements EntityDtoConverter<OfferEntity, Offer> {
//
//    @Override
//    public OfferEntity convertToEntity(Offer dto) {
//        return isNull(dto) ? null :
//                OfferEntity.builder()
//                        .id(dto.getId())
//                        .code(dto.getCode())
//                        .url(dto.getUrl())
//                        .departureTime(dto.getDepartureTime())
//                        .boardType(dto.getBoard())
//                        .duration(dto.getDuration())
//                        .offerDetails(Set.copyOf(offerDetailConverter.convertToEntities(dto.getDetails())))
//                        .build();
//    }
//
//    @Override
//    public Offer convertToDto(OfferEntity entity) {
//        return isNull(entity) ? null :
//                Offer.builder()
//                        .code(OfferCode.of(entity.getCode()))
//                        .url(Url.of(entity.getUrl()))
//                        .departure(Departure.builder()
//                                .departureCity(City.getByPolishName(entity.getDepartureCity()))
//                                .departureTime(DateAndTime.of(entity.getDepartureTime()))
//                                .build())
//                        .board(Board.of(entity.getBoardType()))
//                        .duration(Duration.of(entity.getDuration()))
//                        .price(Price.of(entity.getOfferDetails()))
//                        .requestTime(RequestTime.of(entity.getOfferDetails()))
//                        .build();
//    }
//}
