package com.bilgeadam.rentacar.config.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
  public static ModelMapper getModelMapper() {
    return new ModelMapper();
  }
}
