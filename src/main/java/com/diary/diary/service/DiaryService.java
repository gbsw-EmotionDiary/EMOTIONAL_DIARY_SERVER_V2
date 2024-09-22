package com.diary.diary.service;

import com.diary.auth.model.User;
import com.diary.diary.dto.DiaryDto;
import com.diary.diary.model.Diary;
import com.diary.diary.repository.DiaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public ResponseEntity<?> writeDiary(DiaryDto dto, User currentUser) {
        Diary diary = new Diary(
                null,
                dto.getDate(),
                dto.getTitle(),
                dto.getContent(),
                dto.getEmotion(),
                currentUser
        );
        diaryRepository.save(diary);

        return ResponseEntity.ok().body("일기가 등록되었습니다.");
    }

    public ResponseEntity<?> modifyDiary(DiaryDto dto, User currentUser) {
        Diary diary = diaryRepository.findByDateAndUser(dto.getDate(), currentUser);

        if (diary == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일기를 찾을 수 없습니다");
        }
        else {
            diary.setTitle(dto.getTitle());
            diary.setContent(dto.getContent());
            diary.setEmotion(dto.getEmotion());
            diaryRepository.save(diary);

            return ResponseEntity.ok().body("일기를 성공적으로 수정했습니다.");
        }
    }

    public ResponseEntity<?> deleteDiary(DiaryDto dto, User currentUser) {
        Diary diary = diaryRepository.findByDateAndUser(dto.getDate(), currentUser);

        if (diary == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일기를 찾을 수 없습니다");
        }
        else {
            diaryRepository.delete(diary);

            return ResponseEntity.ok().body("일기를 성공적으로 삭제했습니다.");
        }
    }

    public List<Diary> getDiary(User currentUser) {
        return diaryRepository.findByUser(currentUser);
    }
}
