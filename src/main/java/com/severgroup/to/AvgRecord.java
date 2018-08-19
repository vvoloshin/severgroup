package com.severgroup.to;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.Objects;


@XmlRootElement(name = "avgrecord")
@XmlType(propOrder = {"userName", "url", "avgSeconds"})
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "avgrecords")
public class AvgRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    @NotNull
    @Column(name = "id")
    private Long eid;

    @XmlTransient
    @NotNull
    @Column(name = "date_session")
    private LocalDate dateSession;

    @XmlElement(name = "eid")
    @NotNull
    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Column(name = "url")
    private String url;

    @XmlElement(name = "average")
    @NotNull
    @Column(name = "seconds")
    private double avgSeconds;

    public AvgRecord() {
    }

    public AvgRecord(LocalDate dateSession, String userName, String url, double avgSeconds) {
        this.dateSession = dateSession;
        this.userName = userName;
        this.url = url;
        this.avgSeconds = avgSeconds;
    }

    public LocalDate getDateSession() {
        return dateSession;
    }

    public void setDateSession(LocalDate dateSession) {
        this.dateSession = dateSession;
    }

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public LocalDate getDate() {
        return dateSession;
    }

    public void setDate(LocalDate date) {
        this.dateSession = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getAvgSeconds() {
        return avgSeconds;
    }

    public void setAvgSeconds(double avgSeconds) {
        this.avgSeconds = avgSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvgRecord avgRecord = (AvgRecord) o;
        return Double.compare(avgRecord.avgSeconds, avgSeconds) == 0 &&
                Objects.equals(dateSession, avgRecord.dateSession) &&
                Objects.equals(userName, avgRecord.userName) &&
                Objects.equals(url, avgRecord.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateSession, userName, url, avgSeconds);
    }

    @Override
    public String toString() {
        return "AvgRecord{" +
                "date=" + dateSession +
                ", userName='" + userName + '\'' +
                ", url='" + url + '\'' +
                ", avgSeconds=" + avgSeconds +
                '}';
    }
}

