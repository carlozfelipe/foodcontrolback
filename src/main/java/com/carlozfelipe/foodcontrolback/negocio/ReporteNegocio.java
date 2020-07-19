package com.carlozfelipe.foodcontrolback.negocio;

import com.carlozfelipe.foodcontrolback.dao.PedidoDao;
import com.carlozfelipe.foodcontrolback.negocio.DO.ReporteTemporalDO;
import com.carlozfelipe.foodcontrolback.negocio.DTO.ReporteTemporalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReporteNegocio {

    @Autowired
    PedidoDao pedidoDao;

    public ReporteTemporalDTO obtenerReportePorDias(String fechaInicial, String fechaFinal) {
        String[] ini = fechaInicial.split("-");
        LocalDate inic = LocalDate.of(Integer.parseInt(ini[0]),Integer.parseInt(ini[1]),Integer.parseInt(ini[2]));
        ini = fechaFinal.split("-");
        LocalDate fin = LocalDate.of(Integer.parseInt(ini[0]),Integer.parseInt(ini[1]),Integer.parseInt(ini[2]));
        Date inicial = Date.from(inic.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date finl = Date.from(fin.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<ReporteTemporalDO> reporteTemporalDOList = pedidoDao.obtenerReportePorDias(inicial,finl);
        ReporteTemporalDTO reporteTemporalDTO = new ReporteTemporalDTO();
        DateFormat df = new SimpleDateFormat("MMMdd");
        for (ReporteTemporalDO reporteTemporalDO :reporteTemporalDOList
             ) {
            reporteTemporalDTO.getFechas().add(df.format(reporteTemporalDO.getFecha()));
            reporteTemporalDTO.getValores().add(reporteTemporalDO.getValor());
        }
        return  reporteTemporalDTO;
    }

    public ReporteTemporalDTO obtenerReportePorMes(String fechaInicial, String fechaFinal) {
        String[] ini = fechaInicial.split("-");
        LocalDate inic = LocalDate.of(Integer.parseInt(ini[0]),Integer.parseInt(ini[1]),1);
        String[] fini = fechaFinal.split("-");
        int meses = 12*(Integer.parseInt(fini[0])-Integer.parseInt(ini[0]))+(Integer.parseInt(fini[1])-Integer.parseInt(ini[1]));
        ReporteTemporalDTO reporteTemporalDTO = new ReporteTemporalDTO();
        for(int i = 0; i<= meses; i++){
            Date inicial = Date.from(inic.plusMonths(i).atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date finl = Date.from(inic.plusMonths(i+1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            List<ReporteTemporalDO> reporteTemporalDOList = pedidoDao.obtenerReportePorMes(inicial,finl);
            DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT);
            DateFormat dfs = new SimpleDateFormat("MMMyyyy");

            for (ReporteTemporalDO reporteTemporalDO :reporteTemporalDOList
            ) {
                if(reporteTemporalDO.getValor()!=null) {
                    reporteTemporalDTO.getFechas().add(dfs.format(inicial));
                    reporteTemporalDTO.getValores().add(reporteTemporalDO.getValor());
                }
            }
        }
        return  reporteTemporalDTO;
    }

    public static void main(String[] args){
        String ini[] = {"2020", "06"};
        String fini[] = {"2020", "06"};
        int meses = 12*(Integer.parseInt(fini[0])-Integer.parseInt(ini[0]))+(Integer.parseInt(fini[1])-Integer.parseInt(ini[1]));
        System.out.println(meses);
    }
}
