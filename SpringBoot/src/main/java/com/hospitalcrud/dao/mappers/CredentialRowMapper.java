package com.hospitalcrud.dao.mappers;


import com.hospitalcrud.dao.model.Credential;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CredentialRowMapper {

    public List<Credential> mapRow(ResultSet rs) {
        List<Credential> lista = new ArrayList<>();

        try {
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int user_id = rs.getInt("user_id");

                Credential credential = new Credential(username,password,user_id);

                lista.add(credential);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
