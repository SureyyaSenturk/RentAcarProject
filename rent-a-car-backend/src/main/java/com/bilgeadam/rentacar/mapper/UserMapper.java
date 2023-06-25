package com.bilgeadam.rentacar.mapper;

import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.request.UpdateUserRequest;

import java.util.List;

import com.bilgeadam.rentacar.entity.User;
import com.bilgeadam.rentacar.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseMapper<User, UpdateUserRequest, UserResponse> {

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<User, UserResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setEmail(source.getEmail());
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<UpdateUserRequest, User>() {
          protected void configure() {

          }
        });
  }

  @Override
  public List<UserResponse> entityListToResponseList(final List<User> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public User requestToEntity(final UpdateUserRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public UserResponse entityToResponse(final User source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
