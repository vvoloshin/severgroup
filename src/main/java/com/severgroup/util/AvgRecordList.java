package com.severgroup.util;

import com.severgroup.to.AvgRecord;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "avgrecords")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvgRecordList {
    @XmlElement(name = "avgrecord")
    private List<AvgRecord> list;

    public List<AvgRecord> getList() {
        return list;
    }

    public void setList(List<AvgRecord> list) {
        this.list = list;
    }
}
