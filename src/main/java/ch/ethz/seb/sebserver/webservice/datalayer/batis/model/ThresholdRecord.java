package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import java.math.BigDecimal;
import javax.annotation.Generated;

public class ThresholdRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-04-23T15:30:54.831+02:00", comments="Source field: threshold.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-04-23T15:30:54.831+02:00", comments="Source field: threshold.indicator_id")
    private Long indicatorId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-04-23T15:30:54.831+02:00", comments="Source field: threshold.value")
    private BigDecimal value;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-04-23T15:30:54.831+02:00", comments="Source field: threshold.color")
    private String color;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-04-23T15:30:54.831+02:00", comments="Source Table: threshold")
    public ThresholdRecord(Long id, Long indicatorId, BigDecimal value, String color) {
        this.id = id;
        this.indicatorId = indicatorId;
        this.value = value;
        this.color = color;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-04-23T15:30:54.831+02:00", comments="Source field: threshold.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-04-23T15:30:54.831+02:00", comments="Source field: threshold.indicator_id")
    public Long getIndicatorId() {
        return indicatorId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-04-23T15:30:54.831+02:00", comments="Source field: threshold.value")
    public BigDecimal getValue() {
        return value;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-04-23T15:30:54.831+02:00", comments="Source field: threshold.color")
    public String getColor() {
        return color;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table threshold
     *
     * @mbg.generated Tue Apr 23 15:30:54 CEST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", indicatorId=").append(indicatorId);
        sb.append(", value=").append(value);
        sb.append(", color=").append(color);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table threshold
     *
     * @mbg.generated Tue Apr 23 15:30:54 CEST 2019
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
        ThresholdRecord other = (ThresholdRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIndicatorId() == null ? other.getIndicatorId() == null : this.getIndicatorId().equals(other.getIndicatorId()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
            && (this.getColor() == null ? other.getColor() == null : this.getColor().equals(other.getColor()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table threshold
     *
     * @mbg.generated Tue Apr 23 15:30:54 CEST 2019
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIndicatorId() == null) ? 0 : getIndicatorId().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getColor() == null) ? 0 : getColor().hashCode());
        return result;
    }
}