package com.ruoyi.system.dto;

import com.ruoyi.system.domain.DevEquipmentComponents;
import com.ruoyi.system.domain.DevEquipmentMaster;
import java.util.List;

public class CombinedAlertDto {
    private List<DevEquipmentMaster> lifecycleMasterAlerts;
    private List<DevEquipmentComponents> lifecycleComponentAlerts;
    private List<DevEquipmentMaster> warrantyMasterAlerts;
    private List<DevEquipmentComponents> warrantyComponentAlerts;

    // Getters and Setters
    public List<DevEquipmentMaster> getLifecycleMasterAlerts() { return lifecycleMasterAlerts; }
    public void setLifecycleMasterAlerts(List<DevEquipmentMaster> lifecycleMasterAlerts) { this.lifecycleMasterAlerts = lifecycleMasterAlerts; }
    public List<DevEquipmentComponents> getLifecycleComponentAlerts() { return lifecycleComponentAlerts; }
    public void setLifecycleComponentAlerts(List<DevEquipmentComponents> lifecycleComponentAlerts) { this.lifecycleComponentAlerts = lifecycleComponentAlerts; }
    public List<DevEquipmentMaster> getWarrantyMasterAlerts() { return warrantyMasterAlerts; }
    public void setWarrantyMasterAlerts(List<DevEquipmentMaster> warrantyMasterAlerts) { this.warrantyMasterAlerts = warrantyMasterAlerts; }
    public List<DevEquipmentComponents> getWarrantyComponentAlerts() { return warrantyComponentAlerts; }
    public void setComponentAlerts(List<DevEquipmentComponents> warrantyComponentAlerts) { this.warrantyComponentAlerts = warrantyComponentAlerts; }
}
