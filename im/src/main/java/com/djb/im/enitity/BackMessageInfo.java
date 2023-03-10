package com.djb.im.enitity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("back_message_info")
public class BackMessageInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 消息
     */
    private Integer messageId;

    private Integer languageId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
}
