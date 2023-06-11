package com.bilgeadam.rentacar.brand;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
  private final BrandRepository brandRepository;
  private final BrandMapper brandMapper;

  public BrandServiceImpl(final BrandRepository brandRepository, final BrandMapper brandMapper) {
    this.brandRepository = brandRepository;
    this.brandMapper = brandMapper;
  }

  @Override
  public DataResult<List<BrandResponse>> getAll() {
    final List<Brand> brands = this.brandRepository.findAll();
    List<BrandResponse> brandResponses = brandMapper.entityListToResponseList(brands);
    return new SuccessDataResult<>(
        brandResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public Result add(CreateBrandRequest createBrandRequest) {
    brandDuplicationCheck(createBrandRequest.getName());
    final Brand brand = brandMapper.requestToEntity(createBrandRequest);
    this.brandRepository.save(brand);
    return new SuccessDataResult<>(
        createBrandRequest, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<BrandResponse> getById(String id) {
    final Brand brand = getBrandById(id);
    BrandResponse brandResponse = brandMapper.entityToResponse(brand);
    return new SuccessDataResult<>(
        brandResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result update(String id, UpdateBrandRequest updateBrandRequest) {
    final Brand brand = getBrandById(id);
    brandDuplicationCheck(updateBrandRequest.getName());
    updateBrandOperations(brand, updateBrandRequest);
    BrandResponse brandResponse = brandMapper.entityToResponse(brand);
    return new SuccessDataResult<>(
        brandResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public Result delete(String id) {
    final Brand brand = getBrandById(id);
    brand.setState((short) 0);
    final BrandResponse brandResponse = brandMapper.entityToResponse(brand);
    this.brandRepository.save(brand);
    return new SuccessDataResult(
        brandResponse, BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public Brand getBrandWithId(final String id) {
    return getBrandById(id);
  }

  private void updateBrandOperations(Brand brand, UpdateBrandRequest updateBrandRequest) {
    brand.setName(updateBrandRequest.getName());
  }

  private void brandDuplicationCheck(String name) {
    if (this.brandRepository.existsByName(name)) {
      throw new BusinessException(BusinessMessages.BrandMessages.BRAND_ALREADY_EXISTS + name);
    }
  }

  private Brand getBrandById(String id) {
    return brandRepository
        .findById(id)
        .orElseThrow(
            () -> new BusinessException(BusinessMessages.BrandMessages.BRAND_NOT_FOUND + id));
  }
}
