package com.trainee.PostgreSQL.security.service.mapper;

import com.trainee.PostgreSQL.security.dto.UsuarioDTO;
import com.trainee.PostgreSQL.security.models.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "rol.nombreRol", target = "rol")
    public UsuarioDTO toUsuarioDTO(Usuario usuario);

    @Mapping(target = "authorities", ignore = true)
    public Usuario toUsuario(UsuarioDTO usuarioDTO);

}
