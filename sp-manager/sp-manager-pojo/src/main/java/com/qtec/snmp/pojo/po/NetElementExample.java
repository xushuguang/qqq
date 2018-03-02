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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andLinkageIsNull() {
            addCriterion("linkage is null");
            return (Criteria) this;
        }

        public Criteria andLinkageIsNotNull() {
            addCriterion("linkage is not null");
            return (Criteria) this;
        }

        public Criteria andLinkageEqualTo(Long value) {
            addCriterion("linkage =", value, "linkage");
            return (Criteria) this;
        }

        public Criteria andLinkageNotEqualTo(Long value) {
            addCriterion("linkage <>", value, "linkage");
            return (Criteria) this;
        }

        public Criteria andLinkageGreaterThan(Long value) {
            addCriterion("linkage >", value, "linkage");
            return (Criteria) this;
        }

        public Criteria andLinkageGreaterThanOrEqualTo(Long value) {
            addCriterion("linkage >=", value, "linkage");
            return (Criteria) this;
        }

        public Criteria andLinkageLessThan(Long value) {
            addCriterion("linkage <", value, "linkage");
            return (Criteria) this;
        }

        public Criteria andLinkageLessThanOrEqualTo(Long value) {
            addCriterion("linkage <=", value, "linkage");
            return (Criteria) this;
        }

        public Criteria andLinkageIn(List<Long> values) {
            addCriterion("linkage in", values, "linkage");
            return (Criteria) this;
        }

        public Criteria andLinkageNotIn(List<Long> values) {
            addCriterion("linkage not in", values, "linkage");
            return (Criteria) this;
        }

        public Criteria andLinkageBetween(Long value1, Long value2) {
            addCriterion("linkage between", value1, value2, "linkage");
            return (Criteria) this;
        }

        public Criteria andLinkageNotBetween(Long value1, Long value2) {
            addCriterion("linkage not between", value1, value2, "linkage");
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