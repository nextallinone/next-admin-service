package studio.clouthin.next.miniadmin.framework.dict.services.impl;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictItemQueryParameter;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictItermMergeRequest;
import studio.clouthin.next.miniadmin.framework.dict.models.Dict;
import studio.clouthin.next.miniadmin.framework.dict.models.DictItem;
import studio.clouthin.next.miniadmin.framework.dict.repositoies.DictItemRepository;
import studio.clouthin.next.miniadmin.framework.dict.repositoies.DictRepository;
import studio.clouthin.next.miniadmin.framework.dict.services.DictItemService;
import studio.clouthin.next.shared.utils.JPAQueryHelper;

@Service
@Transactional
public class DictItemServiceImpl implements DictItemService {

    @Autowired
    private DictItemRepository dictItemRepository;

    @Autowired
    private DictRepository dictRepository;


    @Override
    public Page<DictItem> page(DictItemQueryParameter parameter, Pageable pageable) {
        return dictItemRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                JPAQueryHelper.getPredicate(
                        root,
                        parameter,
                        criteriaBuilder), pageable);
    }

    @Override
    public DictItem create(DictItermMergeRequest dictItermMergeRequest) {
        Dict dict = dictRepository.findById(dictItermMergeRequest.getDictId()).orElse(null);
        Preconditions.checkNotNull(dict);
        DictItem dictItem = new DictItem();
        dictItem.setLabel(dictItermMergeRequest.getLabel());
        dictItem.setValue(dictItermMergeRequest.getValue());
        dictItem.setSort(dictItermMergeRequest.getSort());
        dictItem.setDict(dict);
        return dictItemRepository.save(dictItem);
    }

    @Override
    public DictItem update(String id, DictItermMergeRequest dictItermMergeRequest) {
        DictItem dictItem = dictItemRepository.findById(id).orElse(null);
        Preconditions.checkNotNull(dictItem);
        dictItem.setLabel(dictItermMergeRequest.getLabel());
        dictItem.setValue(dictItermMergeRequest.getValue());
        dictItem.setSort(dictItermMergeRequest.getSort());
        return dictItemRepository.save(dictItem);
    }

    @Override
    public DictItem deleteById(String id) {
        DictItem dictItem = dictItemRepository.findById(id).orElse(null);
        Preconditions.checkNotNull(dictItem);
        dictItem.setLabel(dictItem.getLabel() + "_deleted");
        dictItem.markAsDeleted();
        return dictItemRepository.save(dictItem);
    }
}
