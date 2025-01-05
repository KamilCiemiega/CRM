package com.crm.controller;

import com.crm.controller.dto.ReportingDTO;
import com.crm.controller.dto.ReportingDetailsDTO;
import com.crm.entity.Reporting;
import com.crm.service.ReportingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private final ModelMapper modelMapper;
    private final ReportingService reportingService;

    @Autowired
    public ReportingController(ModelMapper modelMapper, ReportingService reportingService){
        this.modelMapper = modelMapper;
        this.reportingService = reportingService;
    }

    @GetMapping()
    public ResponseEntity<List<ReportingDetailsDTO>> getAllReports(){
        List<Reporting> listOfReports = reportingService.getAllReports();
        List<ReportingDetailsDTO> listOfReportDTOs = listOfReports.stream()
                .map(report -> modelMapper.map(report, ReportingDetailsDTO.class))
                .toList();

        return ok(listOfReportDTOs);
    }

    @PostMapping()
    public ResponseEntity<ReportingDetailsDTO> saveNewReport(@RequestBody ReportingDTO reportingDTO){
        Reporting report = modelMapper.map(reportingDTO, Reporting.class);
        Reporting savedReport = reportingService.save(report);

        return ok(modelMapper.map(savedReport, ReportingDetailsDTO.class));
    }
}
