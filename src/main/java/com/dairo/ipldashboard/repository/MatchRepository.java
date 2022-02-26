package com.dairo.ipldashboard.repository;

import java.util.List;

import com.dairo.ipldashboard.model.Match;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


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

}
