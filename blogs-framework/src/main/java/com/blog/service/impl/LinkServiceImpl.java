package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Link;
import com.blog.domain.vo.LinkVo;
import com.blog.domain.vo.PageVo;
import com.blog.mapper.LinkMapper;
import com.blog.service.LinkService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SystemConstants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    // todo 获取所有友链
    /*
     如果进行评论 ，那么就可以将用户的网站名、地址、描述、logo放上去
     */
    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);

        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize) {
        return null;
    }
}