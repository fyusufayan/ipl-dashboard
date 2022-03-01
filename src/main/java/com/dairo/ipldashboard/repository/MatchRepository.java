package com.dairo.ipldashboard.repository;

import java.time.LocalDate;
import java.util.List;

import com.dairo.ipldashboard.model.Match;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface MatchRepository extends CrudRepository<Match,Long> {
    
    //Pageable: we can specify how many of this data we want
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

    //önce bunu controller içinden çağırdık
    //ama pageable bir data component'ı
    //o yüzden repository'nin içine aldık
    //it became more loosely coupled
    default List<Match> findLatestMatchesByTeam(String teamName, int count){
        return getByTeam1OrTeam2OrderByDateDesc(teamName,teamName,PageRequest.of(0, count));
    }




    // List<Match> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
    //     String team1,LocalDate date1, LocalDate date2, 
    //     String team2, LocalDate date3, LocalDate date4);

    @Query("select m from Match m where (m.team1=:teamName or m.team2=:teamName) and m.date between :dateStart and :dateEnd order by date desc")
    List<Match> getMatchesByTeamBetweenDates(
        @Param("teamName") String teamName,
        @Param("dateStart") LocalDate dateStart,
        @Param("dateEnd") LocalDate endDate);

}
