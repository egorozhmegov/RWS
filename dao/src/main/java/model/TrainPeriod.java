package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class of train periods. Train store days, when it arrival or departure on stations.
 */
@Entity
@Table(name = "PERIOD")
public class TrainPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERIOD_ID")
    private long id;

    @Enumerated(EnumType.STRING)
    private WeekDays weekDays;

    @ManyToMany(mappedBy = "period")
    private Set<Train> train = new HashSet<>();

    public TrainPeriod(){
    }

    public TrainPeriod(WeekDays weekDays) {
        this.weekDays = weekDays;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WeekDays getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(WeekDays weekDays) {
        this.weekDays = weekDays;
    }

    public Set<Train> getTrain() {
        return train;
    }

    public void setTrain(Set<Train> train) {
        this.train = train;
    }
}
