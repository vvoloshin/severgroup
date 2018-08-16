package com.severgroup.model;


import com.opencsv.bean.CsvBindByName;

import javax.xml.bind.annotation.*;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement(name = "rowrecord")
@XmlType(propOrder = {"timestamp", "id", "url", "seconds"})
@XmlAccessorType(XmlAccessType.FIELD)
public class RowSessionRecord {
    @XmlTransient
    @CsvBindByName(required = false)
    private UUID uuid;

    @CsvBindByName(column = "timestamp")
    private long timestamp;

    @CsvBindByName(column = "id")
    private String id;

    @CsvBindByName(column = "url")
    private String url;

    @CsvBindByName(column = "seconds")
    private long seconds;

    public RowSessionRecord() {
        this.uuid = UUID.randomUUID();
    }

    public RowSessionRecord(long timestamp, String id, String url, long seconds) {
        this();
        this.timestamp = timestamp;
        this.id = id;
        this.url = url;
        this.seconds = seconds;
    }

    public RowSessionRecord(RowSessionRecord rowSessionRecordOriginal) {
        this();
        this.timestamp = rowSessionRecordOriginal.getTimestamp();
        this.id = rowSessionRecordOriginal.getUserId();
        this.url = rowSessionRecordOriginal.getUrl();
        this.seconds = rowSessionRecordOriginal.getSeconds();
    }

    public String getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return id;
    }

    public void setUserId(String userId) {
        this.id = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowSessionRecord rowSessionRecord = (RowSessionRecord) o;
        return timestamp == rowSessionRecord.timestamp &&
                seconds == rowSessionRecord.seconds &&
                Objects.equals(uuid, rowSessionRecord.uuid) &&
                Objects.equals(id, rowSessionRecord.id) &&
                Objects.equals(url, rowSessionRecord.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "RowSessionRecord{" +
                "timestamp=" + timestamp +
                ", id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", seconds for sessions=" + seconds +
                '}';
    }
}
