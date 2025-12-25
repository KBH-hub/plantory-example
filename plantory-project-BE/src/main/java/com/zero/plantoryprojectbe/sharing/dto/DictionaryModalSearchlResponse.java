package com.zero.plantoryprojectbe.sharing.dto;

import com.zero.plantoryprojectbe.global.plantoryEnum.ManageDemand;
import com.zero.plantoryprojectbe.global.plantoryEnum.ManageLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryModalSearchlResponse {
    private String plantName;

    private ManageLevel manageLevel;      // ENUM
    private String levelLabel;       // 라벨

    private ManageDemand manageDemand;       // ENUM
    private String demandLabel;      // 라벨

    private String fileUrl;
}
