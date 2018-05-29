package com.example.surya2;

public class CampusInfoForSearch {
    private long campusBuildingId;
    private String campusBuildingName;
    private String buildingNumber;
    private String campusName;
    private long campusId;
    private int campusBuildingRange;
    private long campusBuildingRangeId;
    private String roomNumber;

    public long getCampusBuildingId() {
        return campusBuildingId;
    }

    public void setCampusBuildingId(long campusBuildingId) {
        this.campusBuildingId = campusBuildingId;
    }

    public String getCampusBuildingName() {
        return campusBuildingName;
    }

    public void setCampusBuildingName(String campusBuildingName) {
        this.campusBuildingName = campusBuildingName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public long getCampusId() {
        return campusId;
    }

    public void setCampusId(long campusId) {
        this.campusId = campusId;
    }

    public int getCampusBuildingRange() {
        return campusBuildingRange;
    }

    public void setCampusBuildingRange(int campusBuildingRange) {
        this.campusBuildingRange = campusBuildingRange;
    }

    public long getCampusBuildingRangeId() {
        return campusBuildingRangeId;
    }

    public void setCampusBuildingRangeId(long campusBuildingRangeId) {
        this.campusBuildingRangeId = campusBuildingRangeId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "CampusInfoForSearch{" +
                "campusBuildingId=" + campusBuildingId +
                ", campusBuildingName='" + campusBuildingName + '\'' +
                ", buildingNumber=" + buildingNumber +
                ", campusName='" + campusName + '\'' +
                ", campusId=" + campusId +
                ", campusBuildingRange=" + campusBuildingRange +
                ", campusBuildingRangeId=" + campusBuildingRangeId +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }
}
