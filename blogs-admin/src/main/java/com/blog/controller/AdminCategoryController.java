package com.blog.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Category;
import com.blog.domain.vo.CategoryVo;
import com.blog.domain.vo.ExcelCategoryVo;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.WebUtils;
import org.apache.http.HttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 后台分类信息接口
 */
@RestController
@RequestMapping("/content/category")
public class AdminCategoryController {

    @Resource
    private CategoryService categoryService;


    @PreAuthorize("@ps.hasPerms('content:category:export')") //权限控制 ,检查访问的用户是否有权限
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response){
        //1. 设置下载文件的请求头
        try {
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            List<CategoryVo> list = categoryService.listAllCategory();
            //2. 获取导出的数据
            //3. 把数据写道Excel
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(list, ExcelCategoryVo.class);
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
            //4. 如果出现异常响应json格式
        } catch (Exception e) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    //todo 获取所有分类信息
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

     //todo 获取所有分类,并分页展示
     @GetMapping("/list")
     public ResponseResult listAll(int pageNum ,int pageSize,CategoryVo categoryVo){
         return categoryService.listAllPage(pageNum,pageSize,categoryVo);
     }

     @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryVo categoryVo){
        return categoryService.addCategory(categoryVo);
     }

     @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
     }

     @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryVo categoryVo){
        return categoryService.updateCategory(categoryVo);
     }

     @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
     }

}
