package org.but.feec.traintransportmanagement.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.but.feec.traintransportmanagement.api.*;
import org.but.feec.traintransportmanagement.config.DataSourceConfig;
import org.but.feec.traintransportmanagement.exceptions.DataAccessException;

public class UserRepository {

    public UserView findUserView(int userId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT u.id AS user_id, surname, email, password" +
                             " FROM public.sql_injection_table1 u" +
                             " WHERE u.id = ?")
        ) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToUserView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find user by ID failed.", e);
        }
        return null;
    }

    private UserView mapToUserView(ResultSet rs) throws SQLException {
        UserView userView = new UserView();
        userView.setUserId(rs.getLong("id"));
        userView.setSurname(rs.getString("surname"));
        userView.setEmail(rs.getString("email"));
        userView.setPassword(rs.getString("password"));
        return userView;
   }
}
