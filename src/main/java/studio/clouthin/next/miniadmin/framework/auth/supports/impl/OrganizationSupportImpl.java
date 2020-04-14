package studio.clouthin.next.miniadmin.framework.auth.supports.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.BindingUsersRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.UserSummary;
import studio.clouthin.next.miniadmin.framework.auth.models.User;
import studio.clouthin.next.miniadmin.framework.auth.services.OrganizationService;
import studio.clouthin.next.miniadmin.framework.auth.supports.OrganizationSupport;
import studio.clouthin.next.miniadmin.framework.auth.models.Organization;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.OrganizationMergeRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.UserQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.OrganizationSummary;
import studio.clouthin.next.miniadmin.framework.auth.services.SystemUserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrganizationSupportImpl implements OrganizationSupport {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private SystemUserService userService;


    @Override
    public List<OrganizationSummary> findAll() {
        return organizationService.findAll()
                .stream()
                .map(OrganizationSummary::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrganizationSummary> findMine() {
        return findAll();
    }

    @Override
    public Page<UserSummary> getUsers(String orgId, UserQueryParameter queryRequest, Pageable pageable) {
        return userService
                .findByOrgId(orgId, queryRequest, pageable)
                .map(UserSummary::from);
    }

    @Override
    public void bindUsers(String orgId, BindingUsersRequest request) {
        Organization organization = organizationService.findById(orgId);
        List<User> bindUsers = userService.findAllByIdIn(request.getUserIds());
        organization.getUsers().removeAll(bindUsers);
        organization.getUsers()
                .addAll(bindUsers);
        organizationService.save(organization);
    }

    @Override
    public void unbindUsers(String orgId, BindingUsersRequest request) {
        Organization organization = organizationService.findById(orgId);
        List<User> unbindUsers = userService.findAllByIdIn(request.getUserIds());
        organization.getUsers().removeAll(unbindUsers);
        organizationService.save(organization);
    }

    @Override
    public OrganizationSummary create(OrganizationMergeRequest mergeRequest) {
        Organization organization = new Organization();
//        organization.setId(UUID.fastUUID().toString());
        organization.setParentId(mergeRequest.getParentId());
        organization.setName(mergeRequest.getName());
        organization.setCode(mergeRequest.getCode());
        organization.setDescription(mergeRequest.getDescription());
        return OrganizationSummary.from(organizationService.save(organization));
    }

    @Override
    public OrganizationSummary update(String id, OrganizationMergeRequest mergeRequest) {
        Organization organization = organizationService.findById(id);
        organization.setName(mergeRequest.getName());
        organization.setDescription(mergeRequest.getDescription());
        return OrganizationSummary.from(organizationService.save(organization));
    }

    @Override
    public OrganizationSummary move(String id, String parentIdl) {
        Organization organization = organizationService.findById(id);
        organization.setParentId(parentIdl);
        return OrganizationSummary.from(organizationService.save(organization));
    }
}
