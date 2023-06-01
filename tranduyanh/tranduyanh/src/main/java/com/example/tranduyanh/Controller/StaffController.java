package com.example.tranduyanh.Controller;

import com.example.tranduyanh.entity.Staff;
import com.example.tranduyanh.services.CategoryService;
import com.example.tranduyanh.services.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/staffs")
    public String showAllBooks(Model model){
        List<Staff> staffs = staffService.getAllStaffs();
        model.addAttribute("staffs", staffs);
        return "staff/list";
    }

    @GetMapping("/staffs/add")
    public String addStaffForm(Model model){
        model.addAttribute("staff",new Staff());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "staff/add";
    }
    @PostMapping("/staffs//add")
    public String addStaff(@ModelAttribute("staff") Staff staff){
        staffService.addStaff(staff);
        return "redirect:/staffs";
    }
    @GetMapping("staffs//edit/{id}")
    public String editStaffForm(@PathVariable("id") Long id, Model model) {
        Staff editStaff = staffService.getStaffById(id);
        if (editStaff != null) {
            model.addAttribute("staff", editStaff);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "staff/edit";
        } else {
            return "redirect:/staffs";
        }
    }
    @PostMapping("staffs/edit/{id}")
    public String editStaff(@PathVariable("id") Long id, @ModelAttribute("staff") @Valid Staff  editStaff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "staff/edit";
        } else {
            Staff existingStaff = staffService.getStaffById(id);
            if (existingStaff != null) {
                existingStaff.setName(editStaff.getName());
                existingStaff.setPlace(editStaff.getPlace());
                existingStaff.setLuong(editStaff.getLuong());
                existingStaff.setSex(editStaff.getSex());
                existingStaff.setImage(editStaff.getImage());
                existingStaff.setCategory(editStaff.getCategory());
                staffService.updateStaff(existingStaff); // Lưu thay đổi vào cơ sở dữ liệu
            }
            return "redirect:/staffs";
        }
    }
    @GetMapping("staffs/delete/{id}")
    public String deleteStaff(@PathVariable("id") Long id) {
        Staff staff = staffService.getStaffById(id);
        if (staff != null) {
            staffService.deleteStaff(id);
        }
        return "redirect:/staffs";
    }
    @GetMapping("staffs/search")
    public String search(@RequestParam("searchText") String searchText,Model model) {
        List<Staff> staffs = staffService.getAllStaffs();
        List<Staff> filteredStaffs = new ArrayList<>();

        if (searchText != null && !searchText.isEmpty()) {
            filteredStaffs = staffs.stream()
                    .filter(staff -> staff.getName().contains(searchText))
                    .collect(Collectors.toList());
        }
        model.addAttribute("staffs", filteredStaffs);
        return "staff/list";
    }
}

