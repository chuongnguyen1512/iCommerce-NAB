package com.icommerce.nab.entity.user;

import com.icommerce.nab.entity.BaseEntity;
import com.icommerce.nab.entity.order.Order;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class for mapping account object to database
 */
@Entity
@Table(name = "ICOMMERCE_ACCOUNT",
        indexes = {
                @Index(name = "INDEX_USER_NAME", columnList = "USER_NAME", unique = true)
        })
public class Account extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1497216428901486334L;

    @Column(name = "USER_NAME", nullable = false, unique = true, length = 100)
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL_ADDRESS", unique = true)
    private String emailAddress;

    @Column(name = "PHONE")
    private String phoneNum;

    @Column(name = "IS_CLOSED")
    private boolean isClosed;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "CLOSED_DATE")
    private Date closedDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
