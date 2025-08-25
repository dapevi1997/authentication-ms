package co.com.crediya.usecase.getallroles;

import co.com.crediya.model.role.Role;
import co.com.crediya.model.role.gateways.RoleRepository;
import reactor.core.publisher.Flux;

public class GetAllRolesUseCase {
    private final RoleRepository roleRepository;

    public GetAllRolesUseCase(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Flux<Role> getAllRoles(){
        return roleRepository.getAllRoles();
    }


}
