package com.crm.service;

import com.crm.entity.Reporting;

import java.util.List;

public interface ReportingService {
    List<Reporting> getAllReports();
    Reporting save(Reporting reporting);
}
