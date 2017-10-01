package model;

import javax.persistence.*;
import java.io.Serializable;

/*
Class of railway station entity. Use for information about train schedule.
 */

@Entity
@Table(name = "STATION")
public class RailWayStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATION_ID")
    private long id;

    @Column(name = "TITLE")
    private String title;

    public RailWayStation(){
    }

    public RailWayStation(String stationName) {
        this.title = stationName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RailWayStation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
