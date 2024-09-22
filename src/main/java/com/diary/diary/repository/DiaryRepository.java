package com.diary.diary.repository;

import com.diary.auth.model.User;
import com.diary.diary.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, String> {
    Diary findByDateAndUser(Date date, User user);

    List<Diary> findByUser(User currentUser);
}
