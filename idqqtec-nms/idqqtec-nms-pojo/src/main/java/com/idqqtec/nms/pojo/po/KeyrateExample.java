package com.idqqtec.nms.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class KeyrateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public KeyrateExample() {
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

        public Criteria andQkdIpIsNull() {
            addCriterion("qkd_ip is null");
            return (Criteria) this;
        }

        public Criteria andQkdIpIsNotNull() {
            addCriterion("qkd_ip is not null");
            return (Criteria) this;
        }

        public Criteria andQkdIpEqualTo(String value) {
            addCriterion("qkd_ip =", value, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpNotEqualTo(String value) {
            addCriterion("qkd_ip <>", value, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpGreaterThan(String value) {
            addCriterion("qkd_ip >", value, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpGreaterThanOrEqualTo(String value) {
            addCriterion("qkd_ip >=", value, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpLessThan(String value) {
            addCriterion("qkd_ip <", value, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpLessThanOrEqualTo(String value) {
            addCriterion("qkd_ip <=", value, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpLike(String value) {
            addCriterion("qkd_ip like", value, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpNotLike(String value) {
            addCriterion("qkd_ip not like", value, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpIn(List<String> values) {
            addCriterion("qkd_ip in", values, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpNotIn(List<String> values) {
            addCriterion("qkd_ip not in", values, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpBetween(String value1, String value2) {
            addCriterion("qkd_ip between", value1, value2, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andQkdIpNotBetween(String value1, String value2) {
            addCriterion("qkd_ip not between", value1, value2, "qkdIp");
            return (Criteria) this;
        }

        public Criteria andKeyrateIsNull() {
            addCriterion("keyrate is null");
            return (Criteria) this;
        }

        public Criteria andKeyrateIsNotNull() {
            addCriterion("keyrate is not null");
            return (Criteria) this;
        }

        public Criteria andKeyrateEqualTo(String value) {
            addCriterion("keyrate =", value, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateNotEqualTo(String value) {
            addCriterion("keyrate <>", value, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateGreaterThan(String value) {
            addCriterion("keyrate >", value, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateGreaterThanOrEqualTo(String value) {
            addCriterion("keyrate >=", value, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateLessThan(String value) {
            addCriterion("keyrate <", value, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateLessThanOrEqualTo(String value) {
            addCriterion("keyrate <=", value, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateLike(String value) {
            addCriterion("keyrate like", value, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateNotLike(String value) {
            addCriterion("keyrate not like", value, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateIn(List<String> values) {
            addCriterion("keyrate in", values, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateNotIn(List<String> values) {
            addCriterion("keyrate not in", values, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateBetween(String value1, String value2) {
            addCriterion("keyrate between", value1, value2, "keyrate");
            return (Criteria) this;
        }

        public Criteria andKeyrateNotBetween(String value1, String value2) {
            addCriterion("keyrate not between", value1, value2, "keyrate");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(String value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(String value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(String value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(String value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(String value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(String value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLike(String value) {
            addCriterion("time like", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotLike(String value) {
            addCriterion("time not like", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<String> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<String> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(String value1, String value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(String value1, String value2) {
            addCriterion("time not between", value1, value2, "time");
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