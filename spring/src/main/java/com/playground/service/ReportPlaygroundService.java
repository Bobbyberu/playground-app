package com.playground.service;

import com.playground.model.entity.Playground;
import com.playground.model.entity.ReportPlayground;
import com.playground.repository.ReportPlaygroundRepository;
import com.playground.service.interfaces.IReportPlaygroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ReportPlaygroundService
 */
@Service
public class ReportPlaygroundService implements IReportPlaygroundService {

    /** ReportPlaygroundRepository reportPlaygroundRepository */
    private final ReportPlaygroundRepository reportPlaygroundRepository;

    /**
     * ReportPlaygroundService constructor
     *
     * @param reportPlaygroundRepository ReportPlaygroundRepository
     */
    @Autowired
    public ReportPlaygroundService(ReportPlaygroundRepository reportPlaygroundRepository) {
        this.reportPlaygroundRepository = reportPlaygroundRepository;
    }

    @Override
    public List<ReportPlayground> getReportPlaygrounds() {
        List<ReportPlayground> reportPlaygrounds = new ArrayList<>();
        reportPlaygroundRepository.findAll().forEach(reportPlaygrounds::add);

        return reportPlaygrounds;
    }

    @Override
    public List<ReportPlayground> getReportPlaygroundsByPlayground(Playground playground) {
        return reportPlaygroundRepository.getByPlayground(playground);
    }

    @Override
    public ReportPlayground getReportPlayground(int id) {
        return reportPlaygroundRepository.findById(id).orElse(null);
    }

    @Override
    public ReportPlayground createReportPlayground(Playground playground, ReportPlayground reportPlayground) {
        reportPlayground.setPlayground(playground);

        return reportPlaygroundRepository.save(reportPlayground);
    }

    @Override
    public ReportPlayground updateReportPlayground(int id, ReportPlayground reportPlayground) {
        reportPlayground.setId(id);

        return reportPlaygroundRepository.save(reportPlayground);
    }

    @Override
    public void deleteReportPlayground(ReportPlayground reportPlayground) {
        reportPlaygroundRepository.delete(reportPlayground);
    }
}
