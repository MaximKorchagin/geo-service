package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    @Test
    void byRussianIp() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Country actual = geoService.byIp("172.0.0.1").getCountry();
        Assertions.assertEquals(Country.RUSSIA, actual);
    }

    @Test
    void byAmericanIp() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Country actual = geoService.byIp("96.0.0.1").getCountry();
        Assertions.assertEquals(Country.USA, actual);
    }
    @Test
    void byLocalHost() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Country actual = geoService.byIp("127.0.0.1").getCountry();
        assertNull(actual);
    }
    @Test
    void byNull() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location actual = geoService.byIp("22.22.222.22");
        assertNull(actual);
    }

}