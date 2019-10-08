package com.playground.controllers;

import com.playground.model.entity.Comment;
import com.playground.model.entity.Playground;
import com.playground.model.entity.ReportComment;
import com.playground.model.entity.ReportPlayground;
import com.playground.model.entity.Schedule;
import com.playground.model.entity.Sport;
import com.playground.model.entity.User;
import com.playground.model.wrapper.PlaygroundWrapper;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllersUnitTestUtils {

    public static User buildUser() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1);
        when(user.getUsername()).thenReturn("user");
        when(user.getPassword()).thenReturn("pwd");

        return user;
    }

    public static Playground buildPlayground() {
        Playground playground = mock(Playground.class);
        when(playground.getId()).thenReturn(1);
        when(playground.getName()).thenReturn("name");
        when(playground.isCovered()).thenReturn(false);
        when(playground.isPrivate()).thenReturn(false);
        when(playground.getLatitude()).thenReturn(Double.valueOf(0));
        when(playground.getLongitude()).thenReturn(Double.valueOf(0));
        when(playground.getSurface()).thenReturn("surface");
        when(playground.getDescription()).thenReturn("description");
        when(playground.getCity()).thenReturn("Villié-Morgon");
        when(playground.getAddress()).thenReturn("address");

        return playground;
    }

    public static ReportPlayground buildReportPlayground(Playground playground, User author) {
        ReportPlayground reportPlayground = mock(ReportPlayground.class);

        when(reportPlayground.getPlayground()).thenReturn(playground);
        when(reportPlayground.getAuthor()).thenReturn(author);
        when(reportPlayground.getDescription()).thenReturn("description");

        return reportPlayground;
    }

    public static Comment buildComment() {
        Playground playground = buildPlayground();

        User user = buildUser();

        Comment comment = mock(Comment.class);
        when(comment.getId()).thenReturn(1);
        when(comment.getMark()).thenReturn(Double.valueOf(5));
        when(comment.getPlayground()).thenReturn(playground);
        when(comment.getAuthor()).thenReturn(user);

        return comment;
    }

    public static ReportComment buildReportComment(Comment comment, User author) {
        ReportComment reportComment = mock(ReportComment.class);

        when(reportComment.getComment()).thenReturn(comment);
        when(reportComment.getAuthor()).thenReturn(author);
        when(reportComment.getDescription()).thenReturn("description");

        return reportComment;
    }

    public static Set<Schedule> buildSchedules() {
        Set<Schedule> schedules = new HashSet<>();

        Schedule schedule = mock(Schedule.class);

        when(schedule.getOpening()).thenReturn(LocalTime.NOON);
        when(schedule.getClosure()).thenReturn(LocalTime.MIDNIGHT);
        when(schedule.getDay()).thenReturn(DayOfWeek.MONDAY);
        schedules.add(schedule);

        return schedules;
    }

    public static Sport buildSport() {
        Sport sport = mock(Sport.class);

        when(sport.getId()).thenReturn(1);
        when(sport.getName()).thenReturn("sport");
        when(sport.getSymbol()).thenReturn("");

        return sport;
    }
}
