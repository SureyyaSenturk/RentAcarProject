package com.bilgeadam.rentacar.color;

import com.bilgeadam.rentacar.brand.Brand;
import com.bilgeadam.rentacar.brand.BrandResponse;
import com.bilgeadam.rentacar.brand.CreateBrandRequest;
import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.util.Mapper;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class ColorMapper extends BaseMapper<Color, CreateColorRequest, ColorResponse> {


  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<Brand, BrandResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setName(source.getName());
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateBrandRequest, Brand>() {
          protected void configure() {
            map().setName(source.getName());
          }
        });
  }

  @Override
  public List<ColorResponse> entityListToResponseList(final List<Color> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public Color requestToEntity(final CreateColorRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public ColorResponse entityToResponse(final Color source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
