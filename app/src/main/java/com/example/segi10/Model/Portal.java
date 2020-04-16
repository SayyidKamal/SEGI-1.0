package com.example.segi10.Model;

public class Portal {
    private String portalId,startDate,lastEarlyDate,lateDate,type;

    public Portal() {
    }

    public Portal(String portalId, String startDate, String lastEarlyDate, String lateDate, String type) {
        this.portalId = portalId;
        this.startDate = startDate;
        this.lastEarlyDate = lastEarlyDate;
        this.lateDate = lateDate;
        this.type = type;
    }

    public String getPortalId() {
        return portalId;
    }

    public void setPortalId(String portalId) {
        this.portalId = portalId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getLastEarlyDate() {
        return lastEarlyDate;
    }

    public void setLastEarlyDate(String lastEarlyDate) {
        this.lastEarlyDate = lastEarlyDate;
    }

    public String getLateDate() {
        return lateDate;
    }

    public void setLateDate(String lateDate) {
        this.lateDate = lateDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
