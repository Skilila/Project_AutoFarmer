// 센서에서 받아온값 저강하는 용도

package com.example.autofarmer.Crop.DTO;

public class SensorDto {
    private Double airTemp;
    private Double airHumidity;
    private Double soilTemp;
    private Integer soilMoisture;
    private Integer lux;

    public Double getAirTemp() {
        return airTemp;
    }
    public void setAirTemp(Double airTemp) {
        this.airTemp = airTemp;
    }

    public Double getAirHumidity() {
        return airHumidity;
    }
    public void setAirHumidity(Double airHumidity) {
        this.airHumidity = airHumidity;
    }

    public Double getSoilTemp() {
        return soilTemp;
    }
    public void setSoilTemp(Double soilTemp) {
        this.soilTemp = soilTemp;
    }

    public Integer getSoilMoisture() {
        return soilMoisture;
    }
    public void setSoilMoisture(Integer soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public Integer getLux() {
        return lux;
    }
    public void setLux(Integer lux) {
        this.lux = lux;
    }
}
