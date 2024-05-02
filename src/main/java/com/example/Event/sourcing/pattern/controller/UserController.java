package com.example.Event.sourcing.pattern.controller;

import com.example.Event.sourcing.pattern.dto.User;
import com.example.Event.sourcing.pattern.dto.UserEvent;
import com.example.Event.sourcing.pattern.mapper.UserEventMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserEventMapper userEventMapper;
    LocalTime currentTime = LocalTime.now();

    public UserController(UserEventMapper userEventMapper) {
        this.userEventMapper = userEventMapper;
    }

    /**
     * Request body: localhost:8080/users/ea64c1a2-746a-4a2a-8b80-9680c8a159d9
     *
     * @param user
     * @throws JsonProcessingException
     */
    @PostMapping
    public void createUser(@RequestBody User user) throws JsonProcessingException {
        UserEvent userEvent = new UserEvent();
        userEvent.setId(UUID.randomUUID().toString());
        userEvent.setUser_id(user.getId());
        userEvent.setEvent_type("USER_CREATED");
        userEvent.setEvent_data(new ObjectMapper().writeValueAsString(user));
        userEvent.setTime_stamp(currentTime.toString());
        userEventMapper.insert(userEvent);
    }

    /**
     * @param id   = UUID.randomUUID().toString()
     * @param user
     * @throws JsonProcessingException
     */
    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestBody User user) throws JsonProcessingException {
        UserEvent userEvent = new UserEvent();
        userEvent.setId(UUID.randomUUID().toString());
        userEvent.setUser_id(id);
        userEvent.setEvent_type("USER_UPDATED");
        userEvent.setEvent_data(new ObjectMapper().writeValueAsString(user));
        userEvent.setTime_stamp(String.valueOf(System.currentTimeMillis()));
        userEventMapper.insert(userEvent);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) throws JsonProcessingException {
        List<UserEvent> userEvents = userEventMapper.findByUserId(userId);
        System.out.println(userEvents);
        User user = new User();
        for (UserEvent userEvent : userEvents) {
            switch (userEvent.getEvent_type()) {
                case "USER_CREATED":
                case "USER_UPDATED":
                    User eventData = new ObjectMapper().readValue(userEvent.getEvent_data(), User.class);
                    user.setId(eventData.getId());
                    user.setName(eventData.getName());
                    user.setEmail(eventData.getEmail());
                    break;
            }
        }
        return user;
    }
}