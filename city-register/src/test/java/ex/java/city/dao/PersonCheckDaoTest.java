package ex.java.city.dao;

import ex.java.city.damain.PersonRequest;
import ex.java.city.damain.PersonResponse;
import ex.java.city.exception.PersonCheckException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PersonCheckDaoTest {

    @Test
    public void checkPerson() throws PersonCheckException {
        // инициализирую поля, экземпляр PersonRequest для доступа к сеттерам
        PersonRequest personrequest = new PersonRequest();

        personrequest.setSurName("Марш");
        personrequest.setGivenName("Рэнди");
        personrequest.setPatronymic("Марвин");
        personrequest.setDateOfBirth(LocalDate.of(1977, 5,17));
        personrequest.setStreetCode(1);
        personrequest.setBuilding("32");
        personrequest.setExtension("1");
        personrequest.setApartment("103");

        PersonCheckDao personCheckDao = new PersonCheckDao();
        PersonResponse personResponse = personCheckDao.checkPerson(personrequest);
        Assert.assertTrue(personResponse.isRegistered());
        Assert.assertFalse(personResponse.isTemporal());
    }
}