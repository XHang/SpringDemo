package com.springdata.bean;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 一个简单的报表实体类
 * 统计每天有多少办理量，就酱紫
 */
public class StatisticsVo {
    private Date date;
    private Long count;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public StatisticsVo(Date date, Long count) {
        this.date = date;
        this.count = count;
    }
}
