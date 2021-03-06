package com.yuma.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.yuma.app.service.HelperCombo.CombinationReportHelper;
import com.yuma.app.service.HelperCombo.WeeklyCombinationHelper;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.yuma.app.document.Consumer;
import com.yuma.app.service.CombinationReportService;
import com.yuma.app.to.CombinationReportTO;
import com.yuma.app.util.WriteCsvToResponse;

@Slf4j
@RestController
@RequestMapping("api/combinationreport")
public class CombinationReportController {

	CombinationReportService combinationReportService;
	CombinationReportHelper combinationReportHelper;
	WeeklyCombinationHelper weeklyCombinationHelper;

	@Autowired
	public CombinationReportController(CombinationReportService combinationReportService, CombinationReportHelper combinationReportHelper, WeeklyCombinationHelper weeklyCombinationHelper) {
		this.combinationReportService = combinationReportService;
		this.combinationReportHelper = combinationReportHelper;
		this.weeklyCombinationHelper =weeklyCombinationHelper;
	}

	@GetMapping("/weeklycombo")
	public List<CombinationReportTO> getWeeklyCombo() {
		log.info("generating weekly meal combo");
		return this.combinationReportService.generateWeeklyCombination();
	}

	@PostMapping("/weeklycombo/{index}")
	public void chosenCombo(@PathVariable int index){
		
		log.info("Saving combination report");
		combinationReportService.saveCombinationReport(index);
	}
	
	@RequestMapping(value = "/download/consumers.csv", produces = "text/csv")
	public void downloadCSV(HttpServletResponse response) throws IOException {
		log.info("Downloading CSV");
		List<Consumer> consumerList = this.combinationReportService.getMostRecentlyAdded().getUserList();
		WriteCsvToResponse.writeConsumers(response.getWriter(), consumerList);
	}
	
	@GetMapping("/search/{date}")
	public CombinationReportTO getCombinationReportByDate(@PathVariable DateTime date){
		log.info("Retrieving combination report from "+ date);
		return this.combinationReportService.getCombinationReportByDate(date);
	}

	@GetMapping("/search")
	public List<CombinationReportTO> getCombinationReportByDate(
		@RequestParam(name = "startdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam(name = "enddate")	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
		
		log.info("Retrieving combination report from "+ startDate + " to " + endDate);
		return this.combinationReportService.listCombinationReportByDate(startDate, endDate);
	}
}
