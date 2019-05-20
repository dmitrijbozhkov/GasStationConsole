package org.nure.gas_station.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    private ModelAndView prepareMainView() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView home() {
        return prepareMainView();
    }

    @RequestMapping("/{path:^(?:(?!^images$|^js$|^css$|^api$).)*$}/**")
    public ModelAndView anyPath() {
        return prepareMainView();
    }
}