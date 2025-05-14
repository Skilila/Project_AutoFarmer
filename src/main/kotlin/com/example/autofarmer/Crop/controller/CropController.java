package com.example.autofarmer.Crop.controller;

import com.example.autofarmer.Crop.domain.Crop;
import com.example.autofarmer.Crop.service.CropService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crop")
public class CropController {

  private final CropService cropService;

  @Autowired
  public CropController(CropService cropService) {
    this.cropService = cropService;
  }

  @GetMapping("/searchCrop")
  public Crop searchCrop(@RequestParam String cropName) {
    return cropService.findByCropName(cropName);
  }

  @GetMapping("/searchCategory")
  public List<Crop> searchCategoryOrderByCropNameAsc(@RequestParam int categoryNum) {
    return cropService.findByCategoryNumOrderByCropNameAsc(categoryNum);
  }

  @GetMapping("/searchCategory/temp")
  public List<Crop> filterCropByTempRange(@RequestParam int categoryNum, @RequestParam int tempMin, @RequestParam int tempMax) {
    return cropService.findByCategoryNumAndTempMinGreaterThanEqualAndTempMaxLessThanEqual(categoryNum, tempMin, tempMax);
  }

  @GetMapping("/searchCategory/humidity")
  public List<Crop> filterCropByHumidityRange(@RequestParam int categoryNum, @RequestParam int humidityMin, @RequestParam int humidityMax) {
    return cropService.findByCategoryNumAndTempMinGreaterThanEqualAndTempMaxLessThanEqual(categoryNum, humidityMin, humidityMax);
  }
}
