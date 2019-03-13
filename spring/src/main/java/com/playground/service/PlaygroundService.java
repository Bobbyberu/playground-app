package com.playground.service;

import com.playground.model.entity.Playground;
import com.playground.model.entity.Sport;
import com.playground.model.entity.User;
import com.playground.model.wrapper.ScheduleWrapper;
import com.playground.repository.PlaygroundRepository;
import com.playground.service.interfaces.IPlaygroundService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class PlaygroundService
 */
@Service
public class PlaygroundService implements IPlaygroundService {

    @Inject
    private PlaygroundRepository playgroundRepository;

    @Inject
    private UserService userService;

    @Override
    public List<Playground> getPlaygrounds() {
        List<Playground> playgrounds = new ArrayList<>();
        playgroundRepository.findAll().forEach(playgrounds::add);

        return  playgrounds;
    }

    @Override
    public Playground getPlayground(int id) {
        return playgroundRepository.findById(id).orElse(null);
    }

    @Override
    public Playground createPlayground(Playground playground) {
        return playgroundRepository.save(playground);
    }

    @Override
    public Playground updatePlayground(int id, Playground playground) {
        playground.setId(id);
        return playgroundRepository.save(playground);
    }

    @Override
    public Playground addPlayerToPlayground(Playground playground, User user, Sport sport) {
        Set<User> players = playground.getPlayers();
        user.setPlaying(sport);
        userService.updateUser(user.getId(), user);
        players.add(user);
        playground.setPlayers(players);
        return playgroundRepository.save(playground);
    }

    @Override
    public Playground removePlayerFromPlayground(Playground playground, User user) {
        Set<User> players = playground.getPlayers();
        user.setPlaying(null);
        userService.updateUser(user.getId(), user);
        players.remove(user);
        playground.setPlayers(players);
        return playgroundRepository.save(playground);
    }

    @Override
    public List<User> getPlayersOnPlayground(Playground playground) {
        List<User> list = new ArrayList<>();
        list.addAll(playground.getPlayers());
        return list;
    }

    @Override
    public void deletePlayground(Playground playground) {
        playgroundRepository.delete(playground);
    }

    @Override
    public List<Playground> searchPlaygroundByKeyword(String keyword) {
        List<Playground> playgrounds = new ArrayList<>();
        playgroundRepository.search(keyword).forEach(playgrounds::add);

        return playgrounds;
    }

    public boolean isTimeInvalid(ScheduleWrapper scheduleWrapper) {
        return scheduleWrapper.getOpeningHour() < 0 || scheduleWrapper.getOpeningHour() > 23
                || scheduleWrapper.getOpeningMinute() < 0 || scheduleWrapper.getOpeningMinute() > 59
                || scheduleWrapper.getClosureHour() < 0 || scheduleWrapper.getClosureHour() > 23
                || scheduleWrapper.getClosureMinute() < 0 || scheduleWrapper.getClosureMinute() > 59;
    }
}
