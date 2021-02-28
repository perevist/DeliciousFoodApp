package com.perevist.DeliciousFoodApp.service;

import com.perevist.DeliciousFoodApp.exception.DeliciousFoodAppException;
import com.perevist.DeliciousFoodApp.exception.Error;
import com.perevist.DeliciousFoodApp.mapper.UserMapper;
import com.perevist.DeliciousFoodApp.repository.UserRepository;
import com.perevist.DeliciousFoodApp.response.UserDtoResponse;
import com.perevist.DeliciousFoodApp.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final static int PAGE_SIZE = 3;

    @Override
    public List<UserDtoResponse> getUsers(int page) {
        return userRepository.findAll(PageRequest.of(page, PAGE_SIZE)).stream()
                .map(UserMapper::mapUserToUserDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DeliciousFoodAppException(Error.USER_DOES_NOT_EXIST);
        }
    }
}
