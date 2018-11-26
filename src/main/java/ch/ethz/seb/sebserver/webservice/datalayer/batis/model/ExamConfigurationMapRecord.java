package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class ExamConfigurationMapRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-26T12:04:08.179+01:00", comments="Source field: exam_configuration_map.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-26T12:04:08.179+01:00", comments="Source field: exam_configuration_map.exam_id")
    private Long examId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-26T12:04:08.179+01:00", comments="Source field: exam_configuration_map.configuration_node_id")
    private Long configurationNodeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-26T12:04:08.179+01:00", comments="Source field: exam_configuration_map.user_names")
    private String userNames;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-26T12:04:08.179+01:00", comments="Source Table: exam_configuration_map")
    public ExamConfigurationMapRecord(Long id, Long examId, Long configurationNodeId, String userNames) {
        this.id = id;
        this.examId = examId;
        this.configurationNodeId = configurationNodeId;
        this.userNames = userNames;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-26T12:04:08.179+01:00", comments="Source field: exam_configuration_map.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-26T12:04:08.179+01:00", comments="Source field: exam_configuration_map.exam_id")
    public Long getExamId() {
        return examId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-26T12:04:08.179+01:00", comments="Source field: exam_configuration_map.configuration_node_id")
    public Long getConfigurationNodeId() {
        return configurationNodeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-26T12:04:08.179+01:00", comments="Source field: exam_configuration_map.user_names")
    public String getUserNames() {
        return userNames;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_configuration_map
     *
     * @mbg.generated Mon Nov 26 12:04:08 CET 2018
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", examId=").append(examId);
        sb.append(", configurationNodeId=").append(configurationNodeId);
        sb.append(", userNames=").append(userNames);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_configuration_map
     *
     * @mbg.generated Mon Nov 26 12:04:08 CET 2018
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
        ExamConfigurationMapRecord other = (ExamConfigurationMapRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getConfigurationNodeId() == null ? other.getConfigurationNodeId() == null : this.getConfigurationNodeId().equals(other.getConfigurationNodeId()))
            && (this.getUserNames() == null ? other.getUserNames() == null : this.getUserNames().equals(other.getUserNames()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_configuration_map
     *
     * @mbg.generated Mon Nov 26 12:04:08 CET 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getExamId() == null) ? 0 : getExamId().hashCode());
        result = prime * result + ((getConfigurationNodeId() == null) ? 0 : getConfigurationNodeId().hashCode());
        result = prime * result + ((getUserNames() == null) ? 0 : getUserNames().hashCode());
        return result;
    }
}