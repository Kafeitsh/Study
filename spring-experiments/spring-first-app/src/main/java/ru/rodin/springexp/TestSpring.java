package ru.rodin.springexp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * программа, имитирующая работу музыкального плеера, написаная по образу видеокурса Наиля Алишева на Youtube
 *
 * https://www.youtube.com/playlist?list=PLAma_mKffTOR5o0WNHnY0mTjKxnCgSXrZ
 */
public class TestSpring {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        Computer computer1 = context.getBean("computer", Computer.class);
        System.out.println(computer1.toString());

        Computer computer2 = context.getBean("computer", Computer.class);
        System.out.println(computer2.toString());

        Computer computer3 = context.getBean("computer", Computer.class);
        System.out.println(computer3.toString());

        Computer computer4 = context.getBean("computer", Computer.class);
        System.out.println(computer4.toString());

        context.close();
    }
}
