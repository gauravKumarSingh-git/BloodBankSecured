package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.repository.BloodGroupRepository;
import com.bnl.bloodbank.service.BloodGroupService;
import com.bnl.bloodbank.serviceImpl.BloodGroupServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class BloodGroupTest {

    @Mock
    BloodGroupRepository bloodGroupRepository;

    @InjectMocks
    BloodGroupService bloodGroupService = new BloodGroupServiceImpl();

    private static BloodGroup bloodGroup =
            BloodGroup.builder()
                    .bloodGroupId(1)
                    .bloodGroupName("AB-")
                    .quantity(11)
                    .build();

    /**
     * To check new blood group is successfully.
     * @throws AlreadyPresentException
     */
    @Test
    void validAddBloodGroup() throws AlreadyPresentException{
        Assertions.assertEquals("Blood Group " + bloodGroup.getBloodGroupName() + " successfully saved", bloodGroupService.addBloodGroup(bloodGroup));
    }

    /**
     * To check if getBloodGroup returns bloodGroup if bloodGroupId is valid
     * @throws NotPresentException
     */
    @Test
    void validGetBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findById(bloodGroup.getBloodGroupId())).thenReturn(Optional.of(bloodGroup));
        Assertions.assertEquals(bloodGroup, bloodGroupService.getBloodGroup(bloodGroup.getBloodGroupId()));
    }

    /**
     * To check getBloodGroup throws NotPresentException if bloodGroupId is not valid
     * @throws NotPresentException
     */
    @Test
    void invalidGetBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findById(bloodGroup.getBloodGroupId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> bloodGroupService.getBloodGroup(bloodGroup.getBloodGroupId())
        );
        Assertions.assertEquals("Blood Group with ID : " + bloodGroup.getBloodGroupId() + " not present", ex.getMessage());
    }

    /**
     * To check deleteBloodGroup is successful if bloodGroupId provided is valid
     * @throws NotPresentException
     */
    @Test
    void validDeleteBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findById(bloodGroup.getBloodGroupId())).thenReturn(Optional.of(bloodGroup));
        Assertions.assertEquals("Blood Group with ID : " + bloodGroup.getBloodGroupId() + " successfully deleted", bloodGroupService.deleteBloodGroup(bloodGroup.getBloodGroupId()));
    }

    /**
     * To check deleteBloodGroup throws NotPresentException if bloodGroupId provided is invalid
     * @throws NotPresentException
     */
    @Test
    void invalidDeleteBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findById(bloodGroup.getBloodGroupId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> bloodGroupService.deleteBloodGroup(bloodGroup.getBloodGroupId())
        );
        Assertions.assertEquals("Blood Group with ID : " + bloodGroup.getBloodGroupId() + " not present", ex.getMessage());
    }

    /**
     * To check update Blood group quantity is successful
     * @throws NotPresentException
     */
    @Test
    void validUpdateQuantity() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findByBloodGroupName(bloodGroup.getBloodGroupName())).thenReturn(Optional.of(bloodGroup));
        Assertions.assertEquals("Successfully updated quantity for Blood Group " + bloodGroup.getBloodGroupName(), bloodGroupService.updateQuantity(bloodGroup.getBloodGroupName(), bloodGroup.getQuantity()));
    }

    /**
     * To check getByBloodGroup returns bloodGroup details if bloodGroup provided is valid
     * @throws NotPresentException
     */
    @Test
    void validGetByBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findByBloodGroupName(bloodGroup.getBloodGroupName())).thenReturn(Optional.of(bloodGroup));
        Assertions.assertEquals(bloodGroup, bloodGroupService.getByBloodGroup(bloodGroup.getBloodGroupName()));
    }

    /**
     * To check getByBloodGroup throws NotPresentException if bloodGroup provided is invalid
     * @throws NotPresentException
     */
    @Test
    void invalidGetByBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findByBloodGroupName(bloodGroup.getBloodGroupName())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> bloodGroupService.getByBloodGroup(bloodGroup.getBloodGroupName())
        );
        Assertions.assertEquals("Blood Group " + bloodGroup.getBloodGroupName() + " not present", ex.getMessage());
    }
}
