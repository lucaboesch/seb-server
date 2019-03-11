package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class ConfigurationAttributeRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.332+01:00", comments="Source field: configuration_attribute.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.333+01:00", comments="Source field: configuration_attribute.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.334+01:00", comments="Source field: configuration_attribute.type")
    private String type;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.334+01:00", comments="Source field: configuration_attribute.parent_id")
    private Long parentId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.334+01:00", comments="Source field: configuration_attribute.resources")
    private String resources;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.334+01:00", comments="Source field: configuration_attribute.validator")
    private String validator;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.334+01:00", comments="Source field: configuration_attribute.dependencies")
    private String dependencies;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.335+01:00", comments="Source field: configuration_attribute.default_value")
    private String defaultValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.327+01:00", comments="Source Table: configuration_attribute")
    public ConfigurationAttributeRecord(Long id, String name, String type, Long parentId, String resources, String validator, String dependencies, String defaultValue) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.resources = resources;
        this.validator = validator;
        this.dependencies = dependencies;
        this.defaultValue = defaultValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.333+01:00", comments="Source field: configuration_attribute.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.333+01:00", comments="Source field: configuration_attribute.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.334+01:00", comments="Source field: configuration_attribute.type")
    public String getType() {
        return type;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.334+01:00", comments="Source field: configuration_attribute.parent_id")
    public Long getParentId() {
        return parentId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.334+01:00", comments="Source field: configuration_attribute.resources")
    public String getResources() {
        return resources;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.334+01:00", comments="Source field: configuration_attribute.validator")
    public String getValidator() {
        return validator;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.334+01:00", comments="Source field: configuration_attribute.dependencies")
    public String getDependencies() {
        return dependencies;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-03-11T09:59:52.335+01:00", comments="Source field: configuration_attribute.default_value")
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configuration_attribute
     *
     * @mbg.generated Mon Mar 11 09:59:52 CET 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", parentId=").append(parentId);
        sb.append(", resources=").append(resources);
        sb.append(", validator=").append(validator);
        sb.append(", dependencies=").append(dependencies);
        sb.append(", defaultValue=").append(defaultValue);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configuration_attribute
     *
     * @mbg.generated Mon Mar 11 09:59:52 CET 2019
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
        ConfigurationAttributeRecord other = (ConfigurationAttributeRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getResources() == null ? other.getResources() == null : this.getResources().equals(other.getResources()))
            && (this.getValidator() == null ? other.getValidator() == null : this.getValidator().equals(other.getValidator()))
            && (this.getDependencies() == null ? other.getDependencies() == null : this.getDependencies().equals(other.getDependencies()))
            && (this.getDefaultValue() == null ? other.getDefaultValue() == null : this.getDefaultValue().equals(other.getDefaultValue()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configuration_attribute
     *
     * @mbg.generated Mon Mar 11 09:59:52 CET 2019
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getResources() == null) ? 0 : getResources().hashCode());
        result = prime * result + ((getValidator() == null) ? 0 : getValidator().hashCode());
        result = prime * result + ((getDependencies() == null) ? 0 : getDependencies().hashCode());
        result = prime * result + ((getDefaultValue() == null) ? 0 : getDefaultValue().hashCode());
        return result;
    }
}