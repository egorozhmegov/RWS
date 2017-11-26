package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User session class.
 */
@Entity
@Table(name = "USER_SESSION")
public class UserSession implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "USER_SESSION_ID")
    private String sessionId;

    @Column(name = "EXPIRED_TIME")
    private LocalDateTime expiredTime;

    public UserSession() {
    }

    public UserSession(String sessionId, LocalDateTime expiredTime) {
        this.sessionId = sessionId;
        this.expiredTime = expiredTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }
}
