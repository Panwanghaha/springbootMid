package com.example.springbootmid.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :Panking
 * @date : 2022/9/28
 */
@RestController
public class TomController {
    @RequestMapping("/")
    public String home() {
        return "hello word";
    }
}
