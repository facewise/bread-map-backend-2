package com.depromeet.breadmapbackend.domain.bakery.exception;

public class BakeryReportNotFoundException extends RuntimeException {
    public BakeryReportNotFoundException() {
        super("Bakery report is not existed.");
    }
}
