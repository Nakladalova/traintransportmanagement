package org.but.feec.traintransportmanagement.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TrainBasicView {

    private LongProperty id = new SimpleLongProperty();
    private StringProperty trainName = new SimpleStringProperty();
    private StringProperty speed = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();


    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
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

    public String getSpeed() {
        return speed.get();
    }

    public StringProperty speedProperty() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed.set(speed);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
