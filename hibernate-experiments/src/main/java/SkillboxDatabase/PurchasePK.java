package SkillboxDatabase;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class PurchasePK implements Serializable {

    @ManyToOne(targetEntity = Purchase.class)
    @JoinColumn(name = "course_name", nullable = false)
    private String courseName;

    @ManyToOne(targetEntity = Purchase.class)
    @JoinColumn(name = "student_name", nullable = false)
    private String studentName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchasePK)) return false;
        PurchasePK that = (PurchasePK) o;
        return courseName.equals(that.courseName) &&
                studentName.equals(that.studentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, studentName);
    }
}
