package ru.yandex.practicum.catsgram.controller;



import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j          //создаём логер
@RequestMapping("/home")
@Validated
public class SimpleController {

    // создаём логер
    //private static final Logger log = (Logger) LoggerFactory.getLogger(SimpleController.class);


    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String homePage(HttpServletRequest httpServletRequest) {
        // устанавливаем уровень важности логов
        //log.setLevel(Level.DEBUG);

        // логируем факт получения запроса
        log.debug("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), httpServletRequest.getQueryString());


        // возвращаем ответ в виде строки
        return "Котограм";
    }


}
