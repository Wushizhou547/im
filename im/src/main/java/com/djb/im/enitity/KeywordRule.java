package com.djb.im.enitity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("keyword_rule")
public class KeywordRule implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 消息
     */
    private String keyword;

    /**
     * 创建时间
     */
    private String languageId;

    /**
     * 权重
     */
    private Integer weight;

}
