package com.xiexu.reggie.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class})
@ResponseBody//的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) //进行异常处理方法
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        if(ex.getMessage().contains("Duplicate entry") ){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return  R.error(msg);
        }

        return R.error("未知错误");
    }

    //进行异常处理方法
    @ExceptionHandler(CustomException.class) //进行异常处理方法
    public R<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());

        return R.error(ex.getMessage());
    }



}
//public class GlobalExceptionHandler {
//
//    //进行异常处理方法
//    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
//        log.error(ex.getMessage());
//
//        if(ex.getMessage().contains("Duplicate entry")){
//            String[] split = ex.getMessage().split(" ");
//            String msg=split[2]+"已存在";
//            return R.error(msg);
//        }
//
//        return R.error("未知错误");
//    }
//}