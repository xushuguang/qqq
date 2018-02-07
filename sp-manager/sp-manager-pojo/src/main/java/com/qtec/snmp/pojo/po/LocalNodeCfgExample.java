package com.qtec.snmp.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class LocalNodeCfgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LocalNodeCfgExample() {
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

        public Criteria andLocalIpIsNull() {
            addCriterion("local_ip is null");
            return (Criteria) this;
        }

        public Criteria andLocalIpIsNotNull() {
            addCriterion("local_ip is not null");
            return (Criteria) this;
        }

        public Criteria andLocalIpEqualTo(String value) {
            addCriterion("local_ip =", value, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpNotEqualTo(String value) {
            addCriterion("local_ip <>", value, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpGreaterThan(String value) {
            addCriterion("local_ip >", value, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpGreaterThanOrEqualTo(String value) {
            addCriterion("local_ip >=", value, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpLessThan(String value) {
            addCriterion("local_ip <", value, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpLessThanOrEqualTo(String value) {
            addCriterion("local_ip <=", value, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpLike(String value) {
            addCriterion("local_ip like", value, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpNotLike(String value) {
            addCriterion("local_ip not like", value, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpIn(List<String> values) {
            addCriterion("local_ip in", values, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpNotIn(List<String> values) {
            addCriterion("local_ip not in", values, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpBetween(String value1, String value2) {
            addCriterion("local_ip between", value1, value2, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalIpNotBetween(String value1, String value2) {
            addCriterion("local_ip not between", value1, value2, "localIp");
            return (Criteria) this;
        }

        public Criteria andLocalPortIsNull() {
            addCriterion("local_port is null");
            return (Criteria) this;
        }

        public Criteria andLocalPortIsNotNull() {
            addCriterion("local_port is not null");
            return (Criteria) this;
        }

        public Criteria andLocalPortEqualTo(String value) {
            addCriterion("local_port =", value, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortNotEqualTo(String value) {
            addCriterion("local_port <>", value, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortGreaterThan(String value) {
            addCriterion("local_port >", value, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortGreaterThanOrEqualTo(String value) {
            addCriterion("local_port >=", value, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortLessThan(String value) {
            addCriterion("local_port <", value, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortLessThanOrEqualTo(String value) {
            addCriterion("local_port <=", value, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortLike(String value) {
            addCriterion("local_port like", value, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortNotLike(String value) {
            addCriterion("local_port not like", value, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortIn(List<String> values) {
            addCriterion("local_port in", values, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortNotIn(List<String> values) {
            addCriterion("local_port not in", values, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortBetween(String value1, String value2) {
            addCriterion("local_port between", value1, value2, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalPortNotBetween(String value1, String value2) {
            addCriterion("local_port not between", value1, value2, "localPort");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdIsNull() {
            addCriterion("local_node_id is null");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdIsNotNull() {
            addCriterion("local_node_id is not null");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdEqualTo(String value) {
            addCriterion("local_node_id =", value, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdNotEqualTo(String value) {
            addCriterion("local_node_id <>", value, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdGreaterThan(String value) {
            addCriterion("local_node_id >", value, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdGreaterThanOrEqualTo(String value) {
            addCriterion("local_node_id >=", value, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdLessThan(String value) {
            addCriterion("local_node_id <", value, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdLessThanOrEqualTo(String value) {
            addCriterion("local_node_id <=", value, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdLike(String value) {
            addCriterion("local_node_id like", value, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdNotLike(String value) {
            addCriterion("local_node_id not like", value, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdIn(List<String> values) {
            addCriterion("local_node_id in", values, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdNotIn(List<String> values) {
            addCriterion("local_node_id not in", values, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdBetween(String value1, String value2) {
            addCriterion("local_node_id between", value1, value2, "localNodeId");
            return (Criteria) this;
        }

        public Criteria andLocalNodeIdNotBetween(String value1, String value2) {
            addCriterion("local_node_id not between", value1, value2, "localNodeId");
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