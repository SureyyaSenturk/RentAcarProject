package com.bilgeadam.rentacar.service.impl;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.request.UpdateUserRequest;
import com.bilgeadam.rentacar.entity.User;
import com.bilgeadam.rentacar.mapper.UserMapper;
import com.bilgeadam.rentacar.repository.UserRepository;
import com.bilgeadam.rentacar.response.UserResponse;
import com.bilgeadam.rentacar.service.UserService;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.*;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserServiceImpl(final UserRepository userRepository, final UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public DataResult<List<UserResponse>> getAll() {
    final List<User> users = userRepository.findAll();
    final List<UserResponse> userResponses = userMapper.entityListToResponseList(users);
    return new SuccessDataResult<>(
        userResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public DataResult<UserResponse> getById(String id) {
    final User user = getUserWithId(id);
    final UserResponse userResponse = userMapper.entityToResponse(user);
    return new SuccessDataResult<>(
        userResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result update(String id, UpdateUserRequest updateUserRequest) {
    checkIfEmailAlreadyExists(updateUserRequest.getEmail());
    final User user = getUserWithId(id);
    setUpdatedUserFields(updateUserRequest, user);
    final UserResponse userResponse = userMapper.entityToResponse(user);
    return new SuccessDataResult(
        userResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  private void setUpdatedUserFields(final UpdateUserRequest updateUserRequest, final User user) {
    user.setEmail(updateUserRequest.getEmail());
    user.setPassword(updateUserRequest.getPassword());
  }

  @Override
  public Result delete(String id) {
    final User user = getUserWithId(id);
    user.setState((short) 0);
    this.userRepository.save(user);
    final UserResponse userResponse = userMapper.entityToResponse(user);
    return new SuccessDataResult<>(
        userResponse, BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public boolean checkIfUserEmailAlreadyExists(String email) {
    return this.userRepository.existsUserByEmail(email);
  }

  @Override
  public Result login(final String username, final String password) {
    final User user = getUser(username);
    if (user.getPassword().equals(password)) {
      return new SuccessResult();
    }
    return new ErrorResult("Invalid Username or Password");
  }

  private User getUser(final String username) {
    return userRepository
        .findUserByEmail(username)
        .orElseThrow(() -> new BusinessException("Invalid Username or Password"));
  }

  private User getUserWithId(String id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new BusinessException(BusinessMessages.UserMessages.USER_NOT_FOUND));
  }

  private void checkIfEmailAlreadyExists(String email) {

    if (this.userRepository.existsUserByEmail(email)) {
      throw new BusinessException(BusinessMessages.UserMessages.USER_EMAIL_ALREADY_EXISTS + email);
    }
  }
}
