package org.but.feec.traintransportmanagement.services;

import org.but.feec.traintransportmanagement.api.TrainBasicView;
import org.but.feec.traintransportmanagement.data.TrainRepository;
import org.but.feec.traintransportmanagement.api.TrainCreateView;
import org.but.feec.traintransportmanagement.api.TrainDetailView;
import org.but.feec.traintransportmanagement.api.TrainEditView;

import java.util.List;

public class TrainService {   //obluzne sluzby, ktere bude volat(pouzivat) controller, aby vytahl data z databaze, bude vracet data z personBasicView a davat je do nejakeho formatu s kterym uz muzu pracovat v JavaFX

    private TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) { this.trainRepository = trainRepository; }

    public TrainService() {   //defaultni konstruktor, neobsahuje nic, musi tu byt
    }

    public TrainDetailView getTrainDetailView(Long id) {
        return trainRepository.findTrainDetailedView(id);
    }





    public void createTrain(TrainCreateView trainCreateView) {
     /*
        char[] originalPassword = personCreateView.getPwd();
        char[] hashedPassword = hashPassword(originalPassword);
        personCreateView.setPwd(hashedPassword);
    */
        trainRepository.createTrain(trainCreateView);

    }


    public void editTrain(TrainEditView trainEditView) {
        trainRepository.editTrain(trainEditView);
    }

    public List<TrainBasicView> getTrainBasicView(String aColumn, String aValue) {  //vezme veci z repozitare a posle je dal do gui
        return trainRepository.getTrainBasicView(aColumn, aValue);
    }



}



