package com.fykj.wxDev.factory;

import com.fykj.wxDev.enumPackage.MsgTypeEnum;
import com.fykj.wxDev.enumPackage.SelfExceptionEnum;
import com.fykj.wxDev.exception.SelfException;
import com.fykj.wxDev.vo.CommentMessage;
import com.fykj.wxDev.vo.ImageMessage;
import com.fykj.wxDev.vo.TextMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;


/**
 * @Author: wujian
 * @Date: 2018/11/1 21:41
 */
public class CreateMsgFactory {

    //根据传入类型生产对应实例
    public CommentMessage createMsg(String typeName) throws Exception {
        if (StringUtils.isEmpty(typeName)) {
            throw new SelfException(SelfExceptionEnum.INVALIDPARAM.name());
        }

        if (StringUtils.equals(MsgTypeEnum.TEXT.name(), typeName)) {
            return new TextMessage();
        }

        if (StringUtils.equals(MsgTypeEnum.IMAGE.name(), typeName)) {
            return new ImageMessage();
        }
        return null;

    }
}
