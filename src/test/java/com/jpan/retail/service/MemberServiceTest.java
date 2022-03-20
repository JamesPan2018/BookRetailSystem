package com.jpan.retail.service;

import com.jpan.retail.entity.Member;
import com.jpan.retail.enums.MemberType;
//import org.junit.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void testCreateMember() {
        // Create and save the first member object
        Member member = new Member();
        member.setName("MemberA");
        member.setPoint(BigDecimal.valueOf(12.23));
        member.setMemberType(MemberType.GOLD);
        Member result = memberService.createMember(member);
        Assert.assertNotNull("This specified member object should not be null.", result);
        long memberId = result.getId();
        Member memberFromDB = memberService.getMemberById(memberId);
        Assert.assertNotNull("This specified should exists into the DB.", memberFromDB);
        Assert.assertEquals("MemberA", memberFromDB.getName());
        Assert.assertEquals(BigDecimal.valueOf(12.23), memberFromDB.getPoint());
        Assert.assertEquals(MemberType.GOLD, memberFromDB.getMemberType());

        // Create the second member object
        member = new Member();
        member.setName("MemberB");
        member.setPoint(BigDecimal.valueOf(25.68));
        member.setMemberType(MemberType.SILVER);
        result = memberService.createMember(member);
        Assert.assertNotNull("This specified member object should not be null.", result);
        memberId = result.getId();
        memberFromDB = memberService.getMemberById(memberId);
        Assert.assertNotNull("This specified should exists into the DB.", memberFromDB);
        Assert.assertEquals("MemberB", memberFromDB.getName());
        Assert.assertEquals(BigDecimal.valueOf(25.68), memberFromDB.getPoint());
        Assert.assertEquals(MemberType.SILVER, memberFromDB.getMemberType());
    }


    @Test
    public void testGetMemberById() {
        Member member = new Member();
        member.setName("MemberC");
        member.setPoint(BigDecimal.valueOf(38.68));
        member.setMemberType(MemberType.COPER);
        Member result = memberService.createMember(member);
        Assert.assertNotNull("This specified member object should not be null.", result);

        long memberId = result.getId();
        Member memberFromDB = memberService.getMemberById(memberId);
        Assert.assertNotNull("This specified should exists into the DB.", memberFromDB);
        Assert.assertEquals("MemberC", memberFromDB.getName());
        Assert.assertEquals(BigDecimal.valueOf(38.68), memberFromDB.getPoint());
        Assert.assertEquals(MemberType.COPER, memberFromDB.getMemberType());
    }
}