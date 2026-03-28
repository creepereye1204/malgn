package com.malgn.web.contents;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "콘텐츠 생성 및 수정 요청 데이터")
public class ContentsRequest {
    
    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    @Schema(description = "콘텐츠 제목", example = "새로운 콘텐츠 제목")
    private String title;
    
    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    @Schema(description = "콘텐츠 내용", example = "여기에 상세 내용을 입력하세요.")
    private String description;
}
