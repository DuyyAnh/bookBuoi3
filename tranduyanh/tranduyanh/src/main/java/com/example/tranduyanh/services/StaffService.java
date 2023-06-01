package com.example.tranduyanh.services;


import com.example.tranduyanh.entity.Staff;
import com.example.tranduyanh.repository.IStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    private IStaffRepository staffRepository;

    public List<Staff> getAllStaffs(){
        return staffRepository.findAll();
    }
    public Staff getStaffById(Long id) {
        Optional<Staff> optional = staffRepository.findById(id);
        return optional.orElse(null);
    }
    public void addStaff(Staff staff) {
        staffRepository.save(staff);
    }
    public void updateStaff(Staff staff){
        staffRepository.save(staff);
    }
    public void deleteStaff(Long id){
        staffRepository.deleteById(id);
    }
}
