package com.gymbooking.member.service;

import com.gymbooking.member.entities.Member;
import com.gymbooking.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.gymbooking.member.exception.BusinessRuleException;

import java.util.List;

@Service
public class MemberServiceImpl  implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Long id) throws BusinessRuleException {
        return memberRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("1002", "Member not found for id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public void deleteMemberById(Long id) throws BusinessRuleException {
        if (!memberRepository.existsById(id)) {
            throw new BusinessRuleException("1002", "Member not found for id: " + id, HttpStatus.NOT_FOUND);
        }
        memberRepository.deleteById(id);
    }
}
