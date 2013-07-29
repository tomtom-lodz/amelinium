package com.tomtom.amelinium.confluence.doc.controller;

import com.knappsack.swagger4springweb.controller.ApiDocumentationController;
import com.wordnik.swagger.core.Documentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
* This is an example of how you might extend the ApiDocumentationController in order to set your own RequestMapping
* (instead of the default "/api") among other possibilities.  Going this route, you do not necessarily have to define
* the controller in your servlet context.
*/
@Controller
@RequestMapping(value = "/documentation")
public class ExampleDocumentationController extends ApiDocumentationController {

    public ExampleDocumentationController() {
        setBaseControllerPackage("com.tomtom.amelinium.confluence.server.controller");
//        List<String> controllerPackages = new ArrayList<String>();
//        controllerPackages.add("com.knappsack.swagger4springweb.controllers.additionalApi");
//        setAdditionalControllerPackages(controllerPackages);
//
//        setBaseModelPackage("com.knappsack.swagger4springweb.models");
//        List<String> modelPackages = new ArrayList<String>();
//        controllerPackages.add("com.knappsack.swagger4springweb.additionalModels");
//        setAdditionalModelPackages(modelPackages);

        setApiVersion("v1");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String documentation() {
        return "documentation";
    }
    
//    @RequestMapping(value = "/resourceList", method = RequestMethod.GET, produces = "application/json")
//    public
//    @ResponseBody
//    Documentation getResources(HttpServletRequest request) {
//        return super.getResources(request);
//    }

    
}
