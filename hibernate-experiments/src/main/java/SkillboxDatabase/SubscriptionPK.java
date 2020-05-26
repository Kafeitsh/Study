package SkillboxDatabase;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class SubscriptionPK implements Serializable {

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "course_id", nullable = false)
    private Integer courseId;

    @Override
    public boolean equals(Object object) {
        if (object instanceof SubscriptionPK) {
            SubscriptionPK otherId = (SubscriptionPK) object;
            return (this.studentId == otherId.studentId) && (this.courseId == otherId.courseId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return studentId + courseId;
    }
}
