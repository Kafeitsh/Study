package SkillboxDatabase;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Teachers")
@Data
@ToString(of = {"name", "age"})
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses = new HashSet<>();

    @Column(name = "salary")
    private int salary;

    @Column(name = "age")
    private int age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id &&
                salary == teacher.salary &&
                age == teacher.age &&
                Objects.equals(name, teacher.name) &&
                Objects.equals(courses, teacher.courses);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
