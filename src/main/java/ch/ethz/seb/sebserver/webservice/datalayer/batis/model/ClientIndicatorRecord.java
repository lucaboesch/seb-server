package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import java.math.BigDecimal;
import javax.annotation.Generated;

public class ClientIndicatorRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source field: client_indicator.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source field: client_indicator.client_connection_id")
    private Long clientConnectionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source field: client_indicator.type")
    private Integer type;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source field: client_indicator.value")
    private Long value;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.497+01:00", comments="Source field: client_indicator.dec_value")
    private BigDecimal decValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source Table: client_indicator")
    public ClientIndicatorRecord(Long id, Long clientConnectionId, Integer type, Long value, BigDecimal decValue) {
        this.id = id;
        this.clientConnectionId = clientConnectionId;
        this.type = type;
        this.value = value;
        this.decValue = decValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source Table: client_indicator")
    public ClientIndicatorRecord() {
        super();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source field: client_indicator.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source field: client_indicator.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source field: client_indicator.client_connection_id")
    public Long getClientConnectionId() {
        return clientConnectionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source field: client_indicator.client_connection_id")
    public void setClientConnectionId(Long clientConnectionId) {
        this.clientConnectionId = clientConnectionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source field: client_indicator.type")
    public Integer getType() {
        return type;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.496+01:00", comments="Source field: client_indicator.type")
    public void setType(Integer type) {
        this.type = type;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.497+01:00", comments="Source field: client_indicator.value")
    public Long getValue() {
        return value;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.497+01:00", comments="Source field: client_indicator.value")
    public void setValue(Long value) {
        this.value = value;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.497+01:00", comments="Source field: client_indicator.dec_value")
    public BigDecimal getDecValue() {
        return decValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-06T16:00:09.497+01:00", comments="Source field: client_indicator.dec_value")
    public void setDecValue(BigDecimal decValue) {
        this.decValue = decValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_indicator
     *
     * @mbg.generated Mon Dec 06 16:00:09 CET 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clientConnectionId=").append(clientConnectionId);
        sb.append(", type=").append(type);
        sb.append(", value=").append(value);
        sb.append(", decValue=").append(decValue);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_indicator
     *
     * @mbg.generated Mon Dec 06 16:00:09 CET 2021
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ClientIndicatorRecord other = (ClientIndicatorRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getClientConnectionId() == null ? other.getClientConnectionId() == null : this.getClientConnectionId().equals(other.getClientConnectionId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
            && (this.getDecValue() == null ? other.getDecValue() == null : this.getDecValue().equals(other.getDecValue()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_indicator
     *
     * @mbg.generated Mon Dec 06 16:00:09 CET 2021
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getClientConnectionId() == null) ? 0 : getClientConnectionId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getDecValue() == null) ? 0 : getDecValue().hashCode());
        return result;
    }
}