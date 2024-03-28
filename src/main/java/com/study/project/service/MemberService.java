package com.study.project.service;

import com.study.project.domain.Member;
import com.study.project.repository.MemberRepository;
import com.study.project.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {


    private final MemberRepository memberRepository;
  //  @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입

    public Long join(Member member){
        //중복회원 안됨
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

    }

    //중복검증 메서드
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    //회원조회
    public List<Member> findMembers(){
       return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById((memberId));
    }
}
