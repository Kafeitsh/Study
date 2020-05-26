package SkillboxDatabase;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Subscriptions")
@Data
public class Subscription {

    @EmbeddedId
    private SubscriptionPK subscriptionPK;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="course_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Course course;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="student_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Student student;

    public String getSubscriptionDate() {
        return (new SimpleDateFormat("dd.MM.YYYY").format(subscriptionDate));
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "student=" + student.getName() +
                ", course=" + course.getName() +
                ", subscriptionDate=" + getSubscriptionDate() +
                '}';
    }
}

