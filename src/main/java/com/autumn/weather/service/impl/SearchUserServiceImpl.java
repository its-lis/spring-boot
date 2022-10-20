package com.autumn.weather.service.impl;

import com.autumn.weather.entity.User;
import com.autumn.weather.mapper.SearchUserMapper;
import com.autumn.weather.service.SearchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchUserServiceImpl implements SearchUserService {

    @Autowired
    private SearchUserMapper mapper;

    @Override
    public User searchUser() {
        return mapper.searchUserMapper();
    }

    @Override
    public int truncateTestUser() {
        return mapper.truncateUser();
    }
}
