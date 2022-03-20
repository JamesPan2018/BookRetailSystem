package com.jpan.retail.service;

import com.jpan.retail.enums.MemberType;
import com.jpan.retail.exceptions.BookRetailException;
import com.jpan.retail.exceptions.ErrorCode;
import com.jpan.retail.repo.MemberRepository;
import com.jpan.retail.entity.Member;
import com.jpan.retail.entity.AOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class MemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Create a new member object and then save it into database.
     *
     * @param member input member object
     * @return Member
     */
    public Member createMember(Member member) {
        // received member object is null
        if (member == null) {
            LOGGER.error("Failed to create new Member because the received member is null, please check and retry.");
            throw new BookRetailException(ErrorCode.CANNOT_SAVE_NULL_CONTENT);
        }

        // the specific member is already existing in DB
        final long memberId = member.getId();
        Member memberFromDB = getMemberById(memberId);
        if (memberFromDB != null) {
            LOGGER.error(String.format("Failed to create new Member because it's already exists, Member ID: %s", memberId));
            throw new BookRetailException(ErrorCode.CANNOT_SAVE_EXISTING_CONTENT);
        }

        // executing save operation
        Member resMember = saveMember(member);
        LOGGER.info(String.format("This Member is saved successfully, Member ID: %s", resMember.getId()));
        return resMember;
    }

    public Member getMemberById(long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            return memberOptional.get();
        }
        LOGGER.info(String.format("Failed to get member from database, because this member is not existing, Member ID： %s", id));
        return null;
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {
        return saveMember(member);
    }

    /**
     * Get member type and order total price from order, calculate the new member point and update member point into db.
     * new member point = original member point + new member point calculated by the order
     *
     * @param order AOrder
     * @return Member
     */
    public Member updateMemberPoint(AOrder order) {
        final long memberId = order.getMemberId();
        final MemberType type = getMemberType(memberId);
        final BigDecimal memberPoint = calculateMemberPoint(type, order.getTotalPrice());

        Member member = getMemberById(memberId);
        if (member == null) {
            LOGGER.error(String.format("Failed to get member from database, because this member is not existing, Member ID： %s", memberId));
            throw new BookRetailException(ErrorCode.MEMBER_NOT_FOUND);
        }
        final BigDecimal originalPoint = member.getPoint();
        member.setPoint(originalPoint.add(memberPoint));
        return updateMember(member);
    }

    public MemberType getMemberTypebyId(long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (!memberOptional.isPresent()) {
            LOGGER.error(String.format("Failed to get member from database, because this member is not existing, Member ID： %s", memberId));
            throw new BookRetailException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return memberOptional.get().getMemberType();
    }

    /**
     * Calculate member point by member type and order total price
     * Need to calculate the member’s point. Here is formular
     * Gold member – Total price of order * 3
     * Silver member –Total price of order * 2
     * Coper member – Total price of order
     *
     * @param type        MemberType
     * @param totalPrices totalPrices
     * @return new member point
     */
    private BigDecimal calculateMemberPoint(MemberType type, BigDecimal totalPrices) {
        int multiplier = -1;
        switch (type) {
            case GOLD:
                multiplier = MemberType.GOLD.getMultiplier();
                break;
            case SILVER:
                multiplier = MemberType.SILVER.getMultiplier();
                break;
            case COPER:
                multiplier = MemberType.COPER.getMultiplier();
                break;
        }
        if (multiplier == -1) {
            LOGGER.error(String.format("The member type is invalid, type: %s", type));
            throw new BookRetailException(ErrorCode.MEMBER_INVALID_TYPE);
        }

        return totalPrices.multiply(BigDecimal.valueOf(multiplier));
    }

    private MemberType getMemberType(long memberId) {
        return getMemberTypebyId(memberId);
    }
}
