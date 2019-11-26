package com.soulmatefree.soulmate.practice;

import com.soulmatefree.soulmate.model.ZenMaster;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class OptionalPractice {

    public static void main(String[] args) {

        ZenMaster zenMaster = null;
        //flatMap  相当于将原先的多维改成一维 ，只要其中的哪一维出现问题，都会触发默认值
        String name = Optional.ofNullable(zenMaster)
                .flatMap(e->Optional.ofNullable(e.getName()))
                .filter(e-> e.equalsIgnoreCase("李四"))
                .orElse("default");
        System.out.println("name = " + name);


        ZenMaster zenMaster2 = null;

        //optional  if....else 的改写
        if(null != zenMaster2 ){
            if (!StringUtils.isEmpty(zenMaster2.getName())){
                zenMaster2.setName("王二");
            }else{
                zenMaster2.setName("凤凰");
            }
        }else{
            zenMaster2 = new ZenMaster();
            zenMaster2.setName("凤凰");
        }

        System.out.println("zenMaster2.getName() = " + zenMaster2.getName());

        ZenMaster zenMaster3 = new ZenMaster();

        Optional<ZenMaster> zenMasterOpt = Optional.ofNullable(zenMaster3)
                .filter(e -> !StringUtils.isEmpty(e.getName()));
        if (zenMasterOpt.isPresent()){
            zenMaster3.setName("王二");
        }else{
            zenMaster3 = new ZenMaster();
            zenMaster3.setName("凤凰");
        }

        System.out.println("zenMaster3.getName() = " + zenMaster3.getName());




    }


}
