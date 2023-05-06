package com.depromeet.breadmapbackend.domain.admin.bakery;

import com.depromeet.breadmapbackend.domain.admin.bakery.dto.*;
import com.depromeet.breadmapbackend.domain.admin.bakery.param.AdminBakeryFilter;
import com.depromeet.breadmapbackend.domain.admin.bakery.param.AdminBakeryImageType;
import com.depromeet.breadmapbackend.global.annotation.EnumCheck;
import com.depromeet.breadmapbackend.global.dto.ApiResponse;
import com.depromeet.breadmapbackend.global.dto.PageResponseDto;
import com.depromeet.breadmapbackend.global.exception.ValidationGroups;
import com.depromeet.breadmapbackend.global.exception.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated(ValidationSequence.class)
@RestController
@RequestMapping("/v1/admin/bakeries")
@RequiredArgsConstructor
public class AdminBakeryController {
    private final AdminBakeryService adminBakeryService;

    @GetMapping("/alarm-bar")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AdminBakeryAlarmBar> getBakeryAlarmBar() {
        return new ApiResponse<>(adminBakeryService.getBakeryAlarmBar());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponseDto<AdminSimpleBakeryDto>> getBakeryList(
            @RequestParam(required = false)
            List<@EnumCheck(groups = ValidationGroups.PatternCheckGroup.class) AdminBakeryFilter> filterBy,
            @RequestParam(required = false) String name, @RequestParam int page) {
        return new ApiResponse<>(adminBakeryService.getBakeryList(filterBy, name, page));
    }

    @GetMapping("/{bakeryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AdminBakeryDto> getBakery(@PathVariable Long bakeryId) {
        return new ApiResponse<>(adminBakeryService.getBakery(bakeryId));
    }

//    @GetMapping("/search")
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse<PageResponseDto<AdminSimpleBakeryDto>> searchBakeryList(
//            @RequestParam String name, @RequestParam int page) {
//        return new ApiResponse<>(adminBakeryService.searchBakeryList(name, page));
//    }

    @GetMapping("/location")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<BakeryLocationDto> getBakeryLatitudeLongitude(@RequestParam String address) {
        return new ApiResponse<>(adminBakeryService.getBakeryLatitudeLongitude(address));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BakeryAddDto> addBakery(@RequestBody @Validated(ValidationSequence.class) BakeryAddRequest request) {
        return new ApiResponse<>(adminBakeryService.addBakery(request));
    }

    @PatchMapping("/{bakeryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBakery(
            @PathVariable Long bakeryId, @RequestBody @Validated(ValidationSequence.class) BakeryUpdateRequest request) {
        adminBakeryService.updateBakery(bakeryId, request);
    }

    @DeleteMapping("/{bakeryId}/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(
            @PathVariable Long bakeryId, @PathVariable Long productId) {
        adminBakeryService.deleteProduct(bakeryId, productId);
    }

    @GetMapping("/{bakeryId}/is-new-bar")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AdminBakeryIsNewDto> getAdminBakeryIsNewBar(@PathVariable Long bakeryId) {
        return new ApiResponse<>(adminBakeryService.getAdminBakeryIsNewBar(bakeryId));
    }

    @GetMapping("/{bakeryId}/image-bar")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AdminImageBarDto> getAdminImageBar(@PathVariable Long bakeryId) {
        return new ApiResponse<>(adminBakeryService.getAdminImageBar(bakeryId));
    }

    @GetMapping("/{bakeryId}/images/{imageType}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponseDto<AdminImageDto>> getAdminImages(
            @PathVariable Long bakeryId,
            @PathVariable @EnumCheck(groups = ValidationGroups.PatternCheckGroup.class) AdminBakeryImageType imageType,
            @RequestParam int page) {
        return new ApiResponse<>(adminBakeryService.getAdminImages(bakeryId, imageType, page));
    }

    @DeleteMapping("/{bakeryId}/images/{imageType}/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdminImage(
            @PathVariable Long bakeryId,
            @PathVariable @EnumCheck(groups = ValidationGroups.PatternCheckGroup.class) AdminBakeryImageType imageType,
            @PathVariable Long imageId) {
        adminBakeryService.deleteAdminImage(bakeryId, imageType, imageId);
    }

    @GetMapping("/{bakeryId}/product-add-reports")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponseDto<ProductAddReportDto>> getProductAddReports(
            @PathVariable Long bakeryId, @RequestParam int page) {
        return new ApiResponse<>(adminBakeryService.getProductAddReports(bakeryId, page));
    }

    @PatchMapping("/{bakeryId}/product-add-reports/{reportId}/images")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerProductAddImage(
            @PathVariable Long bakeryId, @PathVariable Long reportId,
            @RequestBody @Validated(ValidationSequence.class) AdminImageRegisterRequest request) {
        adminBakeryService.registerProductAddImage(bakeryId, reportId, request);
    }

    @DeleteMapping("/{bakeryId}/product-add-reports/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductAddReport(@PathVariable Long bakeryId, @PathVariable Long reportId) {
        adminBakeryService.deleteProductAddReport(bakeryId, reportId);
    }

    @GetMapping("/{bakeryId}/update-reports")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponseDto<BakeryUpdateReportDto>> getBakeryUpdateReports(
            @PathVariable Long bakeryId, @RequestParam int page) {
        return new ApiResponse<>(adminBakeryService.getBakeryUpdateReports(bakeryId, page));
    }

    @PatchMapping("/{bakeryId}/update-reports/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeBakeryUpdateReport(@PathVariable Long bakeryId, @PathVariable Long reportId) {
        adminBakeryService.changeBakeryUpdateReport(bakeryId, reportId);
    }

    @DeleteMapping("/{bakeryId}/update-reports/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBakeryUpdateReport(@PathVariable Long bakeryId, @PathVariable Long reportId) {
        adminBakeryService.deleteBakeryUpdateReport(bakeryId, reportId);
    }

    @GetMapping("/{bakeryId}/new-reviews")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponseDto<NewReviewDto>> getNewReviews(@PathVariable Long bakeryId, @RequestParam int page) {
        return new ApiResponse<>(adminBakeryService.getNewReviews(bakeryId, page));
    }

    @PatchMapping("/{bakeryId}/new-reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hideNewReview(@PathVariable Long bakeryId, @PathVariable Long reviewId) {
        adminBakeryService.hideNewReview(bakeryId, reviewId);
    }

    @PatchMapping("/{bakeryId}/new-reviews/{reviewId}/images")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerNewReviewImage(
            @PathVariable Long bakeryId, @PathVariable Long reviewId,
            @RequestBody @Validated(ValidationSequence.class) AdminImageRegisterRequest request) {
        adminBakeryService.registerNewReviewImage(bakeryId, reviewId, request);
    }

    @DeleteMapping("/{bakeryId}/new-reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long bakeryId, @PathVariable Long reviewId) {
        adminBakeryService.deleteReview(bakeryId, reviewId);
    }

//    @DeleteMapping("/{bakeryId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteBakery(@PathVariable Long bakeryId) {
//        adminBakeryService.deleteBakery(bakeryId);
//    }
}
