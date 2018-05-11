package com.hao.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hao on 17-12-12.
 */
@Controller
@RequestMapping
public class HelloController {

    @RequestMapping("/hello")
    public Map<String, String> helloMvc() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("result", "SUCCESS");

        return map;
    }

    @RequestMapping("/Status")
    public Map<String, String> status() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("result", "ALIVE");

        return map;
    }
}
