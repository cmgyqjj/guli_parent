package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author qjj
 * @since 2020-11-01
 */
@RestController
@RequestMapping("/eduservice/edu-comment")
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @GetMapping("selectCommentByPage/{limit}/{current}")
    public R selectCommentByPage(@PathVariable long limit,
                                 @PathVariable long current){
        Page<EduComment> Commentpage = new Page<>(current,limit);
        commentService.page(Commentpage,null);
        long total = Commentpage.getTotal();//总记录数
        List<EduComment> records = Commentpage.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
        
    }

}

