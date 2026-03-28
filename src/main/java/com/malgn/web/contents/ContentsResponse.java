package com.malgn.web.contents;

import com.malgn.domain.contents.Contents;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentsResponse {
    private Long id;
    private String title;
    private String description;
    private Long viewCount;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
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
