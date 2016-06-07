package com.excilys.mapper;

import com.excilys.dto.PageRequestDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Created by pixelfeather on 07/06/16.
 */
public class PageRequestDTOToPageRequest implements Converter<PageRequestDTO, PageRequest> {

    //TODO : Maybe need to valid it !
    @Override
    public PageRequest convert(PageRequestDTO pageRequestDTO) {
        int page = Integer.parseInt(pageRequestDTO.getPage());
        int size = Integer.parseInt(pageRequestDTO.getSize());

        if(pageRequestDTO.getDirection() != null || pageRequestDTO.getSortColumn() != null){
            return new PageRequest(page, size);
        }
        Sort.Direction direction = Sort.Direction.fromString(pageRequestDTO.getDirection());
        return new PageRequest(page, size, direction, pageRequestDTO.getSize());
    }
}
