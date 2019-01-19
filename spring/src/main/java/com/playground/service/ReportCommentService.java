package com.playground.service;

import com.playground.model.Comment;
import com.playground.model.ReportComment;
import com.playground.repository.ReportCommentRepository;
import com.playground.service.interfaces.IReportCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ReportCommentService
 */
@Service
public class ReportCommentService implements IReportCommentService {

    /** ReportCommentRepository reportCommentRepository */
    private final ReportCommentRepository reportCommentRepository;

    /**
     * ReportCommentService constructor
     *
     * @param reportCommentRepository ReportCommentRepository
     */
    @Autowired
    public ReportCommentService(ReportCommentRepository reportCommentRepository) {
        this.reportCommentRepository = reportCommentRepository;
    }

    @Override
    public List<ReportComment> getReportComments() {
        List<ReportComment> reportComments = new ArrayList<>();
        reportCommentRepository.findAll().forEach(reportComments::add);

        return reportComments;
    }

    @Override
    public List<ReportComment> getReportCommentsByComment(Comment comment) {
        return reportCommentRepository.getByComment(comment);
    }

    @Override
    public ReportComment getReportComment(int id) {
        return reportCommentRepository.findById(id).orElse(null);
    }

    @Override
    public ReportComment getReportCommentByComment(Comment comment, int id) {
        return reportCommentRepository.getOneByComment(comment, id);
    }

    @Override
    public ReportComment createReportComment(Comment comment, ReportComment reportComment) {
        reportComment.setComment(comment);

        return reportCommentRepository.save(reportComment);
    }

    @Override
    public ReportComment updateReportComment(int id, ReportComment reportComment) {
        reportComment.setId(id);

        return reportCommentRepository.save(reportComment);
    }

    @Override
    public void deleteReportComment(ReportComment reportComment) {
        reportCommentRepository.delete(reportComment);
    }
}
