package com.severgroup.model;


import com.opencsv.bean.CsvBindByName;

import java.util.Objects;
import java.util.UUID;

public class CsvRecord {
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

    public CsvRecord() {
        this.uuid = UUID.randomUUID();
    }

    public CsvRecord(long timestamp, String userId, String url, long seconds) {
        this();
        this.timestamp = timestamp;
        this.id = userId;
        this.url = url;
        this.seconds = seconds;
    }

    public CsvRecord(CsvRecord csvRecordOriginal) {
        this();
        this.timestamp = csvRecordOriginal.getTimestamp();
        this.id = csvRecordOriginal.getUserId();
        this.url = csvRecordOriginal.getUrl();
        this.seconds = csvRecordOriginal.getSeconds();
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
        CsvRecord csvRecord = (CsvRecord) o;
        return timestamp == csvRecord.timestamp &&
                seconds == csvRecord.seconds &&
                Objects.equals(uuid, csvRecord.uuid) &&
                Objects.equals(id, csvRecord.id) &&
                Objects.equals(url, csvRecord.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "CsvRecord{" +
                "timestamp=" + timestamp +
                ", id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", seconds for sessions=" + seconds +
                '}';
    }
}
