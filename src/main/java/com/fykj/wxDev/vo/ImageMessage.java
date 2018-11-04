package com.fykj.wxDev.vo;

import com.fykj.wxDev.enumPackage.MsgTypeEnum;

/**
 * @Author: wujian
 * @Date: 2018/11/1 21:34
 */
public class ImageMessage extends CommentMessage {

    public ImageMessage() {
        this.MsgType = MsgTypeEnum.IMAGE.name();
    }

    @Override
    public void replyMsg() {

    }
}
