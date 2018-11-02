package com.fykj.wxDev.thread;

import com.fykj.wxDev.job.QuartzJobTemplate;
import com.fykj.wxDev.util.QuartzManager;
import org.springframework.stereotype.Component;

/**
 * @Author: wujian
 * @Date: 2018/11/2 23:43
 */
@Component
public class StartQuartzJob {
    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                while (flag){
                    QuartzManager.addJob("quartzJobTemplate", QuartzJobTemplate.class," 0 0/1 * * * ? ");
                    flag = false;
                }
            }
        }).start();
    }
}
