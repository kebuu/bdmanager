package com.cta.web.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cta.model.Bd;
import com.cta.model.Serie;

@Controller
@RequestMapping("/serie")
public class SerieController {

	@ResponseBody
	@RequestMapping(method=RequestMethod.GET)
	public Serie get() {
		return new Serie(1l, new ArrayList<Bd>(), "Ma serie", null, 10f);
	}
}
