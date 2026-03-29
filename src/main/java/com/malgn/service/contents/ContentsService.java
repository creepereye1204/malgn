package com.malgn.service.contents;

import com.malgn.domain.contents.Contents;
import com.malgn.domain.contents.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentsService {

    private final ContentsRepository contentsRepository;

    public Contents create(String title, String description) {
        Contents contents = Contents.builder()
                .title(title)
                .description(description)
                .build();
        return contentsRepository.save(contents);
    }

    @Transactional(readOnly = true)
    public Page<Contents> findAll(Pageable pageable) {
        return contentsRepository.findAll(pageable);
    }

    public Contents findById(Long id) {
        contentsRepository.incrementViewCount(id);
        return contentsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("콘텐츠를 찾을 수 없습니다: " + id));
    }

    public void update(Long id, String title, String description) {
        Contents contents = contentsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("콘텐츠를 찾을 수 없습니다: " + id));

        checkPermission(contents);

        contents.update(title, description);
    }

    public void delete(Long id) {
        Contents contents = contentsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("콘텐츠를 찾을 수 없습니다: " + id));

        checkPermission(contents);

        contentsRepository.delete(contents);
    }

    private void checkPermission(Contents contents) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !contents.getCreatedBy().equals(currentUsername)) {
            throw new AccessDeniedException("해당 콘텐츠를 수정할 권한이 없습니다.");
        }
    }
}
