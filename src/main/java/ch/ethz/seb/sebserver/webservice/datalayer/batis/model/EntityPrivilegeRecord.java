package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class EntityPrivilegeRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source field: entity_privilege.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source field: entity_privilege.entity_type")
    private String entityType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source field: entity_privilege.entity_id")
    private Long entityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source field: entity_privilege.user_uuid")
    private String userUuid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source field: entity_privilege.privilege_type")
    private Byte privilegeType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source Table: entity_privilege")
    public EntityPrivilegeRecord(Long id, String entityType, Long entityId, String userUuid, Byte privilegeType) {
        this.id = id;
        this.entityType = entityType;
        this.entityId = entityId;
        this.userUuid = userUuid;
        this.privilegeType = privilegeType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source field: entity_privilege.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source field: entity_privilege.entity_type")
    public String getEntityType() {
        return entityType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source field: entity_privilege.entity_id")
    public Long getEntityId() {
        return entityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source field: entity_privilege.user_uuid")
    public String getUserUuid() {
        return userUuid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-04-18T08:15:10.178+02:00", comments="Source field: entity_privilege.privilege_type")
    public Byte getPrivilegeType() {
        return privilegeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entity_privilege
     *
     * @mbg.generated Thu Apr 18 08:15:10 CEST 2024
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", entityType=").append(entityType);
        sb.append(", entityId=").append(entityId);
        sb.append(", userUuid=").append(userUuid);
        sb.append(", privilegeType=").append(privilegeType);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entity_privilege
     *
     * @mbg.generated Thu Apr 18 08:15:10 CEST 2024
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
        EntityPrivilegeRecord other = (EntityPrivilegeRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEntityType() == null ? other.getEntityType() == null : this.getEntityType().equals(other.getEntityType()))
            && (this.getEntityId() == null ? other.getEntityId() == null : this.getEntityId().equals(other.getEntityId()))
            && (this.getUserUuid() == null ? other.getUserUuid() == null : this.getUserUuid().equals(other.getUserUuid()))
            && (this.getPrivilegeType() == null ? other.getPrivilegeType() == null : this.getPrivilegeType().equals(other.getPrivilegeType()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entity_privilege
     *
     * @mbg.generated Thu Apr 18 08:15:10 CEST 2024
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEntityType() == null) ? 0 : getEntityType().hashCode());
        result = prime * result + ((getEntityId() == null) ? 0 : getEntityId().hashCode());
        result = prime * result + ((getUserUuid() == null) ? 0 : getUserUuid().hashCode());
        result = prime * result + ((getPrivilegeType() == null) ? 0 : getPrivilegeType().hashCode());
        return result;
    }
}