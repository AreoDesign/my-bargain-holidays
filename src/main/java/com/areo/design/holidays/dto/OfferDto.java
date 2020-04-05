package com.areo.design.holidays.dto;

import com.areo.design.holidays.dictionary.BoardType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class OfferDto implements Serializable {
    private static final long serialVersionUID = 6982747557763042694L;
    private UUID id;
    private UUID hotelId;
    private String code;
    private String url;
    private LocalDateTime departureTime;
    private BoardType boardType;
    private Integer duration;
    private Set<OfferDetailDto> offerDetails;
}
