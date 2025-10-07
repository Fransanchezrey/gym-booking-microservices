package com.gymbooking.member.service;

import com.gymbooking.member.entities.Member;
import com.gymbooking.member.exception.BusinessRuleException;

import java.util.List;

public interface MemberService {

    List<Member> findAll();

    Member getMemberById(Long id) throws BusinessRuleException;

    Member save(Member member);

    void deleteMemberById(Long id) throws BusinessRuleException;
}
