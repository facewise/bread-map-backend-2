package com.depromeet.breadmapbackend.domain.bakery.report;

import com.depromeet.breadmapbackend.domain.bakery.Bakery;
import com.depromeet.breadmapbackend.domain.bakery.BakeryRepository;
import com.depromeet.breadmapbackend.domain.bakery.report.dto.BakeryAddReportRequest;
import com.depromeet.breadmapbackend.domain.bakery.report.dto.BakeryReportImageRequest;
import com.depromeet.breadmapbackend.domain.bakery.report.dto.BakeryUpdateReportRequest;
import com.depromeet.breadmapbackend.domain.user.User;
import com.depromeet.breadmapbackend.domain.user.UserRepository;
import com.depromeet.breadmapbackend.global.ImageType;
import com.depromeet.breadmapbackend.global.S3Uploader;
import com.depromeet.breadmapbackend.global.converter.FileConverter;
import com.depromeet.breadmapbackend.global.exception.DaedongException;
import com.depromeet.breadmapbackend.global.exception.DaedongStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BakeryReportServiceImpl implements BakeryReportService {
    private final BakeryRepository bakeryRepository;
    private final UserRepository userRepository;
    private final BakeryUpdateReportRepository bakeryUpdateReportRepository;
    private final BakeryAddReportRepository bakeryAddReportRepository;
    private final BakeryReportImageRepository bakeryReportImageRepository;

    @Transactional(rollbackFor = Exception.class)
    public void bakeryAddReport(String username, BakeryAddReportRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new DaedongException(DaedongStatus.USER_NOT_FOUND));
        BakeryAddReport bakeryAddReport = BakeryAddReport.builder()
                .name(request.getName()).location(request.getLocation()).content(request.getContent())
                .user(user).build();
        bakeryAddReportRepository.save(bakeryAddReport);
    }

    @Transactional(rollbackFor = Exception.class)
    public void bakeryUpdateReport(String username, Long bakeryId, BakeryUpdateReportRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new DaedongException(DaedongStatus.USER_NOT_FOUND));
        Bakery bakery = bakeryRepository.findById(bakeryId).orElseThrow(() -> new DaedongException(DaedongStatus.BAKERY_NOT_FOUND));
        BakeryUpdateReport bakeryUpdateReport = BakeryUpdateReport.builder()
                .bakery(bakery).user(user).reason(BakeryUpdateReason.ETC).content(request.getContent()).build();
        bakeryUpdateReportRepository.save(bakeryUpdateReport);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            if (request.getImages().size() > 5) throw new DaedongException(DaedongStatus.IMAGE_NUM_EXCEED_EXCEPTION);
            for (String image : request.getImages()) {
                BakeryUpdateReportImage.builder().bakery(bakery).report(bakeryUpdateReport).image(image).build();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void bakeryReportImage(String username, Long bakeryId, BakeryReportImageRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new DaedongException(DaedongStatus.USER_NOT_FOUND));
        Bakery bakery = bakeryRepository.findById(bakeryId).orElseThrow(() -> new DaedongException(DaedongStatus.BAKERY_NOT_FOUND));

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            if (request.getImages().size() > 10) throw new DaedongException(DaedongStatus.IMAGE_NUM_EXCEED_EXCEPTION);
            for (String image : request.getImages()) {
                bakeryReportImageRepository.save(BakeryReportImage.builder().bakery(bakery).image(image).user(user).build());
            }
        }
    }
}
