package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j //로그 찍을 수 있게 해주는는 것.
@RestController
public class ArticleApiController {
    @Autowired //게시글 리파지터리 주입, 의존성 정의되지않은 아티클 리파지터리.
    private ArticleService articleService; //서비스 객체 생성;
    private ArticleRepository articleRepository;
    //GET
    @GetMapping("/api/articles") //URL요청 접수
    public List<Article> index() { //메서드 수행 결과, 아티클 묶음 반환.
        return articleService.index();
    }
    @GetMapping("/api/articles/{id}") //URL요청 접수
    public Article show(@PathVariable Long id) { //id 가져와야하니 @PathVariable 어노테이션
        return articleService.show(id);
    }
    //POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto); //수정할 데이터를 DTO 매개변수로 가져온다. 엔티티로 변환해 변수에 넣어준다.
        return (created != null) ? ResponseEntity.status(HttpStatus.OK).body(created) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //리파지터리를 통해 DB에 저장하고 이를 반환해준다.
    }
    //PATCH
    @PatchMapping ("/api/articles/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ArticleForm dto) { //반환형 수정
        Article updated = articleService.update(id, dto);
        return(updated != null) ?
            ResponseEntity.status(HttpStatus.OK).body(updated): ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    //DELETE
    @DeleteMapping ("/api/articles/{id}") //URL 요청 접수
    public ResponseEntity<Article> delete(@PathVariable Long id){ //메서드 정의
       Article deleted = articleService.delete(id);
        return(deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).body(deleted): ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PostMapping ("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList = articleService.createArticles(dtos);
        return(createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList): ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}
