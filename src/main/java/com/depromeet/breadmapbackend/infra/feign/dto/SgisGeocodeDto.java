package com.depromeet.breadmapbackend.infra.feign.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SgisGeocodeDto {
    private SgisLocationDto result;

    @Getter
    public static class SgisLocationDto {
        private List<SgisLocationResultDto> resultdata;

        @Getter
        public static class SgisLocationResultDto{
            private String y;
            private String x;
        }
    }
}