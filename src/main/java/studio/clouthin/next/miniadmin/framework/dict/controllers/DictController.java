package studio.clouthin.next.miniadmin.framework.dict.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import studio.clouthin.next.miniadmin.framework.dict.services.DictService;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictMergeRequest;
import studio.clouthin.next.miniadmin.framework.dict.dto.response.DictSummary;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictQueryParameter;

@Api(tags = "字典管理")
@RestController
@RequestMapping("/dicts")
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping("")
    public Page<DictSummary> page(DictQueryParameter queryParameter, Pageable pageable) {
        return dictService.page(queryParameter, pageable).map(DictSummary::from);
    }

    @PostMapping("")
    public DictSummary create(@RequestBody DictMergeRequest dictMergeRequest) {
        return DictSummary.from(dictService.create(dictMergeRequest));
    }

    @PutMapping("/{id}")
    public DictSummary update(@PathVariable String id, @RequestBody DictMergeRequest dictMergeRequest) {
        return DictSummary.from(dictService.update(id, dictMergeRequest));
    }

    @DeleteMapping("/{id}")
    public DictSummary delete(@PathVariable String id) {
        return DictSummary.from(dictService.deleteById(id));
    }
}
