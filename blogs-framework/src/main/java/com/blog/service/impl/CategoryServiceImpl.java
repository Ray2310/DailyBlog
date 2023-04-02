package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Article;
import com.blog.domain.entity.Category;

import com.blog.domain.vo.CategoryVo;
import com.blog.domain.vo.PageVo;
import com.blog.mapper.CategoryMapper;
import com.blog.service.ArticleService;
import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SystemConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private ArticleService articleService;


    //todo 分类请求
    @Override
    public ResponseResult getCategoryList() {
        //1. 先在文章表中查询 status（文章发布or未发布）为 0 的，也就是发布了的 。还有就是未删除的
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUT);
        List<Article> articles = articleService.list(queryWrapper);
        //2. 查出上一步的之后只需要查分类id就可以了（category_id）
        //todo 函数式编程 ，用来将查询到的id查询category
        Set<Long> categoryIds = articles.stream()
                .map(new Function<Article, Long>() {
                    @Override
                    public Long apply(Article article) {
                        return article.getCategoryId();
                    }
                }).collect(Collectors.toSet());
        //3. 然后再到category表中查出对应的名称即可 ,还需要判断分类的状态是正常的
        List<Category> categories = listByIds(categoryIds);
        //4. 判断分类的状态是正常的
        List<Category> collect = categories.stream().filter(category -> category.getStatus().equals(SystemConstants.ARTICLE_CATEGORY_STATUS)).collect(Collectors.toList());
        //5. 封装状态
        List<CategoryVo> categoryVoList = BeanCopyUtils.copyBeanList(collect, CategoryVo.class);

        return ResponseResult.okResult(categoryVoList);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        //查询出所有没有删除的分类
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.CATEGORY_STATUS);//没有被禁用的
        queryWrapper.eq(Category::getDelFlag,SystemConstants.CATEGORY_NOTDEL);//没有被删除的
        List<Category> list = list(queryWrapper);
        List<CategoryVo> categoryVo1s = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVo1s;
    }

    /**
     * 分页导出所有分类
     * @param pageNum 页码
     * @param pageSize 分页大小
     * @return
     */
    @Override
    public ResponseResult listAllPage(int pageNum, int pageSize,CategoryVo categoryVo) {
        //先获取所有可用的分类
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //进行模糊查询
        queryWrapper.like(Objects.nonNull(categoryVo.getName()),Category::getName,categoryVo.getName());
        queryWrapper.like(Objects.nonNull(categoryVo.getStatus()),Category::getStatus,categoryVo.getStatus());
        //判断是否可用
        queryWrapper.eq(Category::getDelFlag,SystemConstants.CATEGORY_NOTDEL);//没有被删除的
        //进行分页处理
        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        //按照响应格式返回
        List<Category> records = page.getRecords();
        List<CategoryVo> list = BeanCopyUtils.copyBeanList(records, CategoryVo.class);
        PageVo pageVo = new PageVo(list,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 新增分类
     * @param categoryVo
     * @return
     */
    @Override
    public ResponseResult addCategory(CategoryVo categoryVo) {
        Category category = BeanCopyUtils.copyBean(categoryVo, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    /**
     * 根据id获取分类信息
     */
    @Override
    public ResponseResult getCategoryById(Long id) {
        Category byId = getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(byId, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    /**
     * 更新分类
     * @param categoryVo
     * @return
     */
    @Override
    @Transactional
    public ResponseResult updateCategory(CategoryVo categoryVo) {
        Category category = BeanCopyUtils.copyBean(categoryVo, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }


    /**
     * 逻辑删除分类
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteCategory(Long id) {
        UpdateWrapper<Category> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("del_flag",SystemConstants.DELETE);
        update(wrapper);
        return ResponseResult.okResult();
    }
}
