package com.hospitalcrud.dao.mappers;

import com.hospitalcrud.dao.model.Payment;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentRowMapper {
    public List<Payment> mapRow(ResultSet rs) {
        List<Payment> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                lista.add(new Payment(0, rs.getInt("patient_id"),
                        rs.getInt("amount"), null));
            }
            return lista;
        } catch (Exception e) {
            throw new RuntimeException("Error",e);
        }
    }
}
