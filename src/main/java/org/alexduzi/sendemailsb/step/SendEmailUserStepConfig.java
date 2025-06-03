package org.alexduzi.sendemailsb.step;

import com.sendgrid.helpers.mail.Mail;
import org.alexduzi.sendemailsb.domain.UserBookLoan;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
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
                                  ItemReader<UserBookLoan> readUsersWithLoansCloseToReturnReader,
                                  ItemProcessor<UserBookLoan, Mail> processLoanNotificationEmailProcessor,
                                  ItemWriter<Mail> sendEmailRequestReturnWriter) {
        return new StepBuilder("sendEmailUserStep", jobRepository)
                .<UserBookLoan, Mail>chunk(1, transactionManager)
                .reader(readUsersWithLoansCloseToReturnReader)
                .processor(processLoanNotificationEmailProcessor)
                .writer(sendEmailRequestReturnWriter)
                .build();
    }
}
