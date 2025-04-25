package com.example.autofarmer.Crop.service;

import com.example.autofarmer.Crop.domain.Crop;
import com.example.autofarmer.Crop.repository.CropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropService {

    private final CropRepository cropRepository;
    Crop crop = new Crop();


    @Autowired
    public CropService(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public Crop findByCropName(String cropName) {
        return cropRepository.findByCropName(cropName);
    }

    public List<Crop> findByCategoryNumOrderByHarvestDateAsc(int categoryNum) {
        return cropRepository.findByCategoryNumOrderByHarvestDateAsc(categoryNum);
    }

    public List<Crop> findByCategoryNumAndTempMinGreaterThanEqualAndTempMaxLessThanEqual(int categoryNum, int tempMin, int tempMax) {
        return cropRepository.findByCategoryNumAndTempMinGreaterThanEqualAndTempMaxLessThanEqual(categoryNum, tempMin, tempMax);
    }
    public List<Crop> findByCategoryNumAndHumidityMinGreaterThanEqualAndHumidityMaxLessThanEqual(int categoryNum, int humidityMin, int humidityMax) {
        return cropRepository.findByCategoryNumAndHumidityMinGreaterThanEqualAndHumidityMaxLessThanEqual(categoryNum, humidityMin, humidityMax);
    }
}
