package com.basidoni.backend.controller;

import com.basidoni.backend.model.MoveResponse;
import com.basidoni.backend.service.GameService;
import com.basidoni.backend.service.MinimaxAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class GameControler {

    @Autowired
    private GameService service;

    @PostMapping("/getBestMove")
    public MoveResponse getBestMove(@RequestBody char[][] state) {
        return MinimaxAlgorithm.findBestMove(state);
    }

    @PostMapping("/getBestMoveToMove")
    public MoveResponse getBestMoveToMove(@RequestBody char[][] state) {
        return service.getBestMoveToMove(state);
    }
}

