package com.github.fabriciolfj.dataadvanced.api;

import com.github.fabriciolfj.dataadvanced.domain.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/author")
@RestController
public class AuthorController {

    private final AuthorService authorService;


}
