package com.aunnait.appmusic.service;


import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.dto.SongResponseDTO;
import com.aunnait.appmusic.utils.GenreOperations;
import com.aunnait.appmusic.utils.SongOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.Duration;
import java.util.List;

public interface ISongService extends SongOperations, GenreOperations {
    List<SongResponseDTO> findAll();
    SongResponseDTO findSongById(Integer id);
    SongDTO updateSong(Integer id, SongDTO songDTO);
    SongDTO addSong(SongDTO songDTO);
    void deleteSong(Integer id);
    List<SongDTO> findAllAlbumByAttributes(String title, Duration duration,String songUrl);
    Page<SongDTO> findAllPaginated(Pageable pageable);

}
