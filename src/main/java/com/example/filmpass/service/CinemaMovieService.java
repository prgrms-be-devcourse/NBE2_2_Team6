package com.example.filmpass.service;

import com.example.filmpass.dto.CinemaMovieDto;
import com.example.filmpass.dto.MovieListDto;
import com.example.filmpass.entity.Cinema;
import com.example.filmpass.entity.CinemaMovie;
import com.example.filmpass.entity.Movie;
import com.example.filmpass.repository.CinemaMovieRepository;
import com.example.filmpass.repository.CinemaRepository;
import com.example.filmpass.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CinemaMovieService {
    private final CinemaMovieRepository cinemaMovieRepository;
    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;

    public List<CinemaMovieDto> registerCinema() {
        List<Movie> movies = movieRepository.findAll();
        List<CinemaMovieDto> moviesDto = new ArrayList<>();
        for (Movie movie : movies) {
            int allminutes  = Integer.parseInt(movie.getShowTm());
            int hour = allminutes/60;
            int min = allminutes%60;
            LocalTime showtime = LocalTime.of(hour, min);
            if(cinemaMovieRepository.findAll().isEmpty()){
                for (int i = 0; i < 7; i++) {
                    CinemaMovie cinemaMovie = CinemaMovie.builder()
                            .movie(movie) // movie 객체 설정
                            .screenDate(LocalDate.from(LocalDateTime.now().plusDays(i))) // 오늘부터 i일 더한 날짜로 설정
                            .screenTime(showtime) // showtime은 상영 시간으로 설정
                            .build();
                    cinemaMovieRepository.save(cinemaMovie); // cinemaMovie 저장
                    CinemaMovieDto cinemaMovieDto = new CinemaMovieDto(
                            cinemaMovie.getCinemaMovieId(),
                            cinemaMovie.getMovie(),
                            cinemaMovie.getScreenDate(),
                            cinemaMovie.getScreenTime(),
                            cinemaMovie.getMovie().getMovieName());
                    moviesDto.add(cinemaMovieDto);
                }
            }

        }
        return moviesDto;
    }


    //상영중인 영화 상영정보 조회
    public MovieListDto read(Long movieId) {
        List<CinemaMovie> cinemaMovieList = cinemaMovieRepository.findByMovie_MovieId(movieId);

        List<CinemaMovieDto> infoDto = new ArrayList<>();
        for(CinemaMovie cinemaMovie : cinemaMovieList) {
            infoDto.add(new CinemaMovieDto(movieId, cinemaMovie));
        }

        if(cinemaMovieList.isEmpty()) {
            throw new IllegalStateException("상영정보가 없습니다");
        }
        CinemaMovie movieName = cinemaMovieList.get(0);

        return new MovieListDto(movieId, movieName, infoDto);
    }

}
