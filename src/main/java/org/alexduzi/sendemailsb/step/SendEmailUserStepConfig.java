package org.alexduzi.sendemailsb.step;

import org.alexduzi.sendemailsb.domain.UserBookLoan;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SendEmailUserStepConfig {

    private PlatformTransactionManager transactionManager;

    public SendEmailUserStepConfig(@Qualifier("transactionManagerApp") PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Bean
    public Step sendEmailUserStep(JobRepository jobRepository,
                                  ItemReader<UserBookLoan> itemReaderUserBookLoan {
        return new StepBuilder("sendEmailUserStep", jobRepository)
                .<UserBookLoan, UserBookLoan>chunk(1, transactionManager)
                .reader(itemReaderUserBookLoan)
                .processor(null)
                .writer(null)
                .build();
    }
}
