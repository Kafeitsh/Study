package ru.rodin.springexp;

//@Component
//@Scope("prototype")
public class RussianPopMusic implements Music{

    Genre genre = Genre.RUSSIAN_POP;

    String[] playlist = {"Loboda - Tvoi Glaza", "Leningrad - Eksponat", "Tima Belorusskih - Nezabudka"};

    private RussianPopMusic() {
    }

    public static RussianPopMusic getRussianPopMusic() {
        return new RussianPopMusic();
    }

    @Override
    public String getSong() {
        return playlist[(int)((Math.random() * playlist.length))];
    }

    @Override
    public Genre getGenre() {
        return genre;
    }
}
