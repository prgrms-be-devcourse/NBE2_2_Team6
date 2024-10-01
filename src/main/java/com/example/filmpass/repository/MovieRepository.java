package com.example.filmpass.repository;

import com.example.filmpass.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsByMovieCd(String movieCd);

    Movie findByMovieCd(String movieCd);

    Movie findByMovieName(String movieName);

    // 줄거리 및 포스터 URL 업데이트를 위한 메소드
    @Transactional
    @Modifying
    @Query("UPDATE Movie m SET m.plot = :plot, m.poster = :posterUrl WHERE m.movieCd = :movieCd")
    void updateMoviePlotAndPoster(String movieCd, String plot, String posterUrl);
}
