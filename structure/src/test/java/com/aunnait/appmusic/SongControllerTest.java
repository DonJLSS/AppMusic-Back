package com.aunnait.appmusic;

import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.dto.SongResponseDTO;

import com.aunnait.appmusic.rest.SongController;
import com.aunnait.appmusic.service.SongService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SongController.class)
public class SongControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //-------------------------------------------------------GET-------------------------------------------------------------

    @Test
    @WithMockUser("ADMIN")
    void shouldReturnSongById() throws Exception {
        SongResponseDTO mockSong = new SongResponseDTO(1, "Title", 240L, "url", "Album", "Artist", null);
        given(songService.findSongById(1)).willReturn(mockSong);

        mockMvc.perform(get("/api/songs/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.duration").value(240L))
                .andExpect(jsonPath("$.albumName").value("Album"))
                .andExpect(jsonPath("$.artistName").value("Artist"));
    }

    @Test
    @WithMockUser("ADMIN")
    void shouldReturnSongByIdError404() throws Exception {
        SongResponseDTO mockResponse = new SongResponseDTO(1,"Title",240L,"url","Album", "Artist",null);
        doThrow(new EntityNotFoundException("Song not found"))
                .when(songService)
                .findSongById(eq(2));

        mockMvc.perform(get("/api/songs/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser("ADMIN")
    void shouldReturnAllSongs() throws Exception {
        SongResponseDTO mockSong1 = new SongResponseDTO(1, "Title", 240L, "url", "Album", "Artist", null);
        SongResponseDTO mockSong2 = new SongResponseDTO(2, "Title 2", 240L, "url 2", "Album 2", "Artist 2", null);

        List<SongResponseDTO> mockSongs = List.of(mockSong1, mockSong2);

        given(songService.findAll()).willReturn(mockSongs);

        mockMvc.perform(get("/api/songs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Title"))
                .andExpect(jsonPath("[0].duration").value(240L))
                .andExpect(jsonPath("[0].songUrl").value("url"))
                .andExpect(jsonPath("$[0].artistName").value("Artist"))
                .andExpect(jsonPath("$[0].albumName").value("Album"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Title 2"))
                .andExpect(jsonPath("[1].duration").value(240L))
                .andExpect(jsonPath("[1].songUrl").value("url 2"))
                .andExpect(jsonPath("$[1].artistName").value("Artist 2"))
                .andExpect(jsonPath("$[1].albumName").value("Album 2"));

    }

    @Test
    @WithMockUser("ADMIN")
    void shouldReturnAllSongsEmpty() throws Exception{
        List<SongResponseDTO> emptySongs = List.of();

        given(songService.findAll()).willReturn(emptySongs);

        mockMvc.perform(get("/api/songs"))
                .andExpect(status().isNoContent());
    }

    //-------------------------------------------------------POST-------------------------------------------------------------

    @Test
    @WithMockUser("ADMIN")
    void shouldCreateSong() throws Exception {
        SongDTO songDTO = new SongDTO(null, "New Song", 250L, "newUrl", "New Album", "New Artist");

        SongDTO createdSongDTO = new SongDTO(null, "New Song", 250L, "newUrl", "New Album", "New Artist");

        given(songService.addSong(songDTO)).willReturn(createdSongDTO);

        mockMvc.perform(post("/api/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(songDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Song"))
                .andExpect(jsonPath("$.artistName").value("New Artist"))
                .andExpect(jsonPath("$.albumName").value("New Album"))
                .andExpect(jsonPath("$.duration").value(250L))
                .andExpect(jsonPath("$.songUrl").value("newUrl"));
    }

    @Test
    @WithMockUser("ADMIN")
    void shouldCreateSongIllegalArgument() throws Exception {
        SongDTO songDTO = new SongDTO(null, "New Song", 0L, "newUrl", "New Album", "New Artist");

        doThrow(new IllegalArgumentException("Illegal Argument Exception"))
                .when(songService).addSong(songDTO);
        mockMvc.perform(post("/api/songs", songDTO))
                .andExpect(status().isInternalServerError());
    }


    //-------------------------------------------------------PUT-------------------------------------------------------------


    @Test
    @WithMockUser("ADMIN")
    void shouldUpdateSongSuccessfully() throws Exception {
        SongDTO songDTO = new SongDTO(1, "Title", 240L, "url", "Album", "Artist");

        given(songService.updateSong(eq(1), any(SongDTO.class))).willReturn(songDTO);

        mockMvc.perform(put("/api/songs/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(songDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.duration").value(240L))
                .andExpect(jsonPath("$.songUrl").value("url"))
                .andExpect(jsonPath("$.albumName").value("Album"))
                .andExpect(jsonPath("$.artistName").value("Artist"));

    }

    @Test
    @WithMockUser("ADMIN")
    void shouldUpdateSongError404() throws Exception {
        SongDTO songDTO = new SongDTO(1, "Title", 240L, "url", "Album", "Artist");
        doThrow(new EntityNotFoundException("Song not found with id " + 2))
                .when(songService)
                .updateSong(eq(2), any(SongDTO.class));
        mockMvc.perform(put("/api/songs/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(songDTO)))
                .andExpect(status().isNotFound());
    }



    //-------------------------------------------------------DELETE-------------------------------------------------------------

    @Test
    void shouldDeleteSongSuccessfully() throws Exception {
        SongDTO songDTO = new SongDTO(1, "New Song", 250L, "newUrl", "New Album", "New Artist");
        songService.addSong(songDTO);
        willDoNothing().given(songService).deleteSong(1);

        mockMvc.perform(delete("/api/songs/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteSongReturn404() throws Exception{
        SongDTO songDTO = new SongDTO(1,"New Song", 250L, "newUrl", "New Album", "New Artist" );
        songService.addSong(songDTO);
        doThrow(new EntityNotFoundException("Song not found"))
                .when(songService).deleteSong(2);
        mockMvc.perform(delete("/api/songs/{id}", 2))
                .andExpect(status().isNotFound());
    }
}
