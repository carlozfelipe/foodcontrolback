package com.carlozfelipe.foodcontrolback.rest;

import com.carlozfelipe.foodcontrolback.negocio.DTO.ReporteTemporalDTO;
import com.carlozfelipe.foodcontrolback.negocio.ReporteNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reporte")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class ReportesRest {

    @Autowired
    private ReporteNegocio reporteNegocio;

    @GetMapping("/dias/{fechaInicial}/{fechaFinal}")
    public ReporteTemporalDTO obtenerReportePorDias(@PathVariable String fechaInicial, @PathVariable String fechaFinal){
        if(fechaInicial == null){
            throw new IllegalArgumentException("Fecha inicial nula");
        }
        if(fechaFinal == null){
            throw new IllegalArgumentException("Fecha final nula");
        }
        return reporteNegocio.obtenerReportePorDias(fechaInicial, fechaFinal);
    }

    @GetMapping("/mes/{fechaInicial}/{fechaFinal}")
    public ReporteTemporalDTO obtenerReportePorMes(@PathVariable String fechaInicial, @PathVariable String fechaFinal){
        if(fechaInicial == null){
            throw new IllegalArgumentException("Fecha inicial nula");
        }
        if(fechaFinal == null){
            throw new IllegalArgumentException("Fecha final nula");
        }
        return reporteNegocio.obtenerReportePorMes(fechaInicial, fechaFinal);
    }
}
