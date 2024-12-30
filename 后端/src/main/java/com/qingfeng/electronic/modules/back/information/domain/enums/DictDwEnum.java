package com.qingfeng.electronic.modules.back.information.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.electronic.base.enums.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/2/26
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ActiveLevelEnum", description = "项目级别-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DictDwEnum implements BaseEnum {

    /**
     * 数据字典枚举大类
     */
    PROVINCE("省"),
    CERTIFICATESTYPE("证件类型"),
    EDUCATION("学历"),
    NATION("民族"),
    RELATIONSHIP("关系"),
    GENDER("性别"),
    MARITALSTATUS("婚姻状况"),
    NATIONALITY("国籍"),
    RELIGIOUSBELIEF("宗教信仰"),
    MEDICALINSURANCETYPE("医保类型"),
    CAREER("职业"),
    WHETHERORNOT("是否"),
    BLOODTYPE("血型"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;

    public static DictDwEnum match(String val, DictDwEnum def) {
        for (DictDwEnum enm : DictDwEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DictDwEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DictDwEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
