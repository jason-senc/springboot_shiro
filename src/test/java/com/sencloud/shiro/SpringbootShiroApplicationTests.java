package com.sencloud.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootShiroApplicationTests {

    @Test
    public void contextLoads() {

        String hashname = "md5";
        String pwd = "1234";
        SimpleHash simpleHash = new SimpleHash(hashname, pwd, null, 2);
        System.out.println(simpleHash);
    }


}
