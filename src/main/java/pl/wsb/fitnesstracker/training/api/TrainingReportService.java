package pl.wsb.fitnesstracker.training.api;

import java.time.YearMonth;


public interface TrainingReportService {

    /**
     * Generates and sends monthly training reports for all users for the given month.
     * @param yearMonth The month for which to generate the report (e.g., 2025-03)
     */

    void generateAndSendMonthlyReports(YearMonth yearMonth);


}


