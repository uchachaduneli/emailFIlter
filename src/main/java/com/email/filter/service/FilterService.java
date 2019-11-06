package com.email.filter.service;


import com.email.filter.dao.FilterDAO;
import com.email.filter.dto.FilterDTO;
import com.email.filter.dto.FilterTypeDTO;
import com.email.filter.model.Filter;
import com.email.filter.model.FilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author
 */
@Service
public class FilterService {

    @Autowired
    private FilterDAO filterDAO;

    public List<FilterDTO> getFilters() {
        return FilterDTO.parseToList(filterDAO.getAll(Filter.class));
    }

    @Transactional(rollbackFor = Throwable.class)
    public Filter save(FilterDTO request) throws Exception {
        Filter filter = new Filter();
        filter.setDesc(request.getDesc());
        filter.setType((FilterType) filterDAO.find(FilterType.class, request.getTypeId()));
        if (request.getId() != null) {
            filter.setId(request.getId());
            filter = (Filter) filterDAO.update(filter);
        } else {
            filter = (Filter) filterDAO.create(filter);
        }
        return filter;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void delete(int id) {
        Filter filter = (Filter) filterDAO.find(Filter.class, id);
        if (filter != null) {
            filterDAO.delete(filter);
        }
    }

    public List<FilterTypeDTO> getFilterTypes() {
        return FilterTypeDTO.parseToList(filterDAO.getAll(FilterType.class));
    }
}
