package com.depromeet.breadmapbackend.domain.search.dto.keyword.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentKeywords {
    private List<String> recentKeywords;
}
