package com.liuchao.myspringboot.pay.payimpl;

import com.liuchao.myspringboot.pay.PayService;
import org.springframework.stereotype.Component;

@Component
public class ZfbPayServiceImpl  implements PayService {
    private String name="ZFB";
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String payMethod() {
        return "支付宝支付";
    }
}
