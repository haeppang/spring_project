package com.study.project.service;

import com.study.project.domain.Member;
import com.study.project.repository.MemberRepository;
import com.study.project.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest //스프링 컨테이너와 테스트를 실행한다
@Transactional /*테스트 케이스에 이 이노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고,
     테스트를 완료후에 항상 롤백한다. 이렇게 하면 db데이터가 남지않아 다음 테스트에 영향없음*/
class MemberServiceIntegrationTest {


    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given 주어짐
        Member member = new Member();
        member.setName("hello");

        //when 실행됐을떄
        Long savedId = memberService.join(member);

        //then 결과가 나옴
        Member findMember = memberService.findOne(savedId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());


    }

    @Test
    public void 중복회원예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member1));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


    }
}
