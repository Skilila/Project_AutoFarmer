package com.example.autofarmer.Crop.domain;

import io./
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;

@Entity
public class Crop {
	@Id
	@Column(length = 10)
	private String cropName;
	private int categoryNum;
	private LocalDate harvestDate;
	@Getter
	private int tempMin;
	@Getter
	private int tempMax;
	@Getter
	private int humidityMin;
	@Getter
	private int humidityMax;
}
