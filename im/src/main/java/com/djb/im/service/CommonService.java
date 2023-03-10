package com.djb.im.service;

import com.djb.im.pojo.MessageDto;
import com.djb.im.utils.Result;

import java.io.IOException;
import java.io.InputStream;


public interface CommonService {

    String messageHandle(InputStream in) throws IOException;

}
