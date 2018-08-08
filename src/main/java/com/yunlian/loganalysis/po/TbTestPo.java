package com.yunlian.loganalysis.po;

import java.math.BigDecimal;

/**
 * @author qiang.wen
 * @date 2018/8/8 14:12
 */
public class TbTestPo {

    private Integer id;

    private String name;

    private BigDecimal money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
