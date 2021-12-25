package org.but.feec.traintransportmanagement.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.but.feec.traintransportmanagement.api.*;
import org.but.feec.traintransportmanagement.config.DataSourceConfig;
import org.but.feec.traintransportmanagement.exceptions.DataAccessException;

public class PersonRepository {
    public PersonAuthView findPersonByEmail(String email) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT email, password" +
                             " FROM public.user u" +
                             " WHERE u.email = ?")
        ) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonAuth(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find train failed.", e);
        }
        return null;
    }

    private PersonAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        PersonAuthView person = new PersonAuthView();
        person.setEmail(rs.getString("email"));
        person.setPassword(rs.getString("password"));
        return person;
    }
}
