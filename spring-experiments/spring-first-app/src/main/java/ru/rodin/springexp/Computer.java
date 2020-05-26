package ru.rodin.springexp;

//@Component
//@Scope("prototype")
public class Computer {
    private int id;
    private MusicPlayer musicPlayer;

    public Computer(MusicPlayer musicPlayer) {
        this.id = hashCode();
        this.musicPlayer = musicPlayer;
    }

    @Override
    public String toString() {
        return "Computer " + id +
                "\nSelected Genre: "+ musicPlayer.getSelectedGenre() +
                "\nNow Playing: " + musicPlayer.playMusic() + "\n";
    }
}
