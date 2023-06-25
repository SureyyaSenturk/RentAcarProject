package com.bilgeadam.rentacar.service.impl;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.entity.City;
import com.bilgeadam.rentacar.mapper.CityMapper;
import com.bilgeadam.rentacar.repository.CityRepository;
import com.bilgeadam.rentacar.request.CreateCityRequest;
import com.bilgeadam.rentacar.request.UpdateCityRequest;
import com.bilgeadam.rentacar.response.CityResponse;
import com.bilgeadam.rentacar.service.CityService;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

  private final CityRepository cityRepository;
  private final CityMapper cityMapper;

  public CityServiceImpl(final CityRepository cityRepository, final CityMapper cityMapper) {
    this.cityRepository = cityRepository;
    this.cityMapper = cityMapper;
  }

  @Override
  public DataResult<List<CityResponse>> getAll() {
    final List<City> cities = cityRepository.findAll();
    final List<CityResponse> cityResponses = cityMapper.entityListToResponseList(cities);
    return new SuccessDataResult<>(
        cityResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public Result add(CreateCityRequest createCityRequest) {
    cityDuplicationCheck(createCityRequest.getCode());
    cityNameDuplicationCheck(createCityRequest.getName());
    final City city = cityMapper.requestToEntity(createCityRequest);
    this.cityRepository.save(city);
    return new SuccessDataResult<>(
        createCityRequest, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<CityResponse> getById(String id) {
    final City city = getCityWithId(id);
    final CityResponse cityResponse = cityMapper.entityToResponse(city);
    return new SuccessDataResult<>(
        cityResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result update(String id, UpdateCityRequest updateCityRequest) {
    final City city = getCityWithId(id);
    cityNameDuplicationCheck(city.getName());
    updateCityOperations(city, updateCityRequest);
    final CityResponse cityResponse = cityMapper.entityToResponse(city);
    this.cityRepository.save(city);
    return new SuccessDataResult<>(
        cityResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public Result delete(String id) {
    final City city = getCityWithId(id);
    city.setState((short) 0);
    this.cityRepository.save(city);
    final CityResponse cityResponse = cityMapper.entityToResponse(city);
    return new SuccessDataResult<>(
        cityResponse, BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public boolean cityExistsById(String id) {
    return this.cityRepository.existsById(id);
  }

  @Override
  public City getCityById(String id) {
    return getCityWithId(id);
  }

  private void cityNameDuplicationCheck(String cityName) {
    if (this.cityRepository.existsByName(cityName)) {
      throw new BusinessException(
          BusinessMessages.CityMessages.CITY_NAME_ALREADY_EXISTS + cityName);
    }
  }

  private void cityDuplicationCheck(Integer cityCode) {

    if (this.cityRepository.existsCityByCode(cityCode)) {
      throw new BusinessException(BusinessMessages.CityMessages.CITY_ID_ALREADY_EXISTS + cityCode);
    }
  }

  private City getCityWithId(String id) {
    return cityRepository
        .findById(id)
        .orElseThrow(
            () -> new BusinessException(BusinessMessages.CityMessages.CITY_NOT_FOUND + id));
  }

  private void updateCityOperations(City city, UpdateCityRequest updateCityRequest) {
    city.setName(updateCityRequest.getName());
  }
}
