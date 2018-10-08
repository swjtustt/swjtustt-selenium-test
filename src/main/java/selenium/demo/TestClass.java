package selenium.demo;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestClass {


    @BeforeMethod
    public void beforMethod(){
        System.out.println("这是方法执行之前");
    }

    @Test
    public void test1(){
        System.out.println("这个测试方法一");
    }
    @Test
    public void test2(){
        System.out.println("这是测试方法二");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("这是执行方法后");
    }
}
