package com.aunnait.appmusic;

import com.aunnait.appmusic.model.dto.SongResponseDTO;

import com.aunnait.appmusic.rest.SongController;
import com.aunnait.appmusic.service.SongService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SongController.class)
public class SongControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;



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

//    @Test
//    void shouldReturnSongByIdError404() throws Exception {
//        SongResponseDTO mockResponse = new SongResponseDTO(1,"Title",240L,"url","Album", "Artist",null);
//        given(songService.findSongById(2)).willReturn(mockResponse);
//
//        mvc.perform(get("/api/songs/{id}", 1))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//    //-------------------------------------------------------POST-------------------------------------------------------------
//
//    @Test
//    void shouldCreateSong() throws Exception {
//        SongDTO songDTO = new SongDTO(null, "New Song", 250L, "newUrl", "New Album", "New Artist");
//
//        SongDTO createdSongDTO = new SongDTO(null, "New Song", 250L, "newUrl", "New Album", "New Artist");
//
//        given(songService.addSong(songDTO)).willReturn(createdSongDTO);
//
//        mvc.perform(post("/api/songs")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(songDTO)))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.title").value("New Song"))
//                .andExpect(jsonPath("$.artistName").value("New Artist"))
//                .andExpect(jsonPath("$.albumName").value("New Album"))
//                .andExpect(jsonPath("$.duration").value(250L))
//                .andExpect(jsonPath("$.songUrl").value("newUrl"));
//    }
//
//
//    //-------------------------------------------------------PUT-------------------------------------------------------------
//
//
//
//
//
//    //-------------------------------------------------------DELETE-------------------------------------------------------------
//
//    @Test
//    void shouldDeleteSongSuccessfully() throws Exception {
//        SongDTO songDTO = new SongDTO(1, "New Song", 250L, "newUrl", "New Album", "New Artist");
//        songService.addSong(songDTO);
//        willDoNothing().given(songService).deleteSong(1);
//
//        mvc.perform(delete("/api/songs/{id}", 1))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
}
