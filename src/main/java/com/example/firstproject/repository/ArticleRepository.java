package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> { //CrudRepository는 JPA제공 인터페이스
    //관리 대상 엔티티의 클래스타입, 대표값 타입. Article.java에서 id를 대표값으로 설정했으니 ID의 타입인 LONG입력.


    @Override
    ArrayList<Article> findAll(); //메서드의 반환값은 기본적으로 Article의 Iterable타입으로반환하게 해줌 이를 ArrayList바꿔주었다.
    // article
}
