package com.idqqtec.dess.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: james.xu
 * Date: 2018/1/15
 * Time: 15:20
 * Version:V1.0
 */
@Controller
public class IndexAction {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/{page}")
    public String page(@PathVariable("page") String page){
        return page;
    }
}
