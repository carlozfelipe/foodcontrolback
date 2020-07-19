package com.carlozfelipe.foodcontrolback.negocio.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReporteTemporalDTO {
    private List<String> fechas = new ArrayList<>();
    private List<Long> valores = new ArrayList<>();
}
