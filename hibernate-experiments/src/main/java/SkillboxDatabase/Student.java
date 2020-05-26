package SkillboxDatabase;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Students")
@Data
@ToString(of = {"id", "name", "age"})
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_date")
    private Date registrationDate;

    @OneToMany(mappedBy = "student")
    private Set<Subscription> courses = new HashSet<>();

    @ManyToMany(mappedBy = "purchaseListStudents", fetch = FetchType.LAZY)
    private Set<Course> purchaseListCourses = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
