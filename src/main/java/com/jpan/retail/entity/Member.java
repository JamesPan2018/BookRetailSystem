package com.jpan.retail.entity;

import com.jpan.retail.enums.MemberType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Member object, it is data mapping with Table Member.
 * id: member id
 * name: member name
 * point: member point, calculated by member type and order total price
 * memberType: member type, including  GOLD(3), SILVER(2) and COPER(1);
 */
@Entity
public class Member implements Serializable {

    private static final long serialVersionUID = 5197327305482797331L;

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private BigDecimal point;
    private MemberType memberType;

    public Member() {
    }

    public Member(Builder builder) {
        this.name = builder.name;
        this.point = builder.point;
        this.memberType = builder.memberType;
    }

    public static class Builder {
        private String name;
        private BigDecimal point;
        private MemberType memberType;

        public Builder(String name, BigDecimal point, MemberType memberType) {
            this.name = name;
            this.point = point;
            this.memberType = memberType;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder point(BigDecimal point) {
            this.point = point;
            return this;
        }

        public Builder memberType(MemberType memberType) {
            this.memberType = memberType;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", point=" + point +
                ", memberType=" + memberType +
                '}';
    }
}
