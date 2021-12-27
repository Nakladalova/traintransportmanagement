package org.but.feec.traintransportmanagement.data;

import org.but.feec.traintransportmanagement.api.*;
import org.but.feec.traintransportmanagement.api.TrainBasicView;
import org.but.feec.traintransportmanagement.api.TrainCreateView;
import org.but.feec.traintransportmanagement.api.TrainDetailView;
import org.but.feec.traintransportmanagement.config.DataSourceConfig;
import org.but.feec.traintransportmanagement.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainRepository {

    public List<TrainBasicView> getTrainBasicView(String aColumn, String aValue) {
        String sqltext = "SELECT id, train_name, speed, type FROM public.train ";
        if (!aColumn.equals("None")) {
            sqltext += " WHERE ";
            sqltext += aColumn;
            sqltext += " = ?";
        }
        sqltext += " ORDER BY id ASC";

        try (Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqltext)){
            if (!aColumn.equals("None")) {
                preparedStatement.setString(1, aValue);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TrainBasicView> trainBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                trainBasicViews.add(mapToTrainBasicView(resultSet));
            }
            return trainBasicViews;

        } catch (SQLException e) {
            throw new DataAccessException("Train basic view could not be loaded", e);
        }
    }
    public TrainDetailView findTrainDetailedView(Long trainId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT t.id AS train_id, train_name, speed, t.type AS train_type, depo_name, depo_size" +
                             " FROM public.train t" +
                             " JOIN public.depo d ON d.id=t.depo_id" +
                             " WHERE t.id = ?")
        ) {
            preparedStatement.setLong(1, trainId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToTrainDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    /*public boolean findEngineDriver(String email) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT e.id " +
                             " FROM public.engine_driver e" +
                             " JOIN public.depo d ON d.id=t.depo_id" +
                             " WHERE e.email=?")
        ) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find engine driver by email failed failed.", e);
        }
        return false;
    }*/
    public void editTrain(TrainEditView trainEditView) {
        String insertTrainSQL = "UPDATE public.train t SET train_name = ?, speed = ?, type = ? WHERE t.id = ?";
        String checkIfExists = "SELECT train_name FROM public.train t WHERE t.id = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertTrainSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, trainEditView.getTrainName());
            preparedStatement.setString(2, trainEditView.getTrainSpeed());
            preparedStatement.setString(3, trainEditView.getTrainType());
            preparedStatement.setLong(4, trainEditView.getTrainId());

            try {
                connection.setAutoCommit(false);
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, trainEditView.getTrainId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This train for edit do not exists.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating train failed, no rows affected.");
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating train failed operation on the database failed.");
        }
    }

    public void createTrain(TrainCreateView trainCreateView) {
        String insertTrainSQL = "INSERT INTO public.train (train_name, speed, station_id, depo_id, type) VALUES (?,?,?,?,?)";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertTrainSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, trainCreateView.getTrainName());
            preparedStatement.setString(2, trainCreateView.getTrainSpeed());
            preparedStatement.setInt(3, trainCreateView.getDepoId());
            preparedStatement.setInt(4, trainCreateView.getDepoId());
            preparedStatement.setString(5, trainCreateView.getTrainType());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating train failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating train failed operation on the database failed.");
        }
    }

    private TrainBasicView mapToTrainBasicView(ResultSet rs) throws SQLException {  //jednotlive properties namapujeme na zakladni objekty, vezme radek z result setu a aplni ho do trainbasicview, ktery dame do listu
        TrainBasicView trainBasicView = new TrainBasicView();
        trainBasicView.setId(rs.getLong("id"));
        trainBasicView.setTrainName(rs.getString("train_name"));
        trainBasicView.setSpeed(rs.getString("speed"));
        trainBasicView.setType(rs.getString("type"));
        return trainBasicView;
    }

    private TrainDetailView mapToTrainDetailView(ResultSet rs) throws SQLException {
        TrainDetailView trainDetailView = new TrainDetailView();
        trainDetailView.setTrainId(rs.getLong("train_id"));
        trainDetailView.setTrainName(rs.getString("train_name"));
        trainDetailView.setTrainSpeed(rs.getString("speed"));
        trainDetailView.setTrainType(rs.getString("train_type"));
        trainDetailView.setDepoName(rs.getString("depo_name"));
        trainDetailView.setDepoSize(rs.getString("depo_size"));
        return trainDetailView;
    }


}

