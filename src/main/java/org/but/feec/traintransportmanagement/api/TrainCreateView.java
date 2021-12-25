package org.but.feec.traintransportmanagement.api;

public class TrainCreateView {

    private String trainName;
    private String trainSpeed;
    private String trainType;
    private int depoId;

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

    public void setDepoId(int depoId) {
        this.depoId = depoId;
    }

    public int getDepoId(){
        return depoId;
    }


    @Override
    public String toString() {
        return "TrainCreateView{" +
                "trainName='" + trainName + '\'' +
                ", trainSpeed='" + trainSpeed + '\'' +
                ", trainType='" + trainType + '\'' +
                ", depoId='" + depoId + '\'' +
                '}';

    }

}
