package com.ghada.authentificationjwt.utilitiy;

import com.ghada.authentificationjwt.entities.UserEntity;
import com.ghada.authentificationjwt.model.UserDTO;

public class UserUtility {
    public static UserDTO convertEntityToDTO(UserEntity userEntity) {
        UserDTO userDTO =new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setLogin(userEntity.getLogin());
        userDTO.setSpaceId(userEntity.getSpaceId());
        return  userDTO;
    }
    public static UserEntity convertDTOtoEntity(UserDTO userDTO) {
        UserEntity userEntity =new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setLogin(userDTO.getLogin());
        userEntity.setSpaceId(userDTO.getSpaceId());
        return  userEntity;
    }
}
