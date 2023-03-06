package com.depromeet.breadmapbackend.web.controller.admin.dto;

import com.depromeet.breadmapbackend.domain.product.ProductAddReport;
import com.depromeet.breadmapbackend.domain.product.ProductAddReportImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ProductAddReportDto {
    private Long reportId;
    private String mainImage;
    private List<ProductAddReportImageDto> imageList;
    private LocalDateTime createdAt;
    private String name;
    private String price;
    private String nickName;


    @Getter
    @NoArgsConstructor
    public static class ProductAddReportImageDto {
        private Long imageId;
        private String image;

        @Builder
        public ProductAddReportImageDto(ProductAddReportImage productAddReportImage) {
            this.imageId = productAddReportImage.getId();
            this.image = productAddReportImage.getImage();
        }
    }

    @Builder
    public ProductAddReportDto(ProductAddReport report) {
        this.reportId = report.getId();
        this.mainImage = report.getImages().stream()
                .filter(ProductAddReportImage::getIsMain).collect(Collectors.toList()).get(0).getImage();
        this.imageList = report.getImages().stream().map(ProductAddReportImageDto::new).collect(Collectors.toList());
        this.createdAt = report.getCreatedAt();
        this.name = report.getName();
        this.price = report.getPrice();
        this.nickName = report.getUser().getNickName();
    }
}
