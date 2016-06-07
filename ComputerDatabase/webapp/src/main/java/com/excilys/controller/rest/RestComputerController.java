package com.excilys.controller.rest;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.PageRequestDTO;
import com.excilys.mapper.ComputerDTOToComputer;
import com.excilys.mapper.PageRequestDTOToPageRequest;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestComputerController {

    @Autowired
    ComputerService computerService;

    @RequestMapping(value = "/computer/{id}", method = RequestMethod.GET)
    public Computer find(@PathVariable("id") long id){
        return computerService.find(id);
    }

    @RequestMapping(value = "/computer/list", method = RequestMethod.GET)
    public List<Computer> findAll(){
        List<Computer> list = new ArrayList<>();
        for (Computer computer: computerService.findAll()) {
            list.add(computer);
        }
        return list;
    }

    @RequestMapping(value = "/computer/page", method = RequestMethod.GET)
    public List<Computer> findPage(@Valid @RequestBody PageRequestDTO pageDto){
        PageRequest request = new PageRequestDTOToPageRequest().convert(pageDto);
        List<Computer> list = new ArrayList<>();
        for (Computer computer: computerService.findPage(request)) {
            list.add(computer);
        }
        return list;
    }

    @RequestMapping(value = "/computer", method = RequestMethod.POST)
    public Computer insert(@Valid @RequestBody ComputerDTO dto) {
        Computer computer = new ComputerDTOToComputer().convert(dto);
        return computerService.insert(computer);
    }

    @RequestMapping(value = "/computer/{i}", method = RequestMethod.DELETE)
    public void remove(@PathVariable("id") long id) {
        computerService.remove(id);
    }

    @RequestMapping(value = "/computer/{i}", method = RequestMethod.PUT)
    public Computer update(@PathVariable("id") long id,  @Valid @RequestBody ComputerDTO dto) {
        Computer computer = new ComputerDTOToComputer().convert(dto);
        return computerService.update(id, computer);
    }

}
