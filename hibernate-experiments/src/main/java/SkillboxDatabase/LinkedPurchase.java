package SkillboxDatabase;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LinkedPurchaseList")
@Data
public class LinkedPurchase implements Serializable {

    @EmbeddedId
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Course course;

    @EmbeddedId
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Student student;

}

