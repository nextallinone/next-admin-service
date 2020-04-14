package studio.clouthin.next.miniadmin.framework.dict.services.impl;

import cn.hutool.core.util.RandomUtil;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictMergeRequest;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictQueryParameter;
import studio.clouthin.next.miniadmin.framework.dict.models.Dict;
import studio.clouthin.next.miniadmin.framework.dict.repositoies.DictRepository;
import studio.clouthin.next.miniadmin.framework.dict.services.DictService;
import studio.clouthin.next.shared.utils.JPAQueryHelper;

@Service
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictRepository dictRepository;

    @Override
    public Page<Dict> page(DictQueryParameter parameter, Pageable pageable) {
        return dictRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                JPAQueryHelper.getPredicate(
                        root,
                        parameter,
                        criteriaBuilder), pageable);
    }

    @Override
    public Dict create(DictMergeRequest dictMergeRequest) {
        Dict dict = new Dict();
        dict.setName(dictMergeRequest.getName());
        dict.setRemark(dictMergeRequest.getRemark());
        return dictRepository.save(dict);
    }

    @Override
    public Dict update(String id, DictMergeRequest dictMergeRequest) {
        Dict dict = dictRepository.findById(id).orElse(null);
        Preconditions.checkNotNull(dict);
        dict.setName(dictMergeRequest.getName());
        dict.setRemark(dictMergeRequest.getRemark());
        return dictRepository.save(dict);
    }

    @Override
    public Dict deleteById(String id) {
        Dict dict = dictRepository.findById(id).orElse(null);
        Preconditions.checkNotNull(dict);
        dict.setName(dict.getName() + "_deleted_" + RandomUtil.randomString(4));
        dict.markAsDeleted();
        return dictRepository.save(dict);
    }
}
