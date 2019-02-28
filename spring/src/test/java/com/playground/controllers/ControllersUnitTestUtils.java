package com.playground.controllers;

import com.playground.model.entity.Comment;
import com.playground.model.entity.Playground;
import com.playground.model.entity.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllersUnitTestUtils {

    public static User buildUser() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1);
        when(user.getUsername()).thenReturn("user");

        return user;
    }

    public static Playground buildPlayground() {
        Playground playground = mock(Playground.class);
        when(playground.getId()).thenReturn(1);

        return playground;
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
}
