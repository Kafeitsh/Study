package SkillboxDatabase;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Courses")
@Data
@ToString(of = {"id", "name", "type", "teacher", "price"})
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private CourseType type;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    private Teacher teacher;

    @OneToMany(mappedBy = "course")
    private Set<Subscription> students = new HashSet<>();

    @Column(name = "students_count", nullable = false)
    private Integer studentsCount;

    @Column(name = "price")
    private Integer price;

    @Column(name = "price_per_hour")
    private float pricePerHour;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "linkedPurchaseList",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> purchaseListStudents = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return id == course.id &&
                duration == course.duration &&
                studentsCount == course.studentsCount &&
                price == course.price &&
                Float.compare(course.pricePerHour, pricePerHour) == 0 &&
                Objects.equals(name, course.name) &&
                type == course.type &&
                Objects.equals(description, course.description) &&
                Objects.equals(teacher, course.teacher) &&
                Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
