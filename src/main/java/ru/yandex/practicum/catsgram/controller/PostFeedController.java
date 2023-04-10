package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.modelSchema.FeedParams;
import ru.yandex.practicum.catsgram.modelSchema.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.Collection;

import static ru.yandex.practicum.catsgram.constant.Constant.SORTS;


@RestController
@Validated
public class PostFeedController {

    ObjectMapper objectMapper;
    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService) {
        this.objectMapper =  new ObjectMapper();
        this.postService = postService;
    }

    @PostMapping("/feed-posts/friends")
    Collection<Post> getFriendsFeed(@RequestBody String  params){

        FeedParams feedParams;

        try {
            String paramsFromString = objectMapper.readValue(params, String.class);

            feedParams = objectMapper.readValue(paramsFromString, FeedParams.class);

        } catch (JsonProcessingException e) {

            throw new RuntimeException("невалидный формат json", e);
        }

        if(feedParams != null){

            if (!SORTS.contains(feedParams.getSort())) {
                throw new IncorrectParameterException("sort");
            }
            if (feedParams.getFriendsEmails().isEmpty()) {
                throw new IncorrectParameterException("friendsEmails");
            }
            if (feedParams.getSize() == null || feedParams.getSize() <= 0) {
                throw new IncorrectParameterException("size");
            }



            Collection<Post> result = new ArrayList<>();

            for (String friend : feedParams.getFriendsEmails()) {
                result
                        .addAll(postService
                                .findAllPostsByUserEmail(friend, feedParams.getSize(), feedParams.getSort()));
            }

            return result;


        } else {

            throw new RuntimeException("неверно заполнены параметры");
        }
    }
}