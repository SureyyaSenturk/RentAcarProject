package com.bilgeadam.rentacar.service.impl;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.entity.Color;
import com.bilgeadam.rentacar.mapper.ColorMapper;
import com.bilgeadam.rentacar.repository.ColorRepository;
import com.bilgeadam.rentacar.request.CreateColorRequest;
import com.bilgeadam.rentacar.request.UpdateColorRequest;
import com.bilgeadam.rentacar.response.ColorResponse;
import com.bilgeadam.rentacar.service.ColorService;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl implements ColorService {
  private final ColorRepository colorRepository;
  private final ColorMapper colorMapper;

  public ColorServiceImpl(final ColorRepository colorRepository, final ColorMapper colorMapper) {
    this.colorRepository = colorRepository;
    this.colorMapper = colorMapper;
  }

  @Override
  public DataResult<List<ColorResponse>> getAll(){
    final List<Color> colors = colorRepository.findAll();
    final List<ColorResponse> colorResponses = colorMapper.entityListToResponseList(colors);
    return new SuccessDataResult<>(
        colorResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public Result add(CreateColorRequest createColorRequest){
    final Color color = colorMapper.requestToEntity(createColorRequest);
    colorDuplicationCheck(color.getName());
    this.colorRepository.save(color);
    return new SuccessDataResult(
        createColorRequest, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<ColorResponse> getById(String id){
    final Color color = getColorById(id);
    final ColorResponse colorResponse = colorMapper.entityToResponse(color);
    return new SuccessDataResult<>(
        colorResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result update(String id, UpdateColorRequest updateColorRequest){
    final Color color = getColorById(id);
    colorDuplicationCheck(updateColorRequest.getName());
    updateColorOperations(color, updateColorRequest);
    final ColorResponse colorResponse = colorMapper.entityToResponse(color);
    this.colorRepository.save(color);
    return new SuccessDataResult(
        colorResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public Result delete(String id){
    final Color color = getColorById(id);
    final ColorResponse colorResponse = colorMapper.entityToResponse(color);
    color.setState((short) 0);
    this.colorRepository.deleteById(id);
    return new SuccessDataResult(
        colorResponse, BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public Color getColorWithId(final String id) {
    return getColorById(id);
  }

  private void updateColorOperations(Color color, UpdateColorRequest updateColorRequest) {
    color.setName(updateColorRequest.getName());
  }

  private void colorDuplicationCheck(String name){
    if (this.colorRepository.existsByName(name)) {
      throw new BusinessException(BusinessMessages.ColorMessages.COLOR_ALREADY_EXISTS + name);
    }
  }
  private Color getColorById(String id){
    return colorRepository
        .findById(id)
        .orElseThrow(
            () -> new BusinessException(BusinessMessages.ColorMessages.COLOR_NOT_FOUND + id));
  }
}
