package com.excilys.mapper;

import com.excilys.dto.PageRequestDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestToPageRequestDTO implements Converter<PageRequest, PageRequestDTO>{

    @Override
    public PageRequestDTO convert(PageRequest pageRequest) {
        String page = String.valueOf(pageRequest.getPageNumber());
        String size = String.valueOf(pageRequest.getPageSize());
        String direction = null;
        String sortColumn = null;

        Sort sort = pageRequest.getSort();
        if(sort != null){
            Sort.Order order = sort.iterator().next();
            if(order != null){
                direction = order.getDirection().toString();
                sortColumn = order.getProperty();
            }
        }

        return new PageRequestDTO(page, size, direction, sortColumn);
    }

}
