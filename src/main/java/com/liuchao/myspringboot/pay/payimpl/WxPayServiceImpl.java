package com.liuchao.myspringboot.pay.payimpl;

import com.liuchao.myspringboot.pay.PayService;
import org.springframework.stereotype.Service;


@Service
public class WxPayServiceImpl implements PayService {
    private String name ="WX";
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String payMethod() {
        return "微信支付";
    }
}
