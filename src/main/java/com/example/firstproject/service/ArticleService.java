package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service // 어노테이션, 해당 클래스를 서비스로 인식해 스프링 부트에 서비스 객체 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository; //  게시글 리파지터리 객체 주입

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity(); // dto를 entity로 변환 후 article에 저장
        if(article.getId() != null) return null;
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1. DTO -> 엔티티 변환
        Article article = dto.toEntity();
        log.info("id: {}, article : {}", id, article.toString());
        //2. 타깃 조회하기 기존데이터
        Article target = articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if(target ==null || id != article.getId()){
            log.info("잘못된 요청! id : {}, article : {}", id, article.toString());
            return null; //응답은 컨트롤러가하므로 그냥 null 반환.
        }
        //4. 업데이트 및 정상 응답(200)하기
        target.patch(article);
        Article updated =  articleRepository.save(target); // 엔티티 데이터 DB에 저장
        return updated; // 응답은 컨트롤러가 하므로 수정데이터만 반환
    }

    public Article delete(Long id) {
        //1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //2. 잘못된 요청 처리하기
        if(target == null) return null;
        //3. 대상 삭제하기
        articleRepository.delete(target);
        return target;
    }
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음(리스트)을 엔티티 묶음(리스트)로 변환하기
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());
        // 2. 엔티티 묵음(리스트)을 DB에 저장하기
        articleList.stream().forEach(article -> articleRepository.save(article)); //리스트 엔티티를 디비에 저장.
        // 3. 강제로 에러를 발생시키기
        articleRepository.findById(-1L).orElseThrow(()->new IllegalArgumentException("결제 실패!")); //예외 던져버리기
        // 4. 결과값 반환하기.
        return articleList;
    }
}
