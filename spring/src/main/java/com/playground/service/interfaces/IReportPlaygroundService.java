package com.playground.service.interfaces;

import com.playground.model.Playground;
import com.playground.model.ReportPlayground;

import java.util.List;

/**
 * Interface IReportPlaygroundService
 */
public interface IReportPlaygroundService {

    /**
     * Return all reports for playgrounds
     *
     * @return List<ReportPlayground>
     */
    List<ReportPlayground> getReportPlaygrounds();

    /**
     * Return all reports for a playground
     *
     * @param playground Playground
     *
     * @return List<ReportPlayground>
     */
    List<ReportPlayground> getReportPlaygroundsByPlayground(Playground playground);

    /**
     * Return one report playground
     *
     * @param id int
     *
     * @return ReportPlayground
     */
    ReportPlayground getReportPlayground(int id);

    /**
     * Return a report playground of one playground
     *
     * @param playground Playground
     * @param id int
     *
     * @return ReportPlayground
     */
    ReportPlayground getReportPlaygroundByPlayground(Playground playground, int id);

    /**
     * Create a report playground and return it
     *
     * @param playground Playground
     * @param reportPlayground ReportPlayground
     *
     * @return ReportPlayground
     */
    ReportPlayground createReportPlayground(Playground playground, ReportPlayground reportPlayground);

    /**
     * Update a report playground and return it
     *
     * @param id int
     * @param reportPlayground ReportPlayground
     *
     * @return ReportPlayground
     */
    ReportPlayground updateReportPlayground(int id, ReportPlayground reportPlayground);

    /**
     * Delete a report playground
     *
     * @param reportPlayground ReportPlayground
     */
    void deleteReportPlayground(ReportPlayground reportPlayground);
}
