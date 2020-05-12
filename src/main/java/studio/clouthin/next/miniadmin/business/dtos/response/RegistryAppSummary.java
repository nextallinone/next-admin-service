package studio.clouthin.next.miniadmin.business.dtos.response;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import studio.clouthin.next.miniadmin.business.models.RegistryApp;
import studio.clouthin.next.shared.dto.response.AbstractResponseSummary;

@Data
public class RegistryAppSummary extends AbstractResponseSummary {

    private String name;

    private String remark;

    private String redirectUrl;

    private String authUrl;

    public static RegistryAppSummary from(RegistryApp obj) {
        if (obj == null) {
            return null;
        }
        RegistryAppSummary result = new RegistryAppSummary();
        BeanUtils.copyProperties(obj, result);
        return result;
    }
}
