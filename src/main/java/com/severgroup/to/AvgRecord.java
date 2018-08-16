package com.severgroup.to;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.Objects;

@XmlRootElement(name = "avgrecord")
@XmlType(propOrder = {"userName", "url", "avgSeconds"})
@XmlAccessorType(XmlAccessType.FIELD)
public class AvgRecord {
    @XmlTransient
    private LocalDate date;
    @XmlElement(name = "id")
    private String userName;
    private String url;
    @XmlElement(name = "average")
    private double avgSeconds;

    public AvgRecord() {
    }

    public AvgRecord(LocalDate date, String userName, String url, double avgSeconds) {
        this.date = date;
        this.userName = userName;
        this.url = url;
        this.avgSeconds = avgSeconds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
                Objects.equals(date, avgRecord.date) &&
                Objects.equals(userName, avgRecord.userName) &&
                Objects.equals(url, avgRecord.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, userName, url, avgSeconds);
    }

    @Override
    public String toString() {
        return "AvgRecord{" +
                "date=" + date +
                ", userName='" + userName + '\'' +
                ", url='" + url + '\'' +
                ", avgSeconds=" + avgSeconds +
                '}';
    }
}

