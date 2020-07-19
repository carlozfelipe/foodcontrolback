package com.carlozfelipe.foodcontrolback.negocio.DO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ReporteTemporalDO {
    private Date fecha;
    private Long valor;

    public ReporteTemporalDO(Long valor){
        this.valor=valor;
    }
}
