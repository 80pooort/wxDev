package com.fykj.wxDev.thread;

import com.fykj.wxDev.job.QuartzJobTemplate;
import com.fykj.wxDev.util.QuartzManager;
import com.fykj.wxDev.vo.Setting;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: wujian
 * @Date: 2018/11/2 23:43
 */
@Component
public class StartQuartzJob implements ApplicationRunner {

    @Value("${jobSwitch.quartzJobTemplate}")
    private boolean quartzJobSwitch;

    @Override
    public void run(ApplicationArguments args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                while (flag){
                    if (quartzJobSwitch) {
                        QuartzManager.addJob("quartzJobTemplate", QuartzJobTemplate.class," 0 0/5 * * * ? ");
                    }
                    flag = false;
                }
            }
        }).start();
    }

}
