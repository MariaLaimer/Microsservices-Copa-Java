package br.edu.atitus.order_service.controllers;

import javax.naming.AuthenticationException;

public class UserRoleValidator {
    
    public static void requireAdmin(Integer userType) throws AuthenticationException {
        if (userType == null || userType != 0)
            throw new AuthenticationException("Acesso negado: apenas administradores tem permissão!");
    }

    public static void requireCommonOrAdmin(Integer userType) throws AuthenticationException {
        if (userType == null || (userType != 0 && userType != 1))
            throw new AuthenticationException("Acesso engado: usuário não autenticado.");
    }
    public static boolean isAdmin(Integer userType) {
        return userType != null && userType == 0;
    }
}
