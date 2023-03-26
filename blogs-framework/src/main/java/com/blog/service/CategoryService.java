package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Category;

public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

}
