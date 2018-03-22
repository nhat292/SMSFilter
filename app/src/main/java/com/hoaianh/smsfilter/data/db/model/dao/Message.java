package com.hoaianh.smsfilter.data.db.model.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Nhat on 3/4/18.
 */

@Entity(nameInDb = "messages", indexes = {@Index(value = "date", unique = true)})
public class Message {

    public static final String TYPE_SEND = "Send";
    public static final String TYPE_RECEIVE = "Receive";


    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "address")
    private String address;

    @Property(nameInDb = "body")
    private String body;

    @Property(nameInDb = "type")
    private String type;

    @Property(nameInDb = "date")
    private String date;

    @Property(nameInDb = "is_sync")
    private Boolean isSync;

    @Generated(hash = 637306882)
    public Message() {
    }

    @Generated(hash = 1626873966)
    public Message(Long id, String address, String body, String type, String date, Boolean isSync) {
        this.id = id;
        this.address = address;
        this.body = body;
        this.type = type;
        this.date = date;
        this.isSync = isSync;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSync() {
        return isSync;
    }

    public void setSync(Boolean sync) {
        isSync = sync;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getIsSync() {
        return this.isSync;
    }

    public void setIsSync(Boolean isSync) {
        this.isSync = isSync;
    }
}
