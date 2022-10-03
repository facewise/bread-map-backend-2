package com.depromeet.breadmapbackend.web.controller.admin;

import com.depromeet.breadmapbackend.security.token.JwtToken;
import com.depromeet.breadmapbackend.service.admin.AdminService;
import com.depromeet.breadmapbackend.web.advice.ValidationSequence;
import com.depromeet.breadmapbackend.web.controller.admin.dto.*;
import com.depromeet.breadmapbackend.web.controller.common.ApiResponse;
import com.depromeet.breadmapbackend.web.controller.common.PageResponseDto;
import com.depromeet.breadmapbackend.web.controller.common.SliceResponseDto;
import com.depromeet.breadmapbackend.web.controller.user.dto.ReissueRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void adminJoin(@RequestBody @Validated(ValidationSequence.class) AdminJoinRequest request) {
        adminService.adminJoin(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<JwtToken> adminLogin(@RequestBody @Validated(ValidationSequence.class) AdminLoginRequest request) {
        return new ApiResponse<>(adminService.adminLogin(request));
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<JwtToken> reissue(@RequestBody @Validated(ValidationSequence.class) ReissueRequest request) {
        return new ApiResponse<>(adminService.reissue(request));
    }

    @GetMapping("/bakery/all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponseDto<AdminSimpleBakeryDto>> getBakeryList(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ApiResponse<>(adminService.getBakeryList(pageable));
    }

    @GetMapping("/bakery/{bakeryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AdminBakeryDto> getBakery(@PathVariable Long bakeryId) {
        return new ApiResponse<>(adminService.getBakery(bakeryId));
    }

    @GetMapping("/bakery/search")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponseDto<AdminSimpleBakeryDto>> searchBakeryList(
            @RequestParam String name,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ApiResponse<>(adminService.searchBakeryList(name, pageable));
    }

    @GetMapping("/bakery/location")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<BakeryLocationDto> getBakeryLatitudeLongitude(@RequestParam String address) throws JsonProcessingException {
        return new ApiResponse<>(adminService.getBakeryLatitudeLongitude(address));
    }

    @PostMapping("/bakery")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBakery(
            @RequestPart AddBakeryRequest request,
            @RequestPart(required = false) MultipartFile bakeryImage,
            @RequestPart List<MultipartFile> breadImageList) throws IOException {
        adminService.addBakery(request, bakeryImage, breadImageList);
    }

    @PostMapping("/bakery/{bakeryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBakery(
            @PathVariable Long bakeryId, @RequestPart UpdateBakeryRequest request,
            @RequestPart(required = false) MultipartFile bakeryImage,
            @RequestPart List<MultipartFile> breadImageList) throws IOException {
        adminService.updateBakery(bakeryId, request, bakeryImage, breadImageList);
    }

    @GetMapping("/bakery/{bakeryId}/image")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<SliceResponseDto<AdminBakeryReviewImageDto>> getBakeryReviewImages(
            @PathVariable Long bakeryId,
            @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ApiResponse<>(adminService.getBakeryReviewImages(bakeryId ,pageable));
    }

    @DeleteMapping("/bakery/{bakeryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBakery(@PathVariable Long bakeryId) {
        adminService.deleteBakery(bakeryId);
    }

    @GetMapping("/bakery/report/all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponseDto<SimpleBakeryAddReportDto>> getBakeryReportList(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ApiResponse<>(adminService.getBakeryReportList(pageable));
    }

    @GetMapping("/bakery/report/{reportId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<BakeryAddReportDto> getBakeryReport(@PathVariable Long reportId) {
        return new ApiResponse<>(adminService.getBakeryReport(reportId));
    }

    @PatchMapping("/bakery/report/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBakeryAddReportStatus(@PathVariable Long reportId, @RequestBody UpdateBakeryReportStatusRequest request) {
        adminService.updateBakeryAddReportStatus(reportId, request);
    }

    @GetMapping("/review/report/all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponseDto<AdminReviewReportDto>> getReviewReportList(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ApiResponse<>(adminService.getReviewReportList(pageable));
    }

    @PatchMapping("/review/report/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReviewStatus(@PathVariable Long reportId) {
        adminService.updateReviewStatus(reportId);
    }

    @GetMapping("/user/all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponseDto<AdminUserDto>> getUserList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ApiResponse<>(adminService.getUserList(pageable));
    }

    @PatchMapping("/user/{userId}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUserBlock(@PathVariable Long userId) {
        adminService.changeUserBlock(userId);
    }
}
