package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.TrainingReportService;


import java.time.YearMonth;

@RestController
@RequestMapping("/v1/training-report")
@RequiredArgsConstructor
public class TrainingReportController {

    private final TrainingReportService trainingReportService;

    /**
     * Triggers the generation and e-mail sending of mothly training reports for all users.
     * If no month is specified, the previous month is used by default.
     *
     * @param year Optional (e.g., 2025)
     * @param month Optional month (1-12)
     * @return HTTP 200 if reports were generated and sent
     */
    @PostMapping("/monthly")
    public ResponseEntity<?> generateAndSendMonthlyReports(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ){
        YearMonth reportMonth = (year != null && month != null)
                ? YearMonth.of(year, month)
                : YearMonth.now().minusMonths(1); // żeby defaultowo dawał poprzedni miesiąc

        trainingReportService.generateAndSendMonthlyReports(reportMonth);
        return ResponseEntity.ok("Miesięczny raport został wygenerowany oraz wysłany. Życzę Panu miłego dnia ;)");

    }



}
