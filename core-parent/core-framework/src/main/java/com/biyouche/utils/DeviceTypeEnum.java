package com.biyouche.utils;

/**
 * 设备类型枚举类
 */
public enum DeviceTypeEnum {
    WEIXIN(0,"weixin"),
    ANDROID(1,"android"),
    IOS(2,"ios");

    public int KEY = 0;
    public String REMARK = "" ;

    DeviceTypeEnum(int key,String remark){
        KEY = key ;
        REMARK = remark ;
    }

    public static DeviceTypeEnum getEnumType(int t){
        for (DeviceTypeEnum enums : DeviceTypeEnum.values()) {
            if(enums.KEY==t){
                return enums;
            }
        }
        return WEIXIN;

    }

    public static DeviceTypeEnum validator(String key){
        for (DeviceTypeEnum enums : DeviceTypeEnum.values()) {
            if(enums.REMARK.equals(key.toLowerCase())){
                return enums;
            }
        }
        return null;
    }

    public static boolean isApp(int key){
        if(ANDROID.KEY==key){
            return true;
        }
        if(IOS.KEY==key){
            return true;
        }
        return false;
    }
}
