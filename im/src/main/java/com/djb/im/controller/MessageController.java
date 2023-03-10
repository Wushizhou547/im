package com.djb.im.controller;

import com.djb.im.pojo.MessageDto;
import com.djb.im.service.CommonService;
import com.djb.im.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class MessageController {

    @Autowired
    private CommonService commonService;

    /**
     * 处理微信接收到的消息
     *
     * @param request
     * @return
     */
    @PostMapping("/message/post")
    public String pushMessage(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        return commonService.messageHandle(inputStream);
    }
}
