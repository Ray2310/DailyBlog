package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Category;
import com.blog.domain.vo.CategoryVo;

import java.util.List;

public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult listAllPage(int pageNum, int pageSize,CategoryVo categoryVo);

    ResponseResult addCategory(CategoryVo categoryVo);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(CategoryVo categoryVo);

    ResponseResult deleteCategory(Long id);
}
