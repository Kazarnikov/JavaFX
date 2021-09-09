package com.finance.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainTink {
    private final String user;
    private final long dateTime;
    private final String retailPlace;
    private final List<TinkOff> items = new ArrayList<>();
    private final long operationType = 1;
    private final long cashTotalSum = 0;
    private final long code = 3;
    private final long fiscalDocumentFormatVer = 2;
    private final long taxationType = 1;
    private final LocalDateTime localDateTime;
    private long totalSum;
    private long ecashTotalSum;

    public MainTink(String user, String retailPlace, long dateTime, LocalDateTime localDateTime) {
        this.user = user;
        this.retailPlace = retailPlace;
        this.dateTime = dateTime;
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getUser() {
        return user;
    }

    public long getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(long totalSum) {
        this.totalSum = totalSum;
        this.ecashTotalSum = totalSum;
    }

    public List<TinkOff> getItems() {
        return items;
    }

    public void setItems(TinkOff items) {
        this.items.add(items);
    }

    public long getDateTime() {
        return dateTime;
    }

    public String getRetailPlace() {
        return retailPlace;
    }

    @Override
    public String toString() {
        return "{" + "\"user\": \"" + user + '\"' +
                ", \"operationType\": " + operationType +
                ", \"totalSum\": " + totalSum +
                ", \"cashTotalSum\": " + cashTotalSum +
                ", \"ecashTotalSum\": " + ecashTotalSum +
                ", \"items\": " + items +
                ", \"code\": " + code +
                ", \"fiscalDocumentFormatVer\": " + fiscalDocumentFormatVer +
                ", \"retailPlace\": \"" + retailPlace + '\"' +
                ", \"dateTime\": " + dateTime +
                ", \"taxationType\": " + taxationType +
                ", \"localDateTime\": \"" + localDateTime + '\"' +
                '}';
    }
}
