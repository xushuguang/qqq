package com.qtec.snmp.pojo.po;

public class NERelation {
    private Long neid;

    private Long pairingId;

    private Long parentId;

    private Long distance;

    private String linkType;

    public Long getNeid() {
        return neid;
    }

    public void setNeid(Long neid) {
        this.neid = neid;
    }

    public Long getPairingId() {
        return pairingId;
    }

    public void setPairingId(Long pairingId) {
        this.pairingId = pairingId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType == null ? null : linkType.trim();
    }
}