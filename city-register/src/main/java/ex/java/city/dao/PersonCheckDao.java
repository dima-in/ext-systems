package ex.java.city.dao;

import ex.java.city.damain.PersonRequest;
import ex.java.city.damain.PersonResponse;
import ex.java.city.exception.PersonCheckException;

import java.sql.*;
import java.time.LocalDate;

//отправляет данные в виде структуры(student order) и принимает ответ
public class PersonCheckDao {
    private static final String SQL_REQUEST = "SELECT temporal, upper(crp.sur_name) FROM  " +
            "cr_address_person crap " +
            "INNER JOIN cr_person crp ON crp.person_id = crap.person_id " +
            "INNER JOIN cr_address cra ON cra.address_id = crap.address_id " +
            "WHERE " +
            "(CURRENT_DATE >= crap.start_date) and (CURRENT_DATE <= crap.end_date or crap.end_date is null) " +
            "and upper(crp.sur_name) = upper(?) and upper(crp.given_name) = upper(?) " +
            "and upper(crp.patronymic) = upper(?) " +
            "and crp.date_of_birth = ? " +
            "and cra.street_code = ? " +
            "and upper(cra.building) = upper(?) ";


    //метод принимет запрос request возвращает ответ response
    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();

        String sql = SQL_REQUEST;
        // если в запросе есть extension, добавить к запросу строку
        if (request.getExtension() != null){
            sql += "and upper(cra.extension) = upper(?) ";
        } else {
            sql += "and cra.extension is null";
        }
        // если в запросе есть apartment, добавить к запросу строку
        if (request.getApartment() != null) {
            sql += "and upper(cra.apartment) = upper(?) ";
        } else {
            sql += "and cra.apartment is null";
        }

        //создать простое соединение getConnection
        try(Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            // данные для проверки задаются в PersonCheckDaoTest
            int count = 1;
            stmt.setString(count++, request.getSurName());
            stmt.setString(count++, request.getGivenName());
            stmt.setString(count++, request.getPatronymic());
            stmt.setDate(count++, java.sql.Date.valueOf(request.getDateOfBirth()));
            stmt.setInt(count++, request.getStreetCode());
            stmt.setString(count++, request.getBuilding());
            if (request.getExtension() != null) {
                stmt.setString(count++, request.getExtension());
            }
            if (request.getApartment() != null) {
                stmt.setString(count++, request.getApartment());
            }

            ResultSet rs = stmt.executeQuery();
            //если есть ответ на запрос, значит
            if (rs.next()){
                //человек есть
                response.setRegistered(true);
                //извлекает из указанной колонки значение boolean
                response.setTemporal(rs.getBoolean("temporal"));
            }

        }catch (SQLException e) {
            throw new PersonCheckException(e);
        }

        return response;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost/city_register",
                "postgres","postgres");
    }
}
