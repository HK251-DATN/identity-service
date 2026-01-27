package edu.hcmut.datn.identity_service.dto.request;

import edu.hcmut.datn.identity_service.dao.Group;

public class GroupRequest {
    private String groupName;
    
    private String description;
    
    private boolean isActive;
    
    public Group toEntity() {
        Group grp = new Group();
        
        grp.setActive(this.isActive);
        grp.setGroupName(this.groupName);
        grp.setDescription(this.description);
        
        return grp;
    }
}
