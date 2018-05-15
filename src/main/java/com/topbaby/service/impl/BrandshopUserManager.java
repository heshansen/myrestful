package com.topbaby.service.impl;

import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.brandshop.service.BrandshopUserService;
import org.esbuilder.business.rbac.model.User;
import org.esbuilder.business.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.timon.security.IAuthorizableUserManager;
import org.timon.security.IUser;
import org.timon.security.authc.IAuthenticationToken;

import java.util.Set;

/**
 * <p>shiro导购员信息获取服务类</p>
 * details：
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-22
 */
@Service
public class BrandshopUserManager implements IAuthorizableUserManager<User> {

    @Autowired
    private UserService userService;

    @Autowired
    private BrandshopUserService brandshopUserService;

    public User getUser(IAuthenticationToken authenticationToken) {
        String loginId = (String) authenticationToken.getPrincipal();
        BrandshopUser brandshopUser = brandshopUserService.getUserByLoginId(loginId);

        if (brandshopUser != null) {

            brandshopUser.setPermissions(userService.getUserPermissions(loginId));
            brandshopUser.setRoles(userService.getUserRoles(loginId));
        }

        return brandshopUser;
    }

    public IUser getUserByLoginId(String loginId) {
        User user = userService.getUserByLoginId(loginId);
        user.setRoles(userService.getUserRoles(loginId));
        user.setPermissions(userService.getUserPermissions(loginId));
        return user;
    }

    public Set<String> getUserPermissions(String loginId) {

        return userService.getUserPermissions(loginId);
    }

    public Set<String> getUserRoles(String loginId) {

        return userService.getUserRoles(loginId);
    }

}
