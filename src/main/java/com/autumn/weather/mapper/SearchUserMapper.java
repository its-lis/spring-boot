package com.autumn.weather.mapper;

import com.autumn.weather.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SearchUserMapper {
    User searchUserMapper();

    int truncateUser();
}
