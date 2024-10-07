package com.example.filmpass.service;

import com.example.filmpass.dto.CinemaDto;
import com.example.filmpass.dto.SeatDto;
import com.example.filmpass.entity.Cinema;
import com.example.filmpass.entity.CinemaMovie;
import com.example.filmpass.entity.Seat;
import com.example.filmpass.repository.CinemaMovieRepository;
import com.example.filmpass.repository.CinemaRepository;
import com.example.filmpass.repository.SeatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMovieRepository cinemaMovieRepository;
    private final SeatRepository seatRepository;

    //상영관 등록
    public CinemaDto registerCinema(CinemaDto cinemaDto) {

        Cinema cinema = cinemaDto.toEntity();
        cinemaRepository.save(cinema);
        return new CinemaDto(cinema);
    }

    //상영관 조회
    public List<CinemaDto> read() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        List<CinemaDto> cinemaDtoList = new ArrayList<>();

        for(Cinema cinema : cinemas) {
            CinemaDto cinemaDto = new CinemaDto();
            cinemaDto.setId(cinema.getCinemaId());
            cinemaDto.setCinemaName(cinema.getCinemaName());
            cinemaDto.setSeatRow(cinema.getSeatRow());
            cinemaDto.setSeatCol(cinema.getSeatCol());

            cinemaDtoList.add(cinemaDto);
        }
        return cinemaDtoList;
    }

    // 상영관 배치 - 관리자의 시점, 관리자는 cinemaMovie 테이블을 확인할 수 있다는 가정하에 진행
    public CinemaDto placeCinema(Long cinemaMovieId,Long cinemaId){
        CinemaMovie cinemaMovie = cinemaMovieRepository.findById(cinemaMovieId).orElseThrow(()-> new NoSuchElementException("없는 상영중인 영화입니다"));
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(()-> new NoSuchElementException("없는 상영관입니다"));
        cinemaMovie.addCinema(cinema);
        CinemaDto cinemaDto = new CinemaDto();
        cinemaDto.setId(cinema.getCinemaId());
        cinemaDto.setCinemaName(cinema.getCinemaName());
        cinemaDto.setSeatRow(cinema.getSeatRow());
        cinemaDto.setSeatCol(cinema.getSeatCol());
        return cinemaDto;
    }
}
