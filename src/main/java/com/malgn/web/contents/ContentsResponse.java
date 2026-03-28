package com.malgn.web.contents;

import com.malgn.domain.contents.Contents;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "콘텐츠 응답 데이터")
public class ContentsResponse {
    
    @Schema(description = "고유 아이디", example = "1")
    private Long id;
    
    @Schema(description = "제목", example = "CMS 입문 가이드")
    private String title;
    
    @Schema(description = "내용", example = "CMS를 시작하는 방법입니다.")
    private String description;
    
    @Schema(description = "조회수", example = "42")
    private Long viewCount;
    
    @Schema(description = "생성한 사용자", example = "user1")
    private String createdBy;
    
    @Schema(description = "생성일", example = "2026-03-28T12:00:00")
    private LocalDateTime createdDate;
    
    @Schema(description = "마지막 수정한 사용자", example = "admin")
    private String lastModifiedBy;
    
    @Schema(description = "마지막 수정일", example = "2026-03-28T15:30:00")
    private LocalDateTime lastModifiedDate;

    @Builder
    public ContentsResponse(Contents contents) {
        this.id = contents.getId();
        this.title = contents.getTitle();
        this.description = contents.getDescription();
        this.viewCount = contents.getViewCount();
        this.createdBy = contents.getCreatedBy();
        this.createdDate = contents.getCreatedDate();
        this.lastModifiedBy = contents.getLastModifiedBy();
        this.lastModifiedDate = contents.getLastModifiedDate();
    }
}
