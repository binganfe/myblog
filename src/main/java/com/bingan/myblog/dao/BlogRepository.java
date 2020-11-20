package com.bingan.myblog.dao;

import com.bingan.myblog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findRecommendTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1") //?1代表第一个参数
    Page<Blog> findByQuery(Pageable pageable, String query);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id=?1")
    void updateViews(Long id);

    @Query("select function('date_format', b.updateTime, '%Y') as year from Blog b group by function('date_format', b.updateTime, '%Y') order by function('date_format', b.updateTime, '%Y') desc")
    List<String> findGroupYears();

    @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1")
    List<Blog> findByYear(String year);
}
