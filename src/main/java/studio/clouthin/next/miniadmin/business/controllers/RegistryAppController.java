package studio.clouthin.next.miniadmin.business.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import studio.clouthin.next.miniadmin.business.dtos.request.MergeRegistryAppRequest;
import studio.clouthin.next.miniadmin.business.dtos.request.RegistryAppCriteria;
import studio.clouthin.next.miniadmin.business.dtos.response.RegistryAppSummary;
import studio.clouthin.next.miniadmin.business.services.RegistryAppService;

@Api(value = "注册应用")
@RestController
@RequestMapping("/registryApps")
public class RegistryAppController {

    @Autowired
    private RegistryAppService registryAppService;

    @ApiOperation(value = "创建注册应用")
    @PostMapping(value = {"/create"})
    public RegistryAppSummary create(@RequestBody MergeRegistryAppRequest mergeRegistryAppRequest) {
        return RegistryAppSummary.from(registryAppService.create(mergeRegistryAppRequest));
    }


    @ApiOperation(value = "更新注册应用")
    @PutMapping(value = {"/{id}/update/{version}"})
    public RegistryAppSummary update(@RequestBody MergeRegistryAppRequest mergeRegistryAppRequest,
                                     @PathVariable String id,
                                     @PathVariable int version) {
        return RegistryAppSummary.from(registryAppService.update(id, mergeRegistryAppRequest, version));
    }


    @ApiOperation(value = "查询")
    @GetMapping(value = {"/page"})
    public Page<RegistryAppSummary> page(RegistryAppCriteria criteria, Pageable pageable) {
        return registryAppService.queryAll(criteria, pageable).map(RegistryAppSummary::from);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping(value = {"/{id}/delete"})
    public void delete(@PathVariable String id) {
        registryAppService.delete(id);
    }
}
