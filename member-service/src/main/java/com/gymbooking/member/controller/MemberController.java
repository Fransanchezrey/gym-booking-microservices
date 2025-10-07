package com.gymbooking.member.controller;

import com.gymbooking.member.entities.Member;
import com.gymbooking.member.exception.BusinessRuleException;
import com.gymbooking.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.findAll();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id) throws BusinessRuleException {
            Member member = memberService.getMemberById(id);
            return ResponseEntity.ok(member);
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member savedMember = memberService.save(member);
        return ResponseEntity.status(201).body(savedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) throws BusinessRuleException {
        memberService.deleteMemberById(id);
        return ResponseEntity.noContent().build();
    }
}
