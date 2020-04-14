package studio.clouthin.next.miniadmin.framework.auth.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.BindingUsersRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.OrganizationQueryCriteria;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.UserQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.UserSummary;
import studio.clouthin.next.miniadmin.framework.auth.supports.OrganizationSupport;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.OrganizationMergeRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.OrganizationSummary;

import java.util.List;

/**
 * @author vanish
 */
@Api(tags = "组织机构管理")
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationSupport organizationSupport;

    @ApiOperation("查询组织机构")
    @GetMapping("/all")
    public List<OrganizationSummary> findAll(OrganizationQueryCriteria criteria) {
        return organizationSupport.findAll();
    }

    @ApiOperation("查询组织机构")
    @GetMapping("/mine")
    public List<OrganizationSummary> findMine(OrganizationQueryCriteria criteria) {
        return organizationSupport.findMine();
    }

    @ApiOperation("查询组织机构")
    @GetMapping("/{id}/getUsers")
    public Page<UserSummary> getUsers(@PathVariable String id, UserQueryParameter queryRequest, Pageable pageable) {
        return organizationSupport.getUsers(id, queryRequest, pageable);
    }

    @ApiOperation("绑定用户")
    @PostMapping("/bindUsersByOrg/{orgId}")
    public void bindUsersByOrg(@PathVariable String orgId,
                               @RequestBody BindingUsersRequest bindingUsersRequest) {
        organizationSupport.bindUsers(orgId, bindingUsersRequest);
    }


    @ApiOperation("解绑用户")
    @PostMapping("/unBindUsersByOrg/{orgId}")
    public void unBindUsersByOrg(@PathVariable String orgId,
                                 @RequestBody BindingUsersRequest bindingUsersRequest) {
        organizationSupport.unbindUsers(orgId, bindingUsersRequest);
    }


    @ApiOperation("新增组织机构")
    @PostMapping("")
    public OrganizationSummary create(@RequestBody OrganizationMergeRequest mergeRequest) {
        return organizationSupport.create(mergeRequest);
    }

    @ApiOperation("更新组织机构")
    @PutMapping("/{id}")
    public OrganizationSummary update(@PathVariable String id, @RequestBody OrganizationMergeRequest mergeRequest) {
        return organizationSupport.update(id, mergeRequest);
    }

    @ApiOperation("更改组织机构层级")
    @PutMapping("/{id}/move/{parentId}")
    public OrganizationSummary move(@PathVariable String id, @PathVariable String parentId) {
        return organizationSupport.move(id, parentId);
    }
}
