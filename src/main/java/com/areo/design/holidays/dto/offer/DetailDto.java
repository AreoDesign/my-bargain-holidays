package com.areo.design.holidays.dto.offer;

import com.areo.design.holidays.dto.EntityConvertible;
import com.areo.design.holidays.entity.offer.DetailEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class DetailDto implements Serializable, EntityConvertible<DetailEntity> {
    private static final long serialVersionUID = -5585468068816091365L;
    private Long id;
    private UUID offerId;
    private RequestTime requestTime;
    private Integer price;

    //fixme: this shall replace converter
    @Override
    public DetailEntity toEntity() {
        return DetailEntity.builder()
                .id(this.id)
                .requestTime(this.requestTime.toLocalDateTime())
                .standardPricePerPerson(this.price)
                .build();
    }

    @EqualsAndHashCode
    @ToString
    @Builder
    @AllArgsConstructor(staticName = "of")
    public static class RequestTime {
        private LocalDateTime responseHeaderTime;

        public static RequestTime blank() {
            return RequestTime.of(null);
        }

        public LocalDateTime toLocalDateTime() {
            return responseHeaderTime;
        }

        public void update(LocalDateTime responseHeaderTime) {
            this.responseHeaderTime = responseHeaderTime;
        }
    }
}
