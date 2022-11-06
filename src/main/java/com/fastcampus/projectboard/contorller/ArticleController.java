package com.fastcampus.projectboard.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/articles")
@Controller
public class ArticleController {

    @GetMapping
    public String articles(ModelMap map) {
        // data 넘기기
        map.addAttribute("articles", List.of());

        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String articles(@PathVariable Long articleId, ModelMap map) {
        // TODO: 구현할 때 여기에 실제 데이터를 넘겨줘야함
        map.addAttribute("article", "article");
        map.addAttribute("articleComments", List.of());

        return "articles/detail";
    }
}
