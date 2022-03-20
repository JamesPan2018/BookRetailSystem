package com.jpan.retail.controller;


import com.jpan.retail.entity.Member;
import com.jpan.retail.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * Create member with three different member types (Gold, Silver, Coper)
     * @param member received member object
     * @return common response entity
     */
    @PostMapping("/create")
    public ResponseEntity<Member> createMember(@RequestBody @Valid Member member) {
        return new ResponseEntity<>(memberService.createMember(member), HttpStatus.OK);
    }

    /**
     * Get member by id
     * @param memberId member id
     * @return common response entity
     */
    @GetMapping("/getMember")
    public ResponseEntity<Member> getMemberById(@RequestParam Long memberId){
        return new ResponseEntity<>(memberService.getMemberById(memberId), HttpStatus.OK);
    }
}
