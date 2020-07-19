package com.carlozfelipe.foodcontrolback.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UtilidadFecha {
    public static int meses(Date ini, Date fin){
        if(ini==null || fin == null){
            throw new IllegalArgumentException("UtilidadFecha.meses: fechas nulas");
        }
        if(ini.after(fin) && !ini.equals(fin)){
            throw new IllegalArgumentException("UtilidadFecha.meses: fechas inicial despues de fecha final");
        }
        Calendar inicio = new GregorianCalendar();
        inicio.setTime(ini);
        Calendar finl = new GregorianCalendar();
        finl.setTime(fin);
        return 12*(finl.get(Calendar.YEAR)-inicio.get(Calendar.YEAR))+(finl.get(Calendar.MONTH)-inicio.get(Calendar.MONTH));
    }

    public static Date hoyAlInicioDelDia(){
        return new Date(Timestamp.valueOf(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0,0,0,0))).getTime());
    }

    public static Date incrementarAnio(Date fecha){
        Calendar c = new GregorianCalendar();
        c.setTime(fecha);
        c.add(Calendar.YEAR,1);
        return c.getTime();
    }
}
