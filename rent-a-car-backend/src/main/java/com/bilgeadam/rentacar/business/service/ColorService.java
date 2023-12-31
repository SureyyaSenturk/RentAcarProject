package com.bilgeadam.rentacar.business.service;

import com.bilgeadam.rentacar.entity.Color;
import com.bilgeadam.rentacar.business.request.CreateColorRequest;
import com.bilgeadam.rentacar.business.request.UpdateColorRequest;
import com.bilgeadam.rentacar.business.response.ColorResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface ColorService {

  DataResult<List<ColorResponse>> getAll();

  Result add(CreateColorRequest createColorRequest);

  DataResult<ColorResponse> getById(String id);

  Result update(String id, UpdateColorRequest updateColorRequest);

  Result delete(String id);

  Color getColorWithId(String id);
}
