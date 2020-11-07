package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UcenterFileDegradeFeignClient implements UcenterClient{


    @Override
    public R getMemberInfo(HttpServletRequest request) {
        return R.error().data("message","调用个人信息错误");
    }
}
