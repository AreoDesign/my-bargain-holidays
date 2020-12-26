//package com.areo.design.holidays.converter.impl.offer;
//
//import com.areo.design.holidays.converter.EntityDtoConverter;
//import com.areo.design.holidays.entity.offer.HotelEntity;
//import com.areo.design.holidays.valueobjects.offer.Hotel;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//import static java.util.Objects.isNull;
//
//@Component
//@RequiredArgsConstructor
//public class HotelConverter implements EntityDtoConverter<HotelEntity, Hotel> {
//
//    private final OfferConverter offerConverter;
//
//    @Override
//    public HotelEntity convertToEntity(Hotel dto) {
//        return isNull(dto) ? null :
//                HotelEntity.builder()
//                        .id(dto.getId())
//                        .code(dto.getCode())
//                        .name(dto.getName())
//                        .standard(dto.getStandard())
//                        .opinion(dto.getScore())
//                        .country(dto.getCountry())
//                        .offers(Set.copyOf(offerConverter.convertToEntities(dto.getOffers())))
//                        .build();
//    }
//
//    @Override
//    public Hotel convertToDto(HotelEntity entity) {
//        return isNull(entity) ? null :
//                Hotel.builder()
//                        .id(entity.getId())
//                        .code(entity.getCode())
//                        .name(entity.getName())
//                        .standard(entity.getStandard())
//                        .opinion(entity.getOpinion())
//                        .country(entity.getCountry())
//                        .offers(Set.copyOf(offerConverter.convertToDtos(entity.getOffers())))
//                        .build();
//    }
//
//}
