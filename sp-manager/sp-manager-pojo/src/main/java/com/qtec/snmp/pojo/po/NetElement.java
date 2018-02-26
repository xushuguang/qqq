package com.qtec.snmp.pojo.po;

public class NetElement {
    private Long id;

    private String neName;

    private String neIp;

    private String correspondingQncIp;

    private String paringQkdIp;

    private String type;

    private String belongGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNeName() {
        return neName;
    }

    public void setNeName(String neName) {
        this.neName = neName == null ? null : neName.trim();
    }

    public String getNeIp() {
        return neIp;
    }

    public void setNeIp(String neIp) {
        this.neIp = neIp == null ? null : neIp.trim();
    }

    public String getCorrespondingQncIp() {
        return correspondingQncIp;
    }

    public void setCorrespondingQncIp(String correspondingQncIp) {
        this.correspondingQncIp = correspondingQncIp == null ? null : correspondingQncIp.trim();
    }

    public String getParingQkdIp() {
        return paringQkdIp;
    }

    public void setParingQkdIp(String paringQkdIp) {
        this.paringQkdIp = paringQkdIp == null ? null : paringQkdIp.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getBelongGroup() {
        return belongGroup;
    }

    public void setBelongGroup(String belongGroup) {
        this.belongGroup = belongGroup == null ? null : belongGroup.trim();
    }
}