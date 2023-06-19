package com.fonada.masking.token.service;

/* 
@Component
public class UserValidationServiceImpl implements UserValidationService {
  private static final Logger LOGGER = Logger.getLogger(UserValidationServiceImpl.class.getName());
    @Autowired
    private com.fonada.masking.service.UserService userService;

    @Override
    public boolean authenticatedRequest(Object request) {
        boolean authenticatedRequest = false;
        try {
            Users userEntity = userService.getLoggedInUser();
            long userIdFromBody = 0l;
            if (userEntity != null) {
                if (request instanceof Campaign) {
                    userIdFromBody = ((Campaign) (request)).getUserId();
                    authenticatedRequest = userIdFromBody == userEntity.getUserId();
                } else if (request instanceof IvrTree) {
                    userIdFromBody = ((IvrTree) (request)).getUserId();
                    authenticatedRequest = userIdFromBody == userEntity.getUserId();
                } else if (request instanceof LeadInfo) {
                    userIdFromBody = ((LeadInfo) (request)).getUserId();
                    authenticatedRequest = userIdFromBody == userEntity.getUserId();
                } else if (request instanceof IvrVoicePrompt) {
                    userIdFromBody = ((IvrVoicePrompt) (request)).getUserId();
                    authenticatedRequest = userIdFromBody == userEntity.getUserId();
                } else if (request instanceof PBLeadInfo) {
                    userIdFromBody = ((PBLeadInfo) (request)).getUserId();
                    authenticatedRequest = userIdFromBody == userEntity.getUserId();
                } else if (request instanceof LeadInfoTimes) {
                    userIdFromBody = ((LeadInfoTimes) (request)).getUserId();
                    authenticatedRequest = userIdFromBody == userEntity.getUserId();
                } else if (request instanceof CreditTxn) {
                    UserEntity actionUser = userService.getUserByName(((CreditTxn) (request)).getUserName());
                    if(actionUser != null) {
                        userIdFromBody = actionUser.getUserId();
                        authenticatedRequest = userIdFromBody == userEntity.getUserId();
                    }
                } else if  (request instanceof User) {
                    UserEntity actionUser = userService.getUserByName(((User) (request)).getUserName());
                    if(actionUser != null) {
                        userIdFromBody = actionUser.getUserId();
                        authenticatedRequest = userIdFromBody == userEntity.getUserId();
                    }
                } else if (request instanceof String) {
                    authenticatedRequest = userEntity.getEmail().equals(request);
                }
            }
            if (!authenticatedRequest && userService.isUserIdFromBodyChildOfLoggedInUser(userEntity.getUserId(), userIdFromBody)) {
                authenticatedRequest = true;
            }
        } catch (Exception e) {

        }
        return authenticatedRequest;
    }


}*/
