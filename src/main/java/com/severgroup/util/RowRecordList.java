package com.severgroup.util;

import com.severgroup.model.RowSessionRecord;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "rowrecords")
@XmlAccessorType(XmlAccessType.FIELD)
public class RowRecordList {
    @XmlElement(name = "rowrecord")
    private List<RowSessionRecord> list;

    public List<RowSessionRecord> getList() {
        return list;
    }

    public void setList(List<RowSessionRecord> list) {
        this.list = list;
    }
}
