package com.example.myrestaurant.application;

import com.example.myrestaurant.domain.user.domain.UserCommand;
import com.example.myrestaurant.domain.user.domain.UserInfo;
import com.example.myrestaurant.domain.user.service.UserReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * index 사용 테스트
 */

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserFacadeTest {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private UserReader userReader;

    private static List<String> userTokenList;

    @BeforeAll
    /**
     * 10000명의 user 를 넣어둔다.
     */
    void beforeAll() {

        userTokenList = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            UserCommand.UserRegisterCommand registerCommand = UserCommand.UserRegisterCommand.builder()
                    .email("test-user-email" + i)
                    .nickName("test-user-nickname" + i)
                    .password(String.valueOf(i))
                    .build();
            UserInfo.Main main = userFacade.registerUser(registerCommand);
            userTokenList.add(main.getUserToken());
        }
    }

    //token 을 index 로 설정했을 때 처리 속도를 비교한다.
    @Test
    public void indexSpeedTest() throws Exception {
        //given
        long start = System.currentTimeMillis();

        //when
        for (int i = 0; i < 100; i++) {
            userReader.getUserWithUserToken(userTokenList.get(i));
        }

        //then
        long end = System.currentTimeMillis();



        System.out.println(">>> 처리 시간 : " + (end-start));
    }

}