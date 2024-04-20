package com.shopmax.contorller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping(value ="/") // localhost/
    public String main() {
        return "main"; //메인화면(main.html) 띄움
    }


}