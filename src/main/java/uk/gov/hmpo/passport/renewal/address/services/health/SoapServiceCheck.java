package uk.gov.hmpo.passport.renewal.address.services.health;

import com.codahale.metrics.health.HealthCheck;
import uk.gov.hmpo.passport.renewal.address.services.util.QASSearch;

public class SoapServiceCheck extends HealthCheck {

    protected final QASSearch qasSearch;
    private String mode;

    public SoapServiceCheck(QASSearch qasSearch, String mode) {
        super();
        this.mode = mode;
        this.qasSearch = qasSearch;
    }

    @Override
    protected Result check() throws Exception {
        if (mode.equals("PROD")) {
            String message = qasSearch.checkHealth();
            return message == null ? Result.healthy() : Result.unhealthy(message);
        } else {
            return Result.healthy();
        }
    }

}
