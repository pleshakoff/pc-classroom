package com.parcom.classroom.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService  {

    private final AuthenticationManager authenticationManager;

    private final
    PasswordEncoder passwordEncoder;


    private final UserRepository userRepository;

    public UsersService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


//    @Transactional
//    public ResponseEntity<KlfUsersResource> registerUser(KlfUsersRegPod pod, BindingResult bindingResult) throws BindException, SQLException, WrongProcedureFieldException, EntityNotFoundException {
//        if (bindingResult.hasErrors()) {throw new BindException(bindingResult);}
//        pod.setEncodedPass(passwordEncoder.encode(pod.getPassword()));
//
//        HashMap<String, Object> outMap = userRepository.executeSP("insKlfUsers", pod.getProcParamsMap(null));
//        Long id = (Long)outMap.get("p_id");
//        KlfUsersEntity entity = userRepository.findOne(id);
//        if (entity == null) {
//            throw new EntityNotFoundException(messageEntityNotFound,id);
//        }
//
//        OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(entity);
//        applicationEventPublisher.publishEvent(event);
//        return new ResponseEntity<>(klfUsersAssembler.toResource(entity), HttpStatus.OK);
//    }
//
//    @Transactional(readOnly = true)
//    public ResponseEntity<KlfUsersResource> getCurrentUser() throws EntityNotFoundException {
//        KlfUsersEntity entity = getCurrentUserEntity(messageEntityNotFound);
//        return new ResponseEntity<>(klfUsersAssembler.toResource(entity), HttpStatus.OK);
//    }
//
//    private KlfUsersEntity getCurrentUserEntity(String messageEntityNotFound) throws EntityNotFoundException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof String && principal.equals("anonymousUser")) {
//            throw new MrAccessDeniedException();
//        }
//        KlfUsersEntity entity = userRepository.findUserByName(((UserDetails)principal).getUsername());
//        if (entity == null) {
//            throw new EntityNotFoundException(messageEntityNotFound,null);
//        }
//        return entity;
//    }
//
//
//    @Transactional(readOnly = true)
//    public TokenResource authenticate(KlfUsersAuthPod pod,
//                                      BindingResult bindingResult) throws BindException, SQLException, WrongProcedureFieldException, EntityNotFoundException {
//        if (bindingResult.hasErrors()) {
//            throw new BindException(bindingResult);
//        }
//        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(pod.getUsername(), pod.getPassword());
//        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetailsMr userDetailsMr = (UserDetailsMr) authentication.getPrincipal();
//        return new TokenResource(TokenUtils.createToken(userDetailsMr),userDetailsMr.getId());
//    }
//
//
//    @Transactional
//    public ResponseEntity<TokenResource> confirmRegistration(Long confirmId) throws WrongProcedureFieldException, SQLException, EntityNotFoundException, UserAlreadyEnabledException {
//        KlfUsersEntity klfUsersEntity = userRepository.findOne(confirmId);
//        if (klfUsersEntity == null) {
//            throw new EntityNotFoundException(messageEntityNotFound, confirmId);
//        }
//        if (klfUsersEntity.getEnabled()==1)
//        {
//            throw new UserAlreadyEnabledException();
//        }
//        HashMap paramsIn = (new HashMap<String, Object>());
//        paramsIn.put("p_id",  confirmId);
//        userRepository.executeSP("confirmRegistration", paramsIn);
//
//        return  new ResponseEntity<>(new TokenResource(TokenUtils.createToken(new UserDetailsMr(klfUsersEntity)),klfUsersEntity.getId()), HttpStatus.OK);
//    }
//
//    @Transactional(readOnly = true)
//    public TokenResource refreshToken() throws EntityNotFoundException {
//        UserDetailsMr userDetails = getUserDetails();
//        return new TokenResource(TokenUtils.createToken(userDetails),  userDetails.getId());
//    }
//
//
//    @Transactional
//    @PreAuthorize("hasPermission(-1,'KlfUsers','UPDATE')")
//    public ResponseEntity<KlfUsersResource> setPassword(KlfUsersPassPod pod, BindingResult bindingResult) throws EntityNotFoundException, WrongProcedureFieldException, SQLException, WrongOldPasswordException, BindException {
//
//        if (bindingResult.hasErrors()) {
//            throw new BindException(bindingResult);
//        }
//
//        KlfUsersEntity entity = getCurrentUserEntity(messageEntityNotFound);
//
//        if (!passwordEncoder.matches(pod.getPasswordOld(), entity.getPassword()))
//        {
//           throw new WrongOldPasswordException();
//        }
//
//        pod.setEncodedPass(passwordEncoder.encode(pod.getPassword()));
//        userRepository.executeSP("setPass", pod.getProcParamsMap(entity.getId()));
//
//        entity = userRepository.findOne(entity.getId());
//        if (entity == null) {
//            throw new EntityNotFoundException(messageEntityNotFound,entity.getId());
//        }
//        return  new ResponseEntity<>(klfUsersAssembler.toResource(entity), HttpStatus.OK);
//
//    }
//
//    @Transactional
//    public ResponseEntity<String> resetPasswordById(Long id) throws EntityNotFoundException, WrongProcedureFieldException, SQLException {
//        KlfUsersEntity entity = userRepository.findOne(id);
//
//        if (entity == null) {
//            throw new EntityNotFoundException(messageEntityNotFound,id);
//        }
//
//        String newPass= MrStringUtils.randomString(6);
//        HashMap paramsIn = (new HashMap<String, Object>());
//
//        paramsIn.put("p_id",  entity.getId());
//        paramsIn.put("p_password", passwordEncoder.encode(newPass));
//
//        userRepository.executeSP("setPass", paramsIn);
//
//        OnResetPasswordEvent event = new OnResetPasswordEvent(entity,newPass);
//        applicationEventPublisher.publishEvent(event);
//        return  new ResponseEntity<>("Password reseted", HttpStatus.OK);
//    }
//
//    @Transactional
//    public ResponseEntity<String> remaindByLogin(String username, HttpServletRequest request) throws EntityNotFoundException, WrongProcedureFieldException, SQLException {
//        KlfUsersEntity entity = userRepository.findUserByName(username);
//        if (entity == null) {
//            throw new EntityNotFoundException("klf_users.cant_find_login",null);
//        }
//        OnRemindPasswordEvent event = new OnRemindPasswordEvent(entity);
//        applicationEventPublisher.publishEvent(event);
//        return  new ResponseEntity<>("Letter has been sent", HttpStatus.OK);
//    }
//    @Transactional
//    public ResponseEntity<String> remaindByEmail(String email, HttpServletRequest request) throws EntityNotFoundException, WrongProcedureFieldException, SQLException {
//        KlfUsersEntity entity = userRepository.findUserByEmail(email);
//        if (entity == null) {
//            throw new EntityNotFoundException("klf_users.cant_find_email",null);
//        }
//        OnRemindPasswordEvent event = new OnRemindPasswordEvent(entity);
//        applicationEventPublisher.publishEvent(event);
//        return  new ResponseEntity<>("Letter has been sent", HttpStatus.OK);
//    }
//
//    @Transactional
//    public ResponseEntity<KlfUsersResource> registerTenancy(KlfUsersTenancyPod pod, BindingResult bindingResult, HttpServletRequest request) throws BindException, WrongProcedureFieldException, SQLException, EntityNotFoundException {
//
//        if (bindingResult.hasErrors()) {throw new BindException(bindingResult);}
//        pod.setEncodedPass(passwordEncoder.encode(pod.getPassword()));
//
//        HashMap<String, Object> outMap = userRepository.executeSP("registerTenancy", pod.getProcParamsMap(null));
//        Long id = (Long)outMap.get("p_id_user");
//
//        KlfUsersEntity entity = userRepository.findOne(id);
//        if (entity == null) {
//            throw new EntityNotFoundException(messageEntityNotFound,id);
//        }
//
//        OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(entity);
//        applicationEventPublisher.publishEvent(event);
//        return new ResponseEntity<>(klfUsersAssembler.toResource(entity), HttpStatus.OK);
//    }
//
//
//    public UserDetailsMr authorization() {
//      return getUserDetails();
//    }
//
//    private  UserDetailsMr getUserDetails() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof String && principal.equals("anonymousUser")) {
//            throw new MrAccessDeniedException();
//        }
//        return  (UserDetailsMr) principal;
//    }
//
//
//    public Boolean hasgrant(HasGrantPod pod, BindingResult bindingResult) throws BindException {
//        if (bindingResult.hasErrors()) {
//            throw new BindException(bindingResult);
//        }
//       return hasGrantService.hasPermission(pod.getNameBusinessObj(),pod.getGrantTypeName());
//
//    }
//
//    public TokenResource getLongToken(Integer duration) {
//        UserDetailsMr userDetails = getUserDetails();
//        return new TokenResource(TokenUtils.createToken(userDetails,duration),  userDetails.getId());
//    }
//
//    public ResponseEntity<List<KlfUsersResource>> getReceivers(String entityName, String filter, Integer onlymain) {
//        KlfUsersEntity entity = getCurrentUserEntity(messageEntityNotFound);
//        if (entity == null) {
//                throw new MrAccessDeniedException();
//        }
//        return ResponseEntity.ok(klfUsersAssembler.toResources(userRepository.getReceivers(onlymain, mrUserGetter.getTenancyId(),filter,entity.getIdEventAccessLevel())));
//    }
}


