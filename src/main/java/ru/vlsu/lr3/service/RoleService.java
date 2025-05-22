package ru.vlsu.lr3.service;

import ru.vlsu.lr3.model.Role;
import ru.vlsu.lr3.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

  
    private final RoleRepository roleRepository;

    
  @Autowired
  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }
    @Transactional
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public Role getRole(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
