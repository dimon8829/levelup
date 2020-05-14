package com.dihri.sport.test.demo.controller;

import com.dihri.sport.test.demo.model.State;
import com.dihri.sport.test.demo.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.executable.ValidateOnExecution;

@Validated
@RestController
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping("/api/state/{userId}")
    public State updateState(@PathVariable @Min(value = 1) Integer userId, @RequestBody @Valid State state) {
        return stateService.updateState(userId,state.getExp());
    }

    @GetMapping("/api/state/{userId}")
    public State getState(@PathVariable @Min(value = 1) Integer userId) {
        return stateService.getState(userId);
    }
}
