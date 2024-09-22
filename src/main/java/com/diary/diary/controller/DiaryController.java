package com.diary.diary.controller;

import com.diary.auth.model.User;
import com.diary.diary.dto.DiaryDto;
import com.diary.diary.model.Diary;
import com.diary.diary.service.DiaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/diary")
@AllArgsConstructor
public class DiaryController {
    private DiaryService diaryService;

    @GetMapping("/get")
    public List<Diary> getDiary(
            @AuthenticationPrincipal User currentUser
    ) {
        return diaryService.getDiary(currentUser);
    }

    @PostMapping("/write")
    public ResponseEntity<?> writeDiary(
            @RequestBody DiaryDto dto,
            @AuthenticationPrincipal User currentUser
    ) {
        return diaryService.writeDiary(dto, currentUser);
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyDiary(
            @RequestBody DiaryDto dto,
            @AuthenticationPrincipal User currentUser
    ) {
        return diaryService.modifyDiary(dto, currentUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDiary(
            @RequestBody DiaryDto dto,
            @AuthenticationPrincipal User currentUser
    ) {
        return diaryService.deleteDiary(dto, currentUser);
    }
}
