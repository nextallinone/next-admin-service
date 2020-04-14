package studio.clouthin.next.miniadmin.framework.auth.supports;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.BindingUsersRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.UserSummary;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.OrganizationMergeRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.UserQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.OrganizationSummary;

import java.util.List;

public interface OrganizationSupport {

    List<OrganizationSummary> findAll();

    List<OrganizationSummary> findMine();

    Page<UserSummary> getUsers(String orgId, UserQueryParameter queryRequest, Pageable pageable);

    void bindUsers(String orgId, BindingUsersRequest request);

    void unbindUsers(String orgId, BindingUsersRequest request);

    OrganizationSummary create(OrganizationMergeRequest mergeRequest);

    OrganizationSummary update(String id, OrganizationMergeRequest mergeRequest);

    OrganizationSummary move(String id, String parentIdl);

}
