package com.djb.im.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.djb.im.enitity.BackMessageInfo;
import com.djb.im.enitity.KeywordRule;
import com.djb.im.enitity.LanguageInfo;
import com.djb.im.enitity.MessageInfo;
import com.djb.im.mapper.BackMessageInfoMapper;
import com.djb.im.mapper.KeywordRuleMapper;
import com.djb.im.mapper.LanguageInfoMapper;
import com.djb.im.mapper.MessageInfoMapper;
import com.djb.im.pojo.WxXmlData;
import com.djb.im.service.CommonService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Service
@Slf4j
public class CommonServiceImpl implements CommonService {
    @Autowired
    private KeywordRuleMapper keywordRuleMapper;
    @Autowired
    private MessageInfoMapper messageInfoMapper;
    @Autowired
    private BackMessageInfoMapper backMessageInfoMapper;
    @Autowired
    private LanguageInfoMapper languageInfoMapper;

    public String messageHandle(InputStream in) throws IOException {
        WxXmlData wxXmlData = resolveXmlData(in);
        //对消息进行入库
        MessageInfo info = new MessageInfo();
        info.setMessage(wxXmlData.getContent());
        messageInfoMapper.insert(info);
        //分词
        List<Word> seg = WordSegmenter.segWithStopWords(wxXmlData.getContent());
        //返回
        BackMessageInfo backMessageInfo = new BackMessageInfo();
        backMessageInfo.setMessageId(info.getId());
        String returnMessage ="";
        TreeMap<Integer,String> map = new TreeMap<Integer,String>(
                new Comparator<Integer>() {
                    public int compare(Integer obj1, Integer obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
        for(Word word : seg){
            log.info("=============="+word.toString());
            //查询所有的关键字
            LambdaQueryWrapper<KeywordRule> queryWrapper = new LambdaQueryWrapper<KeywordRule>();
            queryWrapper.like(KeywordRule::getKeyword,word.toString());
            queryWrapper.orderByAsc(KeywordRule::getWeight);
            queryWrapper.last("limit 1");
            KeywordRule rule = keywordRuleMapper.selectOne(queryWrapper);
            if(null == rule){
                backMessageInfo.setRemark("暂未定义此消息回复规则");
                continue;
            }
            String languageId = rule.getLanguageId();
            String[] split = languageId.split(",");
            List<String> stringList = new ArrayList<>();
            for(String str : split){
                stringList.add(str);
            }
            if(CollectionUtils.isEmpty(stringList)){
                backMessageInfo.setRemark("暂未定义此消息回复规则");
                backMessageInfoMapper.insert(backMessageInfo);
                continue;
            }
            returnMessage  = stringList.get(((int) (Math.random() * stringList.size())));
            map.put(rule.getWeight(),returnMessage);
        }
        if(CollectionUtils.isEmpty(map)){
            return "对不起，我不理解您的意思";
        }
        Integer integer = map.firstKey();
        backMessageInfo.setLanguageId(Integer.valueOf(map.get(integer)));
        backMessageInfoMapper.insert(backMessageInfo);
        LanguageInfo languageInfo = languageInfoMapper.selectById(Integer.valueOf(map.get(integer)));
        return languageInfo.getLanguage();
    }

    public WxXmlData resolveXmlData(InputStream in) throws IOException {
        Scanner s = new Scanner(in).useDelimiter("\\A");
        String xmlData = s.hasNext() ? s.next() : "";
        log.info("【receive  xmlData str : {}】", xmlData);
        Map response = JSON.parseObject(xmlData, Map.class);
        Object content = response.get("Content");
        WxXmlData wxXmlData = new WxXmlData();
        wxXmlData.setContent(content.toString());
        return wxXmlData;
    }
}
