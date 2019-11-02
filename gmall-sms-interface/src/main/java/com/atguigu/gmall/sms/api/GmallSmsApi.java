package com.atguigu.gmall.sms.api;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.sms.vo.SaleVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by shkstart on 2019/11/2.
 */
public interface GmallSmsApi {

    @PostMapping("sms/skubounds/sale")
    public Resp<Object> Sale(@RequestBody() SaleVO saleVO) ;

}
