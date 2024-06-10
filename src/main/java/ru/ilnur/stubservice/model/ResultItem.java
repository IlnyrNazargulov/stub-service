package ru.ilnur.stubservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@RequiredArgsConstructor
public class ResultItem {
    @JsonProperty("ID")
    private final Integer id;
    @JsonProperty("Cтатус")
    private final String status;
    @JsonProperty("ТекстОшибки")
    private final String error;


    private static Random random = new Random(System.currentTimeMillis());
    private static List<String> statuses = Arrays.asList("Ошибка", "Успешно");

    public static ResultItem getRandom(String id) {
        String status = statuses.get(random.nextInt(statuses.size()));
        String errorMessage = null;
        if (status.equals("Ошибка")) {
            errorMessage = "Текст ошибки " + System.currentTimeMillis();
        }
        return new ResultItem(Integer.valueOf(id), status, errorMessage);
    }
}
