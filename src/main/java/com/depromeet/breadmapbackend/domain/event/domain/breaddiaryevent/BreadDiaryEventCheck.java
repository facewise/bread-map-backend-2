package com.depromeet.breadmapbackend.domain.event.domain.breaddiaryevent;

import com.depromeet.breadmapbackend.domain.event.domain.Timestamp;

public record BreadDiaryEventCheck(BreadDiaryEventCheckPK pk, BreadDiaryEventCheckTarget diary,
                                   BreadDiaryEventCheckContent content, Timestamp timestamps) {
    public String getHistoryDescription() {
        return "빵일기 "+ content.getState() +" 상태 진입으로 "+ content.getPoint()+"포인트 " + content.getIncreaseKeyword()+"되었습니다";
    }
}
