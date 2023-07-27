package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
//Написать тесты для проверки языка отправляемого сообщения (класс MessageSender) с использованием Mockito
//Поверить, что MessageSenderImpl всегда отправляет только русский текст, если ip относится к российскому сегменту адресов.
//Поверить, что MessageSenderImpl всегда отправляет только английский текст, если ip относится к американскому сегменту адресов.


//Написать тесты для проверки определения локации по ip (класс GeoServiceImpl)
//Проверить работу метода public Location byIp(String ip)

//Написать тесты для проверки возвращаемого текста (класс LocalizationServiceImpl)
//Проверить работу метода public String locale(Country country)

class MessageSenderImplTest {

    @Test
    void send() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "testing ip");

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(anyString())).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenCallRealMethod();

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        String sent = messageSender.send(headers);
        Assertions.assertEquals("Добро пожаловать", sent);

    }
    @Test
    void sendAmerica() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "testing ip");

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(anyString())).thenReturn(new Location(null, Country.USA, null, 0));

        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA)).thenCallRealMethod();

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        String sent = messageSender.send(headers);
        Assertions.assertEquals("Welcome", sent);

    }


}