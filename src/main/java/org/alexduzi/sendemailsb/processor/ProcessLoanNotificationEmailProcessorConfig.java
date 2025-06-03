package org.alexduzi.sendemailsb.processor;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.alexduzi.sendemailsb.domain.UserBookLoan;
import org.alexduzi.sendemailsb.util.GenerateBookReturnDate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessLoanNotificationEmailProcessorConfig {

    @Bean
    public ItemProcessor<UserBookLoan, Mail> processLoanNotificationEmailProcessor() {
        return new ItemProcessor<UserBookLoan, Mail>() {

            @Override
            public Mail process(UserBookLoan item) throws Exception {
                Email from = new Email("duzihd@gmail.com", "Biblioteca Municipal");
                Email to = new Email(item.getUser().getEmail());
                Content content = new Content("text/plain", generateEmailText(item));
                Mail mail = new Mail(from, "Notificaçao devoluçao livro", to, content);
                Thread.sleep(1000);
                return mail;
            }

            private String generateEmailText(UserBookLoan item) {
                StringBuilder writer = new StringBuilder();
                writer.append(String.format("Prezado(a), %s, matricula %d\n", item.getUser().getName(), item.getUser().getId()));
                writer.append(String.format("Informamos que o prazo de devolução do livro %s é amanhã (%s) \n", item.getBook().getName(), GenerateBookReturnDate.getDate(item.getLoan_date())));
                writer.append("Solicitamos que você renove o livro ou devolva, assim que possível.\n");
                writer.append("A Biblioteca Municipal está funcionando de segunda a sexta, das 9h às 17h.\n\n");
                writer.append("Atenciosamente,\n");
                writer.append("Setor de empréstimo e devolução\n");
                writer.append("BIBLIOTECA MUNICIPAL");
                return writer.toString();
            }
        };
    }
}
