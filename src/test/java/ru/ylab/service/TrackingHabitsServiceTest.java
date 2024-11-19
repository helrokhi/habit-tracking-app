package ru.ylab.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.dto.TrackingDto;
import ru.ylab.repository.StatusRepository;
import ru.ylab.repository.TrackingRepository;

@ExtendWith(MockitoExtension.class)
class TrackingHabitsServiceTest {

    @InjectMocks
    private TrackingHabitsService trackingHabitsService;

    @InjectMocks
    private StatusService statusService;

    @Mock
    private TrackingRepository trackingRepository;

    @Mock
    private StatusRepository statusRepository;

    @Test
    void streak() {
        //statusService.createStatus(1002L);
        //statusService.createStatus(1003L);
        //statusService.createStatus(1006L);
        //statusService.createStatus(1007L);

        //statusService.createStatus(1005L);
        TrackingDto trackingDto = trackingHabitsService.streak(10000L);

        Assertions.assertEquals(10, trackingDto.getNumberOfHabits());
        Assertions.assertEquals(4, trackingDto.getNumberOfHabitsIsDoneToday());
        Assertions.assertEquals(9, trackingDto.getNumberOfHabitsNeedDoneToday());

    }
}