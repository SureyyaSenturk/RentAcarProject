package com.bilgeadam.rentacar.common.mapper;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseMapper<Entity, Request, Response> extends PropertyMap<Request, Entity> {

  protected abstract void configureEntityToResponse();

  protected abstract void configureRequestToEntity();

  public Response entityToResponse(Entity source) {
    final ModelMapper modelMapper = new ModelMapper();
    if(modelMapper.getTypeMap(getEntityType(),getResponseType())==null){
    modelMapper.addMappings(this);
    configureEntityToResponse();
    }
    return modelMapper.map(source, getResponseType());
  }

  public List<Response> entityListToResponseList(List<Entity> source) {
    final ModelMapper modelMapper = new ModelMapper();
    return source.stream().map(entity -> modelMapper.map(entity, getResponseType())).toList();
  }

  public Entity requestToEntity(Request source) {
    final ModelMapper modelMapper = new ModelMapper();
    if(modelMapper.getTypeMap(getRequestType(),getEntityType())==null){
    modelMapper.addMappings(this);
    configureRequestToEntity();
    }
    return modelMapper.map(source, getEntityType());
  }

  protected Class<Response> getResponseType() {
    ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
    return (Class<Response>) parameterizedType.getActualTypeArguments()[2];
  }

  protected Class<Entity> getEntityType() {
    ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
    return (Class<Entity>) parameterizedType.getActualTypeArguments()[0];
  }

  protected Class<Request> getRequestType() {
    ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
    return (Class<Request>) parameterizedType.getActualTypeArguments()[1];
  }
}
