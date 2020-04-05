package com.areo.design.holidays.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class OfferDetailDto implements Serializable {
    private static final long serialVersionUID = -5585468068816091365L;
    private Long id;
    private UUID offerId;
    private LocalDateTime requestTime;
    private Integer standardPricePerPerson;
    private Integer discountPricePerPerson;
}
