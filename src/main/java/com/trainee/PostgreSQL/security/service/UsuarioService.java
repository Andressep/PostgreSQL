package com.trainee.PostgreSQL.security.service;

import com.trainee.PostgreSQL.security.dto.UsuarioDTO;
import com.trainee.PostgreSQL.security.dto.UsuarioLoginDTO;

public interface UsuarioService  {
    public UsuarioDTO login(UsuarioLoginDTO usuarioLoginDTO);

    public UsuarioDTO crear(UsuarioDTO usuarioDTO) throws Exception;


}
