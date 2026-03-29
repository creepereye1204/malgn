package com.malgn.web.contents;

import com.malgn.domain.contents.Contents;
import com.malgn.service.contents.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;

@Tag(name = "Contents", description = "콘텐츠 관리 API")
@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;

    @GetMapping("/me")
    public Map<String, String> me(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return Map.of("username", "anonymous");
        }
        return Map.of("username", userDetails.getUsername());
    }

    @Operation(summary = "새 콘텐츠 생성", description = "새로운 콘텐츠를 시스템에 등록합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@Valid @RequestBody ContentsRequest request) {
        Contents contents = contentsService.create(request.getTitle(), request.getDescription());
        return contents.getId();
    }

    @Operation(summary = "콘텐츠 목록 조회", description = "등록된 모든 콘텐츠를 페이징하여 조회합니다.")
    @GetMapping
    public Page<ContentsResponse> findAll(@ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        return contentsService.findAll(pageable)
                .map(ContentsResponse::new);
    }

    @Operation(summary = "콘텐츠 상세 조회", description = "ID를 통해 특정 콘텐츠의 상세 내용을 조회합니다. 조회 시 조회수가 1 증가합니다.")
    @GetMapping("/{id}")
    public ContentsResponse findById(@PathVariable Long id) {
        Contents contents = contentsService.findById(id);
        return new ContentsResponse(contents);
    }

    @Operation(summary = "콘텐츠 수정", description = "ID에 해당하는 콘텐츠의 제목과 내용을 수정합니다. 작성자 본인 또는 관리자만 가능합니다.")
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody ContentsRequest request) {
        contentsService.update(id, request.getTitle(), request.getDescription());
    }

    @Operation(summary = "콘텐츠 삭제", description = "ID에 해당하는 콘텐츠를 삭제합니다. 작성자 본인 또는 관리자만 가능합니다.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        contentsService.delete(id);
    }
}
