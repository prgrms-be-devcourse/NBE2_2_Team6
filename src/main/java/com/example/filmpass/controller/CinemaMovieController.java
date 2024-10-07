package com.example.filmpass.controller;

import com.example.filmpass.dto.CinemaMovieDto;
import com.example.filmpass.dto.MovieListDto;
import com.example.filmpass.entity.CinemaMovie;
import com.example.filmpass.service.CinemaMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinemaMovie")
@Log4j2
public class CinemaMovieController {
    private final CinemaMovieService cinemaMovieService;

    //상영중인 영화 등록
    @PostMapping()
    public ResponseEntity<List<CinemaMovieDto>> create() {
        return ResponseEntity.ok(cinemaMovieService.registerCinema());
    }

    //상영중인 영화 조회
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieListDto> read(@PathVariable Long movieId) {
        return ResponseEntity.ok(cinemaMovieService.read(movieId));
    }
}
