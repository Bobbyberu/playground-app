package com.playground.controllers;

import com.playground.model.dto.ReportPlaygroundDto;
import com.playground.model.entity.Playground;
import com.playground.model.entity.ReportPlayground;
import com.playground.service.PlaygroundService;
import com.playground.service.ReportPlaygroundService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class ReportPlaygroundController
 */
@RestController
public class ReportPlaygroundController {

    /** ReportPlaygroundService reportPlaygroundService */
    private final ReportPlaygroundService reportPlaygroundService;

    /** PlaygroundService playgroundService */
    private final PlaygroundService playgroundService;

    /**
     * ReportPlaygroundController constructor
     *
     * @param reportPlaygroundService ReportPlaygroundService
     * @param playgroundService playgroundService
     */
    @Autowired
    public ReportPlaygroundController(ReportPlaygroundService reportPlaygroundService, PlaygroundService playgroundService) {
        this.reportPlaygroundService = reportPlaygroundService;
        this.playgroundService = playgroundService;
    }

    /**
     * [GET] Return all reports for playgrounds
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/reportPlaygrounds", produces = "application/json")
    public ResponseEntity<List<ReportPlaygroundDto>> getReportPlaygrounds() {
        List<ReportPlaygroundDto> reportedPlaygrounds = reportPlaygroundService.getReportPlaygrounds().stream()
                .map(rp -> new ReportPlaygroundDto(rp))
                .collect(Collectors.toList());

        return new ResponseEntity<>(reportedPlaygrounds, HttpStatus.OK);
    }

    /**
     * [GET] Return one report playground by id
     *
     * @param reportPlaygroundId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     * @throws ResourceNotFoundException ReportPlayground not found
     */
    @GetMapping(value = "/reportPlaygrounds/{reportPlaygroundId}", produces = "application/json")
    public ResponseEntity<ReportPlaygroundDto> getReportPlayground(
            @PathVariable("reportPlaygroundId") int reportPlaygroundId) throws ResourceNotFoundException {

        ReportPlayground reportPlayground = reportPlaygroundService.getReportPlayground(reportPlaygroundId);

        if (reportPlayground == null) {
            throw new ResourceNotFoundException("ReportPlayground with id " + reportPlaygroundId + " not found for this playground");
        }

        return new ResponseEntity<>(new ReportPlaygroundDto(reportPlayground), HttpStatus.OK);
    }

    /**
     * [POST] Create a report playground and return it
     *
     * @param playgroundId int
     * @param reportPlayground ReportPlayground
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     */
    @PostMapping(value = "/playgrounds/{playgroundId}/reportPlaygrounds", consumes = "application/json")
    public ResponseEntity<ReportPlaygroundDto> createReportPlayground(
            @PathVariable("playgroundId") int playgroundId, @RequestBody ReportPlayground reportPlayground) throws ResourceNotFoundException {
        Playground playground = playgroundService.getPlayground(playgroundId);

        if (playground == null) {
            throw new ResourceNotFoundException("Playground with id " + playgroundId + " not found");
        }

        return new ResponseEntity<>(
                new ReportPlaygroundDto(reportPlaygroundService.createReportPlayground(playground, reportPlayground)), HttpStatus.CREATED);
    }

    /**
     * [PUT] Update a report playground and return it
     *
     * @param reportPlaygroundId int
     * @param reportPlayground ReportPlayground
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     * @throws ResourceNotFoundException ReportPlayground not found
     */
    @PutMapping("/reportPlaygrounds/{reportPlaygroundId}")
    public ResponseEntity<ReportPlaygroundDto> updateReportPlayground(@PathVariable("reportPlaygroundId") int reportPlaygroundId,
            @RequestBody ReportPlayground reportPlayground) throws ResourceNotFoundException {

        ReportPlayground currentReportPlayground = reportPlaygroundService.getReportPlayground(reportPlaygroundId);

        if (currentReportPlayground == null) {
            throw new ResourceNotFoundException("ReportPlayground with id " + reportPlaygroundId + " not found for this playground");
        }

        return new ResponseEntity<>(
                new ReportPlaygroundDto(reportPlaygroundService.updateReportPlayground(reportPlaygroundId, reportPlayground)), HttpStatus.OK);
    }

    /**
     * [DELETE] Delete a report playground
     *
     * @param reportPlaygroundId int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Playground not found
     * @throws ResourceNotFoundException Comment not found
     */
    @DeleteMapping("/reportPlaygrounds/{reportPlaygroundId}")
    public ResponseEntity deleteReportPlayground(@PathVariable("reportPlaygroundId") int reportPlaygroundId) throws ResourceNotFoundException {

        ReportPlayground currentReportPlayground = reportPlaygroundService.getReportPlayground(reportPlaygroundId);

        if (currentReportPlayground == null) {
            throw new ResourceNotFoundException("ReportPlayground with id " + reportPlaygroundId + " not found for this playground");
        }

        reportPlaygroundService.deleteReportPlayground(currentReportPlayground);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
