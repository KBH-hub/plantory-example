package com.zero.plantoryprojectbe.profile.service;

import com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType;
import com.zero.plantoryprojectbe.image.ImageMapper;
import com.zero.plantoryprojectbe.profile.ProfileSharingInsertMapper;
import com.zero.plantoryprojectbe.profile.dto.ProfileInterestListRequest;
import com.zero.plantoryprojectbe.profile.dto.ProfileSharingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileInterestServiceImpl implements ProfileInterestService {

    private final ProfileSharingInsertMapper profileSharingInsertMapper;
    private final ImageMapper imageMapper;

    @Override
    public List<ProfileSharingResponse> getProfileInterest(ProfileInterestListRequest req) {

        List<ProfileSharingResponse> list =
                profileSharingInsertMapper.selectInterestSharingList(
                        req.getMemberId(),
                        req.getKeyword(),
                        req.getLimit(),
                        req.getOffset()
                );

        list.forEach(item -> {
            String thumbnail = imageMapper.selectSharingThumbnail(
                    ImageTargetType.SHARING,
                    item.getSharingId()
            );
            item.setThumbnail(thumbnail);
        });

        return list;
    }
}
