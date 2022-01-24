package com.github.fwidde.freedoodle.dao;

import com.github.fwidde.freedoodle.model.Doodle;
import com.github.fwidde.freedoodle.repositories.ApproveRepository;
import com.github.fwidde.freedoodle.repositories.DoodleRepository;
import com.github.fwidde.freedoodle.util.Assert;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
@Flogger
public class DoodleDAOService {
    private final ApproveRepository approveRepository;
    private final DoodleRepository doodleRepository;
    private final UserDAOService userDAOService;

    @Autowired
    public DoodleDAOService(ApproveRepository approveRepository, DoodleRepository doodleRepository, UserDAOService userDAOService) {
        this.approveRepository = approveRepository;
        this.doodleRepository = doodleRepository;
        this.userDAOService = userDAOService;
    }

    public Doodle createDoodle(String name, String description, List<LocalDate> dates, Principal principal){
        Assert.notBlank(name, "Name");
        Assert.notBlank(name, "Description");
        Assert.notEmpty(dates, "Date List");
        Assert.notNull(principal, "Principal");

        Doodle doodle = Doodle.builder()
                .creator(userDAOService.findUserByPrincipal(principal))
                .dates(dates)
                .description(description)
                .name(name)
                .build();

        return doodleRepository.save(doodle);
    }
}
