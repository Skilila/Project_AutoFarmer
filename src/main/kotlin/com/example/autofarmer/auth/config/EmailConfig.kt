import com.example.autofarmer.auth.dto.EmailProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
@EnableConfigurationProperties(EmailProperties::class)
class EmailConfig(
    private val emailProperties: EmailProperties
) {
    @Bean
    fun javaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl().apply {
            host = emailProperties.host
            port = emailProperties.port
            username = emailProperties.username
            password = emailProperties.password
        }
        return mailSender
    }
}
