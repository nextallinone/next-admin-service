package studio.clouthin.next.miniadmin.framework.attachment;

import in.clouthink.daas.fss.alioss.AliossAutoConfiguration;
import in.clouthink.daas.fss.alioss.support.DefaultOssProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AliossAutoConfiguration.class})
@EnableConfigurationProperties(AliossModuleConfiguration.AliOssProperties.class)
public class AliossModuleConfiguration {

    @ConfigurationProperties(prefix = "fss.alioss")
    public static class AliOssProperties extends DefaultOssProperties {

    }

}
