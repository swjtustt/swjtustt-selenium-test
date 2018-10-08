package selenium.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class MailRegister {

    private WebDriver driver;
    private static WebDriverWait wait;


    @BeforeMethod
    public void before(){
        System.setProperty("webdriver.chrome.driver","D:\\soft\\geckodriver\\chromedriver.exe");//这一步必不可少
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,30);
        driver.manage().window().maximize();
        driver.get("https://mail.163.com/");
    }

    @Test
    public void register(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("x-URS-iframe")));

        WebElement element = driver.findElement(By.id("x-URS-iframe"));
        driver.switchTo().frame(element);
        driver.findElement(By.id("changepage")).click();

        String handle = driver.getWindowHandle();
        for (String hand: driver.getWindowHandles()) {
            if(handle.equals(hand))continue;
            driver.switchTo().window(hand);
        }
        long time = System.currentTimeMillis();
        driver.findElement(By.id("nameIpt")).sendKeys("swjtustt"+time);
        driver.findElement(By.id("mainPwdIpt")).sendKeys("doubi123QQ");
        driver.findElement(By.id("mainCfmPwdIpt")).sendKeys("doubi123QQ");
        driver.findElement(By.id("mainMobileIpt")).sendKeys("14567890234");
        driver.findElement(By.id("vcodeIpt")).sendKeys("123124134");
        driver.findElement(By.id("mainAcodeIpt")).sendKeys("52325");
        String str = "  手机验证码不正确，请重新填写";
        Assert.assertEquals(str,"  手机验证码不正确，请重新填写");
    }

    private static void login(WebDriver driver,String email,String pwd){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("x-URS-iframe")));
        driver.switchTo().frame(driver.findElement(By.id("x-URS-iframe")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(pwd);
        driver.findElement(By.id("dologin")).click();
    }
    @Test
    public void loginSuccess(){
        MailRegister.login(driver,"swjtustt","swjtustt0226");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"_mail_component_39_39\"]/a")));
        String quit = driver.findElement(By.xpath("//*[@id=\"_mail_component_39_39\"]/a")).getText();
        System.out.println(quit);
        driver.findElement(By.xpath("//*[@id=\"_mail_component_39_39\"]/a")).click();
        //Assert.assertEquals(quit,"退出");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("js-relogin")));
        String quitText = driver.findElement(By.id("js-relogin")).getText();
        Assert.assertEquals(quitText,"重新登录");
    }

    @DataProvider(name="userlist")
    public Object[][] sendUser(){
        return new Object[][]{
                {"usertestone","one"},
                {"usertesttwo","two"},
                {"usertestthree","three"}
        };
    }


    @Test(dataProvider = "userlist")
    public void loginError(String name,String pwd){
        MailRegister.login(driver,name,pwd);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"nerror\"]/div[2]")));
        String warn = driver.findElement(By.xpath("//*[@id=\"nerror\"]/div[2]")).getText();
        System.out.println(warn);
        //*[@id="auto-id-1538188243607"]
        Assert.assertEquals( warn,"帐号或密码错误");
    }

    @Test
    public void sendMail(){
        MailRegister.login(driver,"swjtustt","swjtustt0226");
        driver.findElement(By.id("dologin")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"_mail_component_63_63\"]/span[2]")));
        String writeMail = driver.findElement(By.xpath("//*[@id=\"_mail_component_63_63\"]/span[2]")).getText();
        driver.findElement(By.xpath("//*[@id=\"_mail_component_63_63\"]/span[2]")).click();
        //Assert.assertEquals(writeMail,"写 信" );
        driver.findElement(By.className("nui-editableAddr-ipt")).sendKeys("swjtustt@163.com");

        driver.findElement(By.xpath("//input[contains(@id, '_subjectInput')]")).sendKeys("邮件的自我测试");
       // driver.findElement(By.xpath("//a[contains(text(), ’一次可发送2000张照片，600首MP3，一部高清电影’)])")).click();
        driver.findElement(By.xpath("//input[contains(@type, 'file')]")).sendKeys("E:\\简历\\微信图片_20180318100450.jpg");

        WebElement bodyFrame = driver.findElement(By.className("APP-editor-iframe"));
        driver.switchTo().frame(bodyFrame);

        driver.findElement(By.xpath("/html/body")).sendKeys("牙好痛，好烦躁！");

        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//*[text()='发送']")).click();
    }
}
