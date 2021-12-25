package org.but.feec.traintransportmanagement.api;

public class TrainEditView {
    private Long trainId;
    private String trainName;
    private String trainSpeed;
    private String trainType;

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainSpeed() {
        return trainSpeed;
    }

    public void setTrainSpeed(String trainSpeed) {
        this.trainSpeed = trainSpeed;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    @Override
    public String toString() {
        return "TrainEditView{" +
                "trainName='" + trainName + '\'' +
                ", trainSpeed='" + trainSpeed + '\'' +
                ", trainType='" + trainType + '\'' +
                '}';
    }

}
