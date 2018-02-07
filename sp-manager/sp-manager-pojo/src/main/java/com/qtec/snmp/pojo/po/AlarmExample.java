package com.qtec.snmp.pojo.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlarmExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AlarmExample() {
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

        public Criteria andTypeIdIsNull() {
            addCriterion("type_id is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(Integer value) {
            addCriterion("type_id =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(Integer value) {
            addCriterion("type_id <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(Integer value) {
            addCriterion("type_id >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("type_id >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(Integer value) {
            addCriterion("type_id <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("type_id <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<Integer> values) {
            addCriterion("type_id in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<Integer> values) {
            addCriterion("type_id not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("type_id between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("type_id not between", value1, value2, "typeId");
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

        public Criteria andQkdRuntimeIsNull() {
            addCriterion("qkd_runtime is null");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeIsNotNull() {
            addCriterion("qkd_runtime is not null");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeEqualTo(String value) {
            addCriterion("qkd_runtime =", value, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeNotEqualTo(String value) {
            addCriterion("qkd_runtime <>", value, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeGreaterThan(String value) {
            addCriterion("qkd_runtime >", value, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeGreaterThanOrEqualTo(String value) {
            addCriterion("qkd_runtime >=", value, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeLessThan(String value) {
            addCriterion("qkd_runtime <", value, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeLessThanOrEqualTo(String value) {
            addCriterion("qkd_runtime <=", value, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeLike(String value) {
            addCriterion("qkd_runtime like", value, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeNotLike(String value) {
            addCriterion("qkd_runtime not like", value, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeIn(List<String> values) {
            addCriterion("qkd_runtime in", values, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeNotIn(List<String> values) {
            addCriterion("qkd_runtime not in", values, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeBetween(String value1, String value2) {
            addCriterion("qkd_runtime between", value1, value2, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andQkdRuntimeNotBetween(String value1, String value2) {
            addCriterion("qkd_runtime not between", value1, value2, "qkdRuntime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeIsNull() {
            addCriterion("alarm_time is null");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeIsNotNull() {
            addCriterion("alarm_time is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeEqualTo(Date value) {
            addCriterion("alarm_time =", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeNotEqualTo(Date value) {
            addCriterion("alarm_time <>", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeGreaterThan(Date value) {
            addCriterion("alarm_time >", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("alarm_time >=", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeLessThan(Date value) {
            addCriterion("alarm_time <", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeLessThanOrEqualTo(Date value) {
            addCriterion("alarm_time <=", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeIn(List<Date> values) {
            addCriterion("alarm_time in", values, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeNotIn(List<Date> values) {
            addCriterion("alarm_time not in", values, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeBetween(Date value1, Date value2) {
            addCriterion("alarm_time between", value1, value2, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeNotBetween(Date value1, Date value2) {
            addCriterion("alarm_time not between", value1, value2, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmAckIsNull() {
            addCriterion("alarm_ack is null");
            return (Criteria) this;
        }

        public Criteria andAlarmAckIsNotNull() {
            addCriterion("alarm_ack is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmAckEqualTo(String value) {
            addCriterion("alarm_ack =", value, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckNotEqualTo(String value) {
            addCriterion("alarm_ack <>", value, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckGreaterThan(String value) {
            addCriterion("alarm_ack >", value, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckGreaterThanOrEqualTo(String value) {
            addCriterion("alarm_ack >=", value, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckLessThan(String value) {
            addCriterion("alarm_ack <", value, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckLessThanOrEqualTo(String value) {
            addCriterion("alarm_ack <=", value, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckLike(String value) {
            addCriterion("alarm_ack like", value, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckNotLike(String value) {
            addCriterion("alarm_ack not like", value, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckIn(List<String> values) {
            addCriterion("alarm_ack in", values, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckNotIn(List<String> values) {
            addCriterion("alarm_ack not in", values, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckBetween(String value1, String value2) {
            addCriterion("alarm_ack between", value1, value2, "alarmAck");
            return (Criteria) this;
        }

        public Criteria andAlarmAckNotBetween(String value1, String value2) {
            addCriterion("alarm_ack not between", value1, value2, "alarmAck");
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