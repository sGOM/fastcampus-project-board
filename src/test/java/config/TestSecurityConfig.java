package config;

import com.fastcampus.projectboard.config.SecurityConfig;
import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

// test 전용 SecurityConfig
@Import(SecurityConfig.class)
public class TestSecurityConfig {

    // Controller test 나 Jpa test 에서 UserAccountRepository 문제를 없애줌
    @MockBean private UserAccountRepository userAccountRepository;

    // 가짜 계정 정보 생성
    // (Spring) 인증 관련 test 를 할때만 특정 주기에 맞춰 특정 주기에 실행하게 끔 Listener 를 통해 실행
    // - 해당 코드는 각 Test Method 가 실행되기 전에
    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "gomTest",
                "pw",
                "gom-test@mail.com",
                "gom-test",
                "test memo"
        )));
    }
}
