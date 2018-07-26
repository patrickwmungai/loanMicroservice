package com.finessence.loan.processes;

import com.finessence.loan.repository.CrudService;
import com.finessence.loan.services.GlobalFunctions;
import com.finessence.loan.services.RestTemplateServices;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author patrick
 */
@Service("processService")
@Transactional
public class ProcesServiceImpl implements ProcesService {

    Logger LOGGER = Logger.getLogger(ProcesServiceImpl.class.getName());

    @Autowired
    Environment environment;

    private static ThreadPoolExecutor exec_service = new ThreadPoolExecutor(10, 10, 5000, TimeUnit.SECONDS, new LinkedBlockingQueue());

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(20, 20, 5000, TimeUnit.SECONDS, new LinkedBlockingQueue());

    @Autowired
    Environment env;

    @Autowired
    GlobalFunctions globalFunctions;

    @Autowired
    CrudService crudService;

    @Autowired
    RestTemplateServices resttemplateService;

//    @Scheduled(initialDelay = 2000, fixedRate = 2000)
    @Override
    public void processCrude() {
        LOGGER.info("processing Crude items");
    }

   // @Scheduled(initialDelay = 2000, fixedRate = 2000)
    @Override
    public void processRepayments() {
        LOGGER.info("processing Repayment items");
        /*
        Get Loan shedules when the date is due
        Debit the settlement account
        credit the loan Account
        update the schedule as paid default and days due
         */
        globalFunctions.processRepayment();

    }

}
