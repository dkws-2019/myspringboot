package com.liuchao.myspringboot.pay.payimpl;

import com.liuchao.myspringboot.pay.PayService;
import org.springframework.stereotype.Component;

@Component
public class YlPayServiceImpl implements PayService {
    private String name="YL";
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String payMethod() {
        return "银联支付";
    }
}
