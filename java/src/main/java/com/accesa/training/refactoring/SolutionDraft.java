package com.accesa.training.refactoring;

import java.util.Collections;
import java.util.HashMap;
import java.util.function.Consumer;

public class SolutionDraft {

    private final UserService userService;
    private final UserNotificationService userNotificationService;
    private final HashMap<ActionType, Consumer<UserAction>> actionHandlers;

    public SolutionDraft(UserService userService, UserNotificationService userNotificationService) {
        this.userService = userService;
        this.userNotificationService = userNotificationService;
        this.actionHandlers = new HashMap<>();
        this.actionHandlers.put(ActionType.CREATE_USER, this::handleCreateUser);
        //TODO add handlers
        this.actionHandlers.put(ActionType.DEFAULT, a -> {
            System.out.println("Action not supported");
        });
    }

    private void handleCreateUser(UserAction userAction) {
        String admin = userService.locateAdmin();
        userNotificationService.notifyUsers(Collections.singletonList(admin));
    }

    public void handleUserAction(String resourceModified, String resourceId, String actionType) {
        UserAction userAction = new UserAction(); // TODO construct user action
        this.actionHandlers.get(userAction.getActionType()).accept(userAction);
    }
}
