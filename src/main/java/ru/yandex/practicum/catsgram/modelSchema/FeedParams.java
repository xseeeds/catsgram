package ru.yandex.practicum.catsgram.modelSchema;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;

@Value
@Builder
@Jacksonized
public class FeedParams {
    String sort;

    Integer size;

    Collection<String> friendsEmails;
}
