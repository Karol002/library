package com.library.controller;

import com.library.model.Title;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TitleController {
    @GetMapping("/titles")
    public List<Title> getTitles() {
        throw new IllegalArgumentException("Not implementet yet!");
    }
    @GetMapping("/titles/{id}")
    public Title getTitle() {
        throw new IllegalArgumentException("Not implementet yet! title");
    }
}