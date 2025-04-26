package com.example.autofarmer.Crop.controller;

import com.example.autofarmer.Crop.DTO.SensorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SensorController {

    @PostMapping("/airTemp")
    public ResponseEntity<String> receiveAirTemp(@RequestBody SensorDto dto) {
        System.out.println(" airTemp : " + dto.getAirTemp());
        return ResponseEntity.ok("airTemp OK");
    }

    @PostMapping("/airHumidity")
    public ResponseEntity<String> receiveAirHumidity(@RequestBody SensorDto dto) {
        System.out.println(" airHumidity : " + dto.getAirHumidity());
        return ResponseEntity.ok("airHumidity OK");
    }

    @PostMapping("/soilTemp")
    public ResponseEntity<String> receiveSoilTemp(@RequestBody SensorDto dto) {
        System.out.println("🌱soilTemp : " + dto.getSoilTemp());
        return ResponseEntity.ok("soilTemp OK");
    }

    @PostMapping("/soilMoisture")
    public ResponseEntity<String> receiveSoilMoisture(@RequestBody SensorDto dto) {
        System.out.println(" soilMoisture : " + dto.getSoilMoisture());
        return ResponseEntity.ok("soilMoisture OK");
    }

    @PostMapping("/lux")
    public ResponseEntity<String> receiveLux(@RequestBody SensorDto dto) {
        System.out.println(" lux : " + dto.getLux());
        return ResponseEntity.ok("lux OK");
    }
}
