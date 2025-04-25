package com.example.autofarmer.Crop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.autofarmer.Crop.domain.Crop;
import com.example.autofarmer.Crop.service.CropService;

@RestController
@RequestMapping("/crops")
public class CropController {
	private final CropService cropService;

	@Autowired
	public CropController(CropService cropService) {
		this.cropService = cropService;
	}

	@GetMapping("/search")
	public Crop searchCrop(
			@RequestParam("cropName") String cropName
	) {
		return cropService.findByCropName(cropName);
	}
	@GetMapping("/search")
	public List<Crop> sortCropByHarvestDate(
			@RequestParam("categoryNum") int categoryNum
	) {
		return cropService.findByCategoryNumOrderByHarvestDateAsc(categoryNum);
	}
	@GetMapping("/search")
	public List<Crop> filterCropByTempRange(
		@RequestParam("categoryNum") int categoryNum,
		@RequestParam("tempMin") int tempMin,
		@RequestParam("tempMax") int tempMax
	) {
		return cropService.findByCategoryNumAndTempMinGreaterThanEqualAndTempMaxLessThanEqual(categoryNum, tempMin, tempMax);
	}
	@GetMapping("/search")
	public List<Crop> filterCropByHumidityRange(
			@RequestParam("categoryNum") int categoryNum,
			@RequestParam("humidityMin") int humidityMin,
			@RequestParam("humidityMax") int humidityMax
	) {
		return cropService.findByCategoryNumAndTempMinGreaterThanEqualAndTempMaxLessThanEqual(categoryNum, humidityMin, humidityMax);
	}
}
