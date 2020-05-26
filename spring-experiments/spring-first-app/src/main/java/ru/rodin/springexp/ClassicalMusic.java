package ru.rodin.springexp;

//@Component
//@Scope("prototype")
public class ClassicalMusic implements Music {

    Genre genre = Genre.CLASSICAL;

    String[] playlist = {"Hungarian Rhapsody", "Symphony №5", "Adagio sostenuto"};

    private ClassicalMusic() {}

    public static ClassicalMusic getClassicalMusic() {
        return new ClassicalMusic();
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
