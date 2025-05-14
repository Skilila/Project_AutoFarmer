package com.example.autofarmer.Crop.repository;

import com.example.autofarmer.Crop.domain.Crop;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop, String> {

  Crop findByCropName(String cropName);

  List<Crop> findByCategoryNumOrderByCropNameAsc(int categoryNum);

  List<Crop> findByCategoryNumAndTempMinGreaterThanEqualAndTempMaxLessThanEqual(int categoryNum, int tempMin, int tempMax);

  List<Crop> findByCategoryNumAndHumidityMinGreaterThanEqualAndHumidityMaxLessThanEqual(int categoryNum, int humidityMin, int humidityMax);
}
