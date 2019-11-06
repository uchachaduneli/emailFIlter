package com.email.filter.controller;

import com.email.filter.dto.FilterDTO;
import com.email.filter.misc.Response;
import com.email.filter.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author
 */
@RequestMapping("/filters")
@Controller
public class FilterController {

    @Autowired
    private FilterService filterService;

    @RequestMapping("/get-filter")
    @ResponseBody
    private Response getFilters() throws Exception {
        return Response.withSuccess(filterService.getFilters());
    }

    @RequestMapping("/get-filter-types")
    @ResponseBody
    private Response getFilterTypes() throws Exception {
        return Response.withSuccess(filterService.getFilterTypes());
    }

    @RequestMapping({"/save"})
    @ResponseBody
    public Response saveFilter(@RequestBody FilterDTO request) throws Exception {
        return Response.withSuccess(FilterDTO.parse(filterService.save(request)));
    }

    @RequestMapping({"/delete-filter"})
    @ResponseBody
    public Response deleteFilter(@RequestParam int id) {
        filterService.delete(id);
        return Response.withSuccess(true);
    }

}
