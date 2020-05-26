package ru.rodin.springexp;

//@Component
//@Scope("prototype")
public class RockMusic implements Music {

    Genre genre = Genre.ROCK;

    String[] playlist = {"Pain - Shut Your Mouth", "Sex Pistols - Anarchy In The UK", "Iron Maiden - Aces High"};

    private RockMusic() {}

    public static RockMusic getRockMusic() {
        return new RockMusic();
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
