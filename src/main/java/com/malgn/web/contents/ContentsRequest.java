package com.malgn.web.contents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContentsRequest {
    
    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String title;
    
    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    private String description;
}
