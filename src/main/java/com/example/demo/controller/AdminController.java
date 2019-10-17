package com.example.demo.controller;


import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminRepository adminRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminController(AdminRepository adminRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public  ResponseEntity<List<Admin>> get() {
        List<Admin> admins = adminRepository.findAll();
        return ResponseEntity.ok(admins);
    }


    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody Admin admin) {
        if(admin == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        System.out.println(admin);
        if(adminRepository.save(admin) != null){

            return ResponseEntity.status(HttpStatus.CREATED).build();
        };
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PutMapping
    public Admin put(@RequestBody Admin admin){
        return adminRepository.save(admin);
    }


    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Admin admin = adminRepository.getOne(id);

        if (admin == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        adminRepository.delete(admin);
        return ResponseEntity.ok(null);
    }

}
