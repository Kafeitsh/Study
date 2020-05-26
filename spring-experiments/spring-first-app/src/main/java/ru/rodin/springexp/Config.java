package ru.rodin.springexp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;

@Configuration
//@ComponentScan("ru.rodin.springexp")
@PropertySource("classpath:musicPlayer.properties")
public class Config {

    @Bean
    @Scope("prototype")
    public RussianPopMusic russianPopMusic() {
        return RussianPopMusic.getRussianPopMusic();
    }

    @Bean
    @Scope("prototype")
    public ClassicalMusic classicalMusic() {
        return ClassicalMusic.getClassicalMusic();
    }

    @Bean
    @Scope("prototype")
    public RockMusic rockMusic() {
        return RockMusic.getRockMusic();
    }

    @Bean
    @Scope("prototype")
    public RapMusic rapMusic() {
        return RapMusic.getRapMusic();
    }

    @Bean
    @Scope("prototype")
    public PopMusic popMusic() {
        return PopMusic.getPopMusic();
    }

    @Bean
    @Scope("prototype")
    public MusicPlayer musicPlayer() {
        return new MusicPlayer(Arrays.asList(
                classicalMusic(),
                rockMusic(),
                rapMusic(),
                popMusic(),
                russianPopMusic()
        ));
    }

    @Bean
    @Scope("prototype")
    public Computer computer() {
        return new Computer(musicPlayer());
    }
}
