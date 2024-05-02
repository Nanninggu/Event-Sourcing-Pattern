package com.example.Event.sourcing.pattern.mapper;

import com.example.Event.sourcing.pattern.dto.UserEvent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserEventMapper {

    @Insert("INSERT INTO user_event(id, user_id, event_type, event_data, time_stamp) " +
            "VALUES(#{id}, #{user_id}, #{event_type}, #{event_data}, #{time_stamp})")
    void insert(UserEvent userEvent);

    @Select("SELECT id, user_id, event_type, event_data " +
            "FROM user_event " +
            "WHERE user_id = #{userId} ORDER BY time_stamp ASC")
    List<UserEvent> findByUserId(String userId);

}
