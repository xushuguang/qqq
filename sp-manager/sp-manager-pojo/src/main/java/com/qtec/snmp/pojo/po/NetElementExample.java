package com.qtec.snmp.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class NetElementExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NetElementExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNeNameIsNull() {
            addCriterion("ne_name is null");
            return (Criteria) this;
        }

        public Criteria andNeNameIsNotNull() {
            addCriterion("ne_name is not null");
            return (Criteria) this;
        }

        public Criteria andNeNameEqualTo(String value) {
            addCriterion("ne_name =", value, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameNotEqualTo(String value) {
            addCriterion("ne_name <>", value, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameGreaterThan(String value) {
            addCriterion("ne_name >", value, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameGreaterThanOrEqualTo(String value) {
            addCriterion("ne_name >=", value, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameLessThan(String value) {
            addCriterion("ne_name <", value, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameLessThanOrEqualTo(String value) {
            addCriterion("ne_name <=", value, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameLike(String value) {
            addCriterion("ne_name like", value, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameNotLike(String value) {
            addCriterion("ne_name not like", value, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameIn(List<String> values) {
            addCriterion("ne_name in", values, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameNotIn(List<String> values) {
            addCriterion("ne_name not in", values, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameBetween(String value1, String value2) {
            addCriterion("ne_name between", value1, value2, "neName");
            return (Criteria) this;
        }

        public Criteria andNeNameNotBetween(String value1, String value2) {
            addCriterion("ne_name not between", value1, value2, "neName");
            return (Criteria) this;
        }

        public Criteria andNeIpIsNull() {
            addCriterion("ne_ip is null");
            return (Criteria) this;
        }

        public Criteria andNeIpIsNotNull() {
            addCriterion("ne_ip is not null");
            return (Criteria) this;
        }

        public Criteria andNeIpEqualTo(String value) {
            addCriterion("ne_ip =", value, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpNotEqualTo(String value) {
            addCriterion("ne_ip <>", value, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpGreaterThan(String value) {
            addCriterion("ne_ip >", value, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpGreaterThanOrEqualTo(String value) {
            addCriterion("ne_ip >=", value, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpLessThan(String value) {
            addCriterion("ne_ip <", value, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpLessThanOrEqualTo(String value) {
            addCriterion("ne_ip <=", value, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpLike(String value) {
            addCriterion("ne_ip like", value, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpNotLike(String value) {
            addCriterion("ne_ip not like", value, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpIn(List<String> values) {
            addCriterion("ne_ip in", values, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpNotIn(List<String> values) {
            addCriterion("ne_ip not in", values, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpBetween(String value1, String value2) {
            addCriterion("ne_ip between", value1, value2, "neIp");
            return (Criteria) this;
        }

        public Criteria andNeIpNotBetween(String value1, String value2) {
            addCriterion("ne_ip not between", value1, value2, "neIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpIsNull() {
            addCriterion("corresponding_qnc_ip is null");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpIsNotNull() {
            addCriterion("corresponding_qnc_ip is not null");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpEqualTo(String value) {
            addCriterion("corresponding_qnc_ip =", value, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpNotEqualTo(String value) {
            addCriterion("corresponding_qnc_ip <>", value, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpGreaterThan(String value) {
            addCriterion("corresponding_qnc_ip >", value, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpGreaterThanOrEqualTo(String value) {
            addCriterion("corresponding_qnc_ip >=", value, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpLessThan(String value) {
            addCriterion("corresponding_qnc_ip <", value, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpLessThanOrEqualTo(String value) {
            addCriterion("corresponding_qnc_ip <=", value, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpLike(String value) {
            addCriterion("corresponding_qnc_ip like", value, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpNotLike(String value) {
            addCriterion("corresponding_qnc_ip not like", value, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpIn(List<String> values) {
            addCriterion("corresponding_qnc_ip in", values, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpNotIn(List<String> values) {
            addCriterion("corresponding_qnc_ip not in", values, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpBetween(String value1, String value2) {
            addCriterion("corresponding_qnc_ip between", value1, value2, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andCorrespondingQncIpNotBetween(String value1, String value2) {
            addCriterion("corresponding_qnc_ip not between", value1, value2, "correspondingQncIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpIsNull() {
            addCriterion("paring_qkd_ip is null");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpIsNotNull() {
            addCriterion("paring_qkd_ip is not null");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpEqualTo(String value) {
            addCriterion("paring_qkd_ip =", value, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpNotEqualTo(String value) {
            addCriterion("paring_qkd_ip <>", value, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpGreaterThan(String value) {
            addCriterion("paring_qkd_ip >", value, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpGreaterThanOrEqualTo(String value) {
            addCriterion("paring_qkd_ip >=", value, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpLessThan(String value) {
            addCriterion("paring_qkd_ip <", value, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpLessThanOrEqualTo(String value) {
            addCriterion("paring_qkd_ip <=", value, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpLike(String value) {
            addCriterion("paring_qkd_ip like", value, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpNotLike(String value) {
            addCriterion("paring_qkd_ip not like", value, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpIn(List<String> values) {
            addCriterion("paring_qkd_ip in", values, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpNotIn(List<String> values) {
            addCriterion("paring_qkd_ip not in", values, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpBetween(String value1, String value2) {
            addCriterion("paring_qkd_ip between", value1, value2, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andParingQkdIpNotBetween(String value1, String value2) {
            addCriterion("paring_qkd_ip not between", value1, value2, "paringQkdIp");
            return (Criteria) this;
        }

        public Criteria andBelongGroupIsNull() {
            addCriterion("belong_group is null");
            return (Criteria) this;
        }

        public Criteria andBelongGroupIsNotNull() {
            addCriterion("belong_group is not null");
            return (Criteria) this;
        }

        public Criteria andBelongGroupEqualTo(String value) {
            addCriterion("belong_group =", value, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupNotEqualTo(String value) {
            addCriterion("belong_group <>", value, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupGreaterThan(String value) {
            addCriterion("belong_group >", value, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupGreaterThanOrEqualTo(String value) {
            addCriterion("belong_group >=", value, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupLessThan(String value) {
            addCriterion("belong_group <", value, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupLessThanOrEqualTo(String value) {
            addCriterion("belong_group <=", value, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupLike(String value) {
            addCriterion("belong_group like", value, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupNotLike(String value) {
            addCriterion("belong_group not like", value, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupIn(List<String> values) {
            addCriterion("belong_group in", values, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupNotIn(List<String> values) {
            addCriterion("belong_group not in", values, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupBetween(String value1, String value2) {
            addCriterion("belong_group between", value1, value2, "belongGroup");
            return (Criteria) this;
        }

        public Criteria andBelongGroupNotBetween(String value1, String value2) {
            addCriterion("belong_group not between", value1, value2, "belongGroup");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}