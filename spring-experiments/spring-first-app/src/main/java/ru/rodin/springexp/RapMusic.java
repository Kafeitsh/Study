package ru.rodin.springexp;

//@Component
//@Scope("prototype")
public class RapMusic implements Music {

    Genre genre = Genre.RAP;

    String[] playlist = {"Eminem - Lose Yourself", "Gangstarr - Full Clip", "Lil Peep - Benz Truck"};

    private RapMusic() {}

    public static RapMusic getRapMusic() {
        return new RapMusic();
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
