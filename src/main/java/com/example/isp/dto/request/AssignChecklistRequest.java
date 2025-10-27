package com.example.isp.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AssignChecklistRequest {
    private List<ChecklistItemRequest> checklists;
}

