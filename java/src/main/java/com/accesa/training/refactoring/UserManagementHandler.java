package com.accesa.training.refactoring;

import java.util.Arrays;
import java.util.Collections;

/**
 * handle user acctions:
 * <p>
 * User is created => Notify admin
 * User is added to a group => notify admin & grup manager
 * User is removed to a group => notify group manager
 * User recieves a new role => notify user and group manager
 * User looses a role => notify user
 */

public class UserManagementHandler {

    private UserService userService;
    private UserNotificationService userNotificationService;

    public UserManagementHandler(UserService userService, UserNotificationService userNotificationService) {
        this.userService = userService;
        this.userNotificationService = userNotificationService;
    }

    public void handleUserAction(String resourceModified, String resourceId, String actionType) {
        if (resourceModified.equals("User") && actionType.equals("CREATED")) {
            String admin = userService.locateAdmin();
            userNotificationService.notifyUsers(Collections.singletonList(admin));
        } else if (resourceModified.equals("Group") && actionType.equals("ADDED_USER")) {
            String groupManager = userService.getGroupManager(resourceId);
            String admin = userService.locateAdmin();
            userNotificationService.notifyUsers(Arrays.asList(admin, groupManager));
        } else if (resourceModified.equals("Group") && actionType.equals("REMOVED_USER")) {
            String groupManager = userService.getGroupManager(resourceId);
            userNotificationService.notifyUsers(Collections.singletonList(groupManager));
        } else if (resourceModified.equals("User") && actionType.equals("NEW_ROLE")) {
            String groupManager = userService.getGroupManager(resourceId);
            userNotificationService.notifyUsers(Collections.singletonList(groupManager));
        } else if (resourceModified.equals("User") && actionType.equals("REMOVE_ROLE")) {
            userNotificationService.notifyUsers(Collections.singletonList(resourceId));
        } else {
            throw new RuntimeException("Unrecognized action");
        }
    }

}
