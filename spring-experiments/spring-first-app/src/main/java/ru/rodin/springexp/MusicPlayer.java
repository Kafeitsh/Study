package ru.rodin.springexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Component
@Scope("prototype")
public class MusicPlayer {

//    @Autowired
    private List<Music> musicList = new ArrayList<>();
    private String name;
    private int volume;
    private Genre genre;
    private List<Genre> genres;

    public MusicPlayer(List<Music> musicList) {
        this.musicList = musicList;
        setRandomGenre();
    }

    public String playMusic() {
        String song = null;
        for (Music music : musicList) {
            if(music.getGenre().equals(genre)) {
                song = music.getSong();
            }
        }
        return song;
    }

    public String getName() {
        return name;
    }

    @Value("${musicPlayer.name}")
    public void setName(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    @Value("${musicPlayer.volume}")
    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        musicList.forEach(music -> {
            builder.append("Playing: ").append(music.getSong()).append("\n");
        });
        return builder.toString();
    }

    public void setRandomGenre () {
        genres = Arrays.asList(Genre.CLASSICAL, Genre.POP, Genre.RAP, Genre.ROCK, Genre.RUSSIAN_POP);
        this.genre = genres.get((int)(Math.random() * genres.size()));
    }

    public String getSelectedGenre() {
        return genre.toString();
    }
}
