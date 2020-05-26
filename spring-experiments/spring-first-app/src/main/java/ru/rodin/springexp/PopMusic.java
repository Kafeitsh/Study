package ru.rodin.springexp;

//@Component
//@Scope("prototype")
public class PopMusic implements Music {

    Genre genre = Genre.POP;

    String[] playlist = {"Aqua - Barbie Girl", "Milene Farmer - F*ck Them All", "Dido - Faith"};

    private PopMusic() {}

    public static PopMusic getPopMusic() {
        return new PopMusic();
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
