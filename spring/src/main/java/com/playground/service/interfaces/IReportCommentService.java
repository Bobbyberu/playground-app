package com.playground.service.interfaces;

import com.playground.model.entity.Comment;
import com.playground.model.entity.ReportComment;

import java.util.List;

/**
 * Interface IReportCommentService
 */
public interface IReportCommentService {

    /**
     * Return all report comments
     *
     * @return List<ReportComment>
     */
    List<ReportComment> getReportComments();

    /**
     * Return all reports for a comment
     *
     * @param comment Comment
     *
     * @return List<ReportComment>
     */
    List<ReportComment> getReportCommentsByComment(Comment comment);

    /**
     * Return one report comment
     *
     * @param id int
     *
     * @return ReportComment
     */
    ReportComment getReportComment(int id);

    /**
     * Return a report comment of one comment
     *
     * @param comment Comment
     * @param id int
     *
     * @return ReportComment
     */
    ReportComment getReportCommentByComment(Comment comment, int id);

    /**
     * Create a report comment and return it
     *
     * @param comment Comment
     * @param reportComment ReportComment
     *
     * @return ReportComment
     */
    ReportComment createReportComment(Comment comment, ReportComment reportComment);

    /**
     * Update a report comment and return it
     *
     * @param id int
     * @param reportComment ReportComment
     *
     * @return ReportComment
     */
    ReportComment updateReportComment(int id, ReportComment reportComment);

    /**
     * Delete a report comment
     *
     * @param reportComment ReportComment
     */
    void deleteReportComment(ReportComment reportComment);
}
