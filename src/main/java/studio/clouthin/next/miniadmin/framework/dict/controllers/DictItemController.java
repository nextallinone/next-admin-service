package studio.clouthin.next.miniadmin.framework.dict.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictItemQueryParameter;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictItermMergeRequest;
import studio.clouthin.next.miniadmin.framework.dict.dto.response.DictItemSummary;
import studio.clouthin.next.miniadmin.framework.dict.services.DictItemService;

@Api(tags = "字典项管理")
@RestController
@RequestMapping("/dictItems")
public class DictItemController {

    @Autowired
    private DictItemService dictItemService;

    @GetMapping("/getByDict")
    public Page<DictItemSummary> page(DictItemQueryParameter queryParameter,
                                      @PageableDefault(sort = {"sort"}, direction = Sort.Direction.ASC)  Pageable pageable) {
        return dictItemService.page(queryParameter, pageable).map(DictItemSummary::from);
    }

    @PostMapping("")
    public DictItemSummary create(@RequestBody DictItermMergeRequest dictItermMergeRequest) {
        return DictItemSummary.from(dictItemService.create(dictItermMergeRequest));
    }

    @PutMapping("/{id}")
    public DictItemSummary update(@PathVariable String id, @RequestBody DictItermMergeRequest dictItermMergeRequest) {
        return DictItemSummary.from(dictItemService.update(id, dictItermMergeRequest));
    }

    @DeleteMapping("/{id}")
    public DictItemSummary delete(@PathVariable String id) {
        return DictItemSummary.from(dictItemService.deleteById(id));
    }

}
