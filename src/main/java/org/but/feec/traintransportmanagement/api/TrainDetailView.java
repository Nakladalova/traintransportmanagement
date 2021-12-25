package org.but.feec.traintransportmanagement.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TrainDetailView {
    private LongProperty trainId = new SimpleLongProperty();
    private StringProperty trainName = new SimpleStringProperty();
    private StringProperty trainSpeed = new SimpleStringProperty();
    private StringProperty trainType = new SimpleStringProperty();
    private StringProperty depoName = new SimpleStringProperty();
    private StringProperty depoSize = new SimpleStringProperty();

    public long getTrainId() {
        return trainId.get();
    }

    public LongProperty trainIdProperty() {
        return trainId;
    }

    public void setTrainId(long trainId) {
        this.trainId.set(trainId);
    }

    public String getTrainName() {
        return trainName.get();
    }

    public StringProperty trainNameProperty() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName.set(trainName);
    }

    public String getTrainSpeed() {
        return trainSpeed.get();
    }

    public StringProperty trainSpeedProperty() {
        return trainSpeed;
    }

    public void setTrainSpeed(String trainSpeed) {
        this.trainSpeed.set(trainSpeed);
    }

    public String getTrainType() {
        return trainType.get();
    }

    public StringProperty trainTypeProperty() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType.set(trainType);
    }

    public String getDepoName() {
        return depoName.get();
    }

    public StringProperty depoNameProperty() {
        return depoName;
    }

    public void setDepoName(String depoName) {
        this.depoName.set(depoName);
    }

    public String getDepoSize() {
        return depoSize.get();
    }

    public StringProperty depoSizeProperty() {
        return depoSize;
    }

    public void setDepoSize(String depoSize) {
        this.depoSize.set(depoSize);
    }
}
