package com.example.autofarmer.Crop.repository;

import com.example.autofarmer.Crop.domain.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, String> {
	Crop findByCropName(String cropName);

	List<Crop> findByCategoryNumOrderByHarvestDateAsc(int categoryNum);
	List<Crop> findByCategoryNumAndTempMinGreaterThanEqualAndTempMaxLessThanEqual(int categoryNum, int tempMin, int tempMax);
	List<Crop> findByCategoryNumAndHumidityMinGreaterThanEqualAndHumidityMaxLessThanEqual(int categoryNum, int humidityMin, int humidityMax);
}

