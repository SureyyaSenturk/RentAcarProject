package com.bilgeadam.rentacar.api.controllers;

import com.bilgeadam.rentacar.color.ColorResponse;
import com.bilgeadam.rentacar.color.ColorService;
import com.bilgeadam.rentacar.color.CreateColorRequest;
import com.bilgeadam.rentacar.color.UpdateColorRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/colors")
@CrossOrigin
public class ColorsController {

  private final ColorService colorService;

  public ColorsController(ColorService colorService) {
    this.colorService = colorService;
  }

  @GetMapping("/getAll")
  public DataResult<List<ColorResponse>> getAll() {
    return this.colorService.getAll();
  }

  @PostMapping("/add")
  public Result add(@RequestBody @Valid CreateColorRequest createColorRequest) {
    return this.colorService.add(createColorRequest);
  }

  @GetMapping("/getById")
  public DataResult<ColorResponse> getById(@RequestParam String id) {
    return this.colorService.getById(id);
  }

  @PutMapping("/update")
  public Result update(
      @RequestParam String id, @RequestBody @Valid UpdateColorRequest updateColorRequest) {
    return this.colorService.update(id, updateColorRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(@RequestParam String id) {
    return this.colorService.delete(id);
  }
}
