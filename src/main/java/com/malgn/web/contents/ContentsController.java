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

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@Valid @RequestBody ContentsRequest request) {
        Contents contents = contentsService.create(request.getTitle(), request.getDescription());
        return contents.getId();
    }

    @GetMapping
    public Page<ContentsResponse> findAll(@ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        return contentsService.findAll(pageable)
                .map(ContentsResponse::new);
    }

    @GetMapping("/{id}")
    public ContentsResponse findById(@PathVariable Long id) {
        Contents contents = contentsService.findById(id);
        return new ContentsResponse(contents);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody ContentsRequest request) {
        contentsService.update(id, request.getTitle(), request.getDescription());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        contentsService.delete(id);
    }
}
