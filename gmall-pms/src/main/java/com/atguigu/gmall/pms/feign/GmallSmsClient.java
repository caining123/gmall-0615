package com.atguigu.gmall.pms.feign;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.sms.vo.SaleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by shkstart on 2019/11/1.
 */
@FeignClient("sms-service")
public interface GmallSmsClient {

    @PostMapping("sms/skubounds/sale")
    public Resp<Object> saveSale(@RequestBody() SaleVO saleVO);
}
